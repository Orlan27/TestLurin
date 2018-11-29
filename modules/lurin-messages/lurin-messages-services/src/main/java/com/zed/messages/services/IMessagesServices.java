package com.zed.messages.services;

import com.zed.lurin.domain.jpa.ChannelMap;
import com.zed.lurin.domain.jpa.Messages;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * <p>Abstract Class which defines the methods manage the Messages are implemented</p>
 * @author Francisco Lujano
 */
public interface IMessagesServices {


    /**
     * <p>method that obtain all Messages</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link ChannelMap }
     */
    List<Messages> getAllMessages(String jndiNameDs);

}
