package com.zed.messages.services.impl;


import com.zed.lurin.domain.jpa.ChannelMap;
import com.zed.lurin.domain.jpa.Messages;
import com.zed.lurin.domain.services.IEntityManagerCreateImpl;
import com.zed.messages.services.IMessagesServices;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * <p>Stateless where the methods that manage the Messages are implemented</p>
 * @author Francisco Lujano
 * {@link IMessagesServices}
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class MessagesServicesImpl implements IMessagesServices {

    private static Logger LOGGER = Logger.getLogger(MessagesServicesImpl.class.getName());

    /**
     * Entity Manager reference.
     */
    private EntityManager em;

    /**
     * Service that creates the EntityManager
     */
    @EJB
    IEntityManagerCreateImpl createEntityManagerFactory;


    /**
     * <p>method that obtain all Messages</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link ChannelMap }
     */
    @Override
    public List<Messages> getAllMessages(String jndiNameDs) {

        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        List<Messages> messages = new ArrayList<>();
        try {
            this.em.getTransaction().begin();
            TypedQuery<Messages> query =
                    this.em.createQuery("SELECT cm FROM Messages cm", Messages.class);

            messages.addAll(query.getResultList());

        } finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }

        }
        return messages;
    }


}
