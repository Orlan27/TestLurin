package com.zed.lurin.mdb.cas.queue.validation;

import com.zed.lurin.mdb.api.dto.CampaignGenericMapping;
import com.zed.lurin.mdb.api.keys.QueueNameKeys;
import com.zed.lurin.mdb.api.queue.IMessageQueue;
import com.zed.lurin.mdb.core.keys.QueueMessageKeys;
import com.zed.lurin.process.cas.dto.CampaingCAS;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "queue/ValidationReceiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "queue/validationCommandCasQueue")})
public class ValidationReceiver implements MessageListener {

    private static Logger LOGGER = Logger.getLogger(ValidationReceiver.class.getName());

    @EJB
    IMessageQueue messageQueue;

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
                    QueueNameKeys.VALIDATION_QUEUE_NAME.toString()));
            MapMessage mapMessage = (MapMessage) message;

            byte[] messagesData = mapMessage.getBytes(QueueMessageKeys.DATA.toString());

            CampaignGenericMapping campaignGenericMapping = SerializationUtils.deserialize(messagesData);

            /**
             * @apiNote Lorenzo Rodriguez
             * here you must add the command sending method for only the first 20
             In case the CAS rejects any command the method must return an Exception to rollback and then try
             */


            this.messageQueue.send(QueueNameKeys.SEND_COMMAND_QUEUE_NAME, campaignGenericMapping);
            LOGGER.debug(String.format("[QUEUE][%s] Successful Validation, Sending the rest of the CAS commands",
                    QueueNameKeys.VALIDATION_QUEUE_NAME.toString()));

        }catch (Exception e){
            LOGGER.error(
                    String.format("[QUEUE][%s] Error when validating the commands of the cas [%s]",
                            QueueNameKeys.VALIDATION_QUEUE_NAME.toString(), e.getMessage()));
            this.context.setRollbackOnly();
        }

    }
}
