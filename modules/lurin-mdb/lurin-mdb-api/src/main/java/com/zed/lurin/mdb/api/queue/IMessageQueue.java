package com.zed.lurin.mdb.api.queue;

import com.zed.lurin.mdb.api.dto.CampaignGenericMapping;
import com.zed.lurin.mdb.api.keys.QueueNameKeys;

public interface IMessageQueue {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.mdb.services/MessageQueueAPI!com.zed.lurin.mdb.api.queue.IMessageQueue";

    /**
     *
     * This method send a message to the given queue.
     *
     * @param queueName
     * @param campaignGenericMapping
     */
    void send(QueueNameKeys queueName, CampaignGenericMapping campaignGenericMapping);
}
