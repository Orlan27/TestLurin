package com.zed.lurin.mdb.cas.queue.sending;

import com.zed.lurin.domain.jpa.Carriers;
import com.zed.lurin.domain.jpa.Provisioning;
import com.zed.lurin.mdb.api.dto.CampaignGenericMapping;
import com.zed.lurin.mdb.api.keys.QueueNameKeys;
import com.zed.lurin.mdb.api.queue.IMessageQueue;
import com.zed.lurin.mdb.core.keys.QueueMessageKeys;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.process.cas.services.ICampaignsCasServices;
import com.zed.lurin.process.cas.services.IProvisioningServicesImpl;
import com.zed.lurin.security.keys.Keys;
import com.zed.operators.managment.services.IOperatorsManagmentServices;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.List;
import java.util.NoSuchElementException;

@MessageDriven(name = "queue/SendingCommand", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "queue/sendCommandCasQueue")})
public class SendingCommand implements MessageListener {

    private static Logger LOGGER = Logger.getLogger(SendingCommand.class.getName());

    @Inject
    IMessageQueue messageQueue;

    @EJB
    ICampaignsCasServices iCampaignsCasServices;

    @EJB
    IProvisioningServicesImpl iProvisioningServices;

    @EJB
    IOperatorsManagmentServices iOperatorsManagmentServices;

    @EJB
    ICoreParameterServices iCoreParameterServices;

    /**
     * The context associated with the bean
     */
    @Resource
    private MessageDrivenContext context;

    /**
     * Passes a message to the listener.
     *
     * @param message the message passed to the listener
     */
    @Override
    public void onMessage(Message message) {

        try{
            LOGGER.debug(String.format("[QUEUE] Receiving message on the %s queue",
                    QueueNameKeys.SEND_COMMAND_QUEUE_NAME.toString()));
            MapMessage mapMessage = (MapMessage) message;

            byte[] messagesData = mapMessage.getBytes(QueueMessageKeys.DATA.toString());

            CampaignGenericMapping campaignGenericMapping = SerializationUtils.deserialize(messagesData);

            long totalCarriers = this.iOperatorsManagmentServices.countAllCarriers();

            int commandsPerSecondsConf = this.iCoreParameterServices.getCoreParameterInteger(Keys.FEE_SENDING_COMMANDS_PER_SECOND.toString());

            float commandsPerSeconds = ((100/totalCarriers)*commandsPerSecondsConf) / 100;

            long feeInMilliseconds = (long)((60/commandsPerSeconds)*1000);


            List<Provisioning> provisioningList =
                    this.iProvisioningServices.getProvisioningMembersByCarrier (campaignGenericMapping.getCampaignMember(),
                            campaignGenericMapping.getCarrierId(),
                            campaignGenericMapping.getInitBatch(), campaignGenericMapping.getEndBatch());

            provisioningList.stream().forEach(provisioning -> {
                /**
                 * Send to cas command
                 */
                try {
                    this.iCampaignsCasServices.sendCampaignsCas(provisioning,
                            campaignGenericMapping.getIpServerCas(),
                            campaignGenericMapping.getPortServerCas(),
                            campaignGenericMapping.getPortServerCas2(),
                            campaignGenericMapping.getPortServerCas3());
                    Thread.sleep(feeInMilliseconds);
                    this.iProvisioningServices.deleteProvisioning(provisioning.getCode());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });


        }catch (Exception e){
            LOGGER.error(
                    String.format("[QUEUE][%s] Error while sending CAS commands [%s]",
                            QueueNameKeys.SEND_COMMAND_QUEUE_NAME.toString(),e.getMessage()));
            this.context.setRollbackOnly();
        }

    }
}
