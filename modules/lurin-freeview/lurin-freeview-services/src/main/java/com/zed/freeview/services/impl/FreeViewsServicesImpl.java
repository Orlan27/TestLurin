package com.zed.freeview.services.impl;


import com.zed.channel.packages.services.IChannelPackageServices;
import com.zed.freeview.services.IFreeViewsServices;
import com.zed.lurin.domain.jpa.ChannelPackages;
import com.zed.lurin.domain.jpa.FreeView;
import com.zed.lurin.domain.services.IEntityManagerCreateImpl;
import com.zed.lurin.domain.services.IStatusServices;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.*;
import java.util.*;

/**
 * <p>Stateless where the methods that manage the free views are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.freeview.services.IFreeViewsServices}
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class FreeViewsServicesImpl implements IFreeViewsServices {

    private static Logger LOGGER = Logger.getLogger(FreeViewsServicesImpl.class.getName());
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
     * Services the channel packages
     */
    @EJB
    IChannelPackageServices iChannelPackageServices;

    /**
     * Service that creates the EntityManager
     */
    @EJB
    IEntityManagerCreateImpl createEntityManagerFactory;


    /**
     * <p>method that creates a free view</p>
     * @param freeView
     * @return id self-generated
     */
    @Override
    public long createFreeView(FreeView freeView, String jndiNameDs){

        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();

        try {
            this.em.getTransaction().begin();
            freeView.setStatus(this.iStatusServices.getCategoryActive(jndiNameDs));
            Set<ChannelPackages> channelPackagesData = new HashSet<ChannelPackages>();
            freeView.getChannelPackages().stream().forEach(channelPackages -> {
                ChannelPackages channelPackage = this.em.find(ChannelPackages.class, channelPackages.getCode());
                channelPackagesData.add(channelPackage);
            });
            freeView.setChannelPackages(channelPackagesData);
            this.em.persist(freeView);
            this.em.getTransaction().commit();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return freeView.getFreeViewId();
    }



    /**
     * <p>method that updates a free view</p>
     * @param freeView
     * @return Object {@link FreeView }
     */
    @Override
    public FreeView updateFreeView(FreeView freeView, String jndiNameDs){
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();

        try{
            this.em.getTransaction().begin();

            Set<ChannelPackages> channelPackagesData = new HashSet<ChannelPackages>();
            freeView.getChannelPackages().stream().forEach(channelPackages -> {
                ChannelPackages channelPackage = this.em.find(ChannelPackages.class, channelPackages.getCode());
                channelPackagesData.add(channelPackage);
            });
            freeView.setChannelPackages(channelPackagesData);

            this.em.merge(freeView);
            this.em.getTransaction().commit();


        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return freeView;
    }

    /**
     * <p>method that deactivate a free view</p>
     * @param freeViewId
     * @return  Object {@link FreeView }
     */
    @Override
    public FreeView deactivateFreeView(long freeViewId, String jndiNameDs){
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        FreeView freeView = null;
        try{
            this.em.getTransaction().begin();

            TypedQuery<FreeView> query =
                    this.em.createQuery("SELECT DISTINCT cz FROM FreeView cz " +
                            "left join fetch cz.channelPackages WHERE cz.freeViewId=:freeViewId ", FreeView.class);

            query.setParameter("freeViewId", freeViewId);

            freeView = query.getSingleResult();

            freeView.setStatus(this.iStatusServices.getCategoryInactive(jndiNameDs));
            this.em.merge(freeView);
            this.em.getTransaction().commit();

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return freeView;
    }

    /**
     * <p>method that obtain all free views</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link FreeView }
     */
    @Override
    public List<FreeView> getAllFreeViews(String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        List<FreeView> freeViews =  new ArrayList<>();
        try{
            this.em.getTransaction().begin();
            TypedQuery<FreeView> query =
                    this.em.createQuery("SELECT f FROM FreeView f", FreeView.class);

            freeViews.addAll(query.getResultList());

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return freeViews;
    }

    /**
     * <p>method that obtain a free views</p>
     *
     * @param freeViewId
     * @return Object {@link FreeView }
     */
    @Override
    public FreeView getFindByIdFreeViews(long freeViewId, String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        FreeView freeView = null;
        try{
            this.em.getTransaction().begin();

            TypedQuery<FreeView> query =
                    this.em.createQuery("SELECT DISTINCT cz FROM FreeView cz " +
                            "left join fetch cz.channelPackages WHERE cz.freeViewId=:freeViewId ", FreeView.class);

            query.setParameter("freeViewId", freeViewId);

            freeView = query.getSingleResult();

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return freeView;
    }

    /**
     * <p>method that delete a free view</p>
     *
     * @param freeViewId
     * @return void
     */
    @Override
    public void deleteFreeView(long freeViewId, String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try{
            this.em.getTransaction().begin();

            TypedQuery<FreeView> query =
                    this.em.createQuery("SELECT DISTINCT cz FROM FreeView cz " +
                            "left join fetch cz.channelPackages WHERE cz.freeViewId=:freeViewId ", FreeView.class);

            query.setParameter("freeViewId", freeViewId);

            FreeView freeView = query.getSingleResult();
            this.em.remove(freeView);
            this.em.getTransaction().commit();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return;
    }

}
