package com.zed.channel.packages.services.impl;


import com.zed.channel.packages.services.IChannelPackageServices;
import com.zed.lurin.domain.jpa.ChannelPackages;
import com.zed.lurin.domain.services.IEntityManagerCreateImpl;
import com.zed.lurin.domain.services.IStatusServices;

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
 * <p>Stateless where the methods that manage the channel packages are implemented</p>
 * @author Francisco Lujano
 * {@link IChannelPackageServices}
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ChannelPackageServicesImpl implements IChannelPackageServices {

    private static Logger LOGGER = Logger.getLogger(ChannelPackageServicesImpl.class.getName());

    /**
     * Entity Manager reference.
     */
    private EntityManager em;

    /**
     * Services the status methods
     */
    @EJB
    IStatusServices iStatusServices;


    /**
     * Service that creates the EntityManager
     */
    @EJB
    IEntityManagerCreateImpl createEntityManagerFactory;

    /**
     * <p>method that creates a channel package</p>
     * @param channelPackages
     * @return id self-generated
     */
    @Override
    public long createChannelPackages(ChannelPackages channelPackages, String jndiNameDs){

        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();

        try{
            this.em.getTransaction().begin();
            channelPackages.setStatus(this.iStatusServices.getCategoryActive(jndiNameDs));
            this.em.persist(channelPackages);
            this.em.getTransaction().commit();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return channelPackages.getCode();
    }

    /**
     * <p>method that updates a channel package</p>
     * @param channelPackages
     * @return Object {@link ChannelPackages }
     */
    @Override
    public ChannelPackages updateChannelPackages(ChannelPackages channelPackages, String jndiNameDs){
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();

        try{
            this.em.getTransaction().begin();
            this.em.merge(channelPackages);
            this.em.getTransaction().commit();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return channelPackages;
    }

    /**
     * <p>method that deactivate a channel package</p>
     * @param channelPackagesId
     * @return  Object {@link ChannelPackages }
     */
    @Override
    public ChannelPackages deactivateChannelPackages(long channelPackagesId, String jndiNameDs){
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        ChannelPackages channelPackages = null;
        try{
            this.em.getTransaction().begin();
            channelPackages = this.em.find(ChannelPackages.class, channelPackagesId);
            channelPackages.setStatus(this.iStatusServices.getCategoryInactive(jndiNameDs));
            this.em.merge(channelPackages);
            this.em.getTransaction().commit();

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return channelPackages;
    }

    /**
     * <p>method that obtain all channel packages</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link ChannelPackages }
     */
    @Override
    public List<ChannelPackages> getAllChannelPackages(String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        List<ChannelPackages> channelPackages =  new ArrayList<>();
        try{
            this.em.getTransaction().begin();
            TypedQuery<ChannelPackages> query =
                    this.em.createQuery("SELECT cz FROM ChannelPackages cz", ChannelPackages.class);
            channelPackages.addAll(query.getResultList());
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return channelPackages;
    }

    /**
     * <p>method that obtain a channel packages</p>
     *
     * @param channelPackagesId
     * @return Object {@link ChannelPackages }
     */
    @Override
    public ChannelPackages getFindByIdChannelPackages(long channelPackagesId, String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        ChannelPackages channelPackages = null;
        try{
            this.em.getTransaction().begin();
            channelPackages = this.em.find(ChannelPackages.class, channelPackagesId);
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return channelPackages;
    }

    /**
     * <p>method that delete a channel package</p>
     *
     * @param channelPackagesId
     * @return void
     */
    @Override
    public void deleteChannelPackages(long channelPackagesId, String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try{
            this.em.getTransaction().begin();
            ChannelPackages channelPackages = this.em.find(ChannelPackages.class, channelPackagesId);
            this.em.remove(channelPackages);
            this.em.getTransaction().commit();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
    }

}
