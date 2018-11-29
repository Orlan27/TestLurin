package com.zed.lurin.mdb.services.queue;


import com.zed.lurin.mdb.api.dto.CampaignGenericMapping;
import com.zed.lurin.mdb.api.queue.IMessageQueue;
import com.zed.lurin.mdb.api.keys.QueueNameKeys;
import com.zed.lurin.mdb.core.keys.QueueCommonsKeys;
import com.zed.lurin.mdb.core.keys.QueueMessageKeys;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Bean the queue needs to manage the sending of messages to queues.
 *
 * @author Francisco Lujano
 */
@Stateless
public class MessageQueueAPI implements IMessageQueue {
    /*
     * Logger
     */
    private static Logger LOGGER = Logger.getLogger(MessageQueueAPI.class.getName());

    /**
     *
     * This method send a message to the given queue.
     *
     * @param queueName
     * @param campaignGenericMapping
     */
    @Override
    public void send(QueueNameKeys queueName, CampaignGenericMapping campaignGenericMapping){

        try{
            Context ctx = new InitialContext();
            QueueConnectionFactory qcf =
                    (QueueConnectionFactory) ctx.lookup(QueueCommonsKeys
                            .QUEUE_CONNECTION_FACTORY_NAME.toString());

            final Queue queue = (Queue) ctx.lookup(queueName.toString());
            try (QueueConnection conn = qcf.createQueueConnection();
                 QueueSession session = conn.createQueueSession(false,
                         QueueSession.AUTO_ACKNOWLEDGE);
                 QueueSender send = session.createSender(queue)) {

                conn.start();

                MapMessage mapMessage = session.createMapMessage();

                /**
                 * Serialize object to send to the queue
                 */

                byte[] messagesData = SerializationUtils.serialize(campaignGenericMapping);

                mapMessage.setBytes(
                        QueueMessageKeys.DATA.toString(),
                        messagesData);

                send.send(mapMessage);

                LOGGER.info(String.format("Message sent successfully to the %s queue",queueName.toString()));
            }
        }catch (Exception e){
            LOGGER.error(String.format("Failed to send the message to the %s queue [%s].",queueName.toString(),e.getMessage()));
        }

    }


}
