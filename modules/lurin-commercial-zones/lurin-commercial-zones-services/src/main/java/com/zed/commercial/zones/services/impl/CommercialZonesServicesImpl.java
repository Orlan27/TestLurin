package com.zed.commercial.zones.services.impl;


import com.zed.commercial.zones.services.ICommercialZonesServices;
import com.zed.lurin.domain.jpa.CommercialZone;
import com.zed.lurin.domain.services.IEntityManagerCreateImpl;
import com.zed.lurin.domain.services.IStatusServices;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Stateless where the methods that manage the commercial zones are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.commercial.zones.services.ICommercialZonesServices}
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CommercialZonesServicesImpl implements ICommercialZonesServices {

    private static Logger LOGGER = Logger.getLogger(CommercialZonesServicesImpl.class.getName());

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
     * <p>method that creates a commercial zone</p>
     * @param commercialZone
     * @return id self-generated
     */
    @Override
    public long createCommercialZone(CommercialZone commercialZone, String jndiNameDs){

        try{
            this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
            this.em.getTransaction().begin();
            commercialZone.setStatus(this.iStatusServices.getCategoryActive(jndiNameDs));
            this.em.persist(commercialZone);
            this.em.getTransaction().commit();

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return commercialZone.getZone();
    }

    /**
     * <p>method that updates a commercial zone</p>
     * @param commercialZone
     * @return Object {@link com.zed.lurin.domain.jpa.CommercialZone }
     */
    @Override
    public CommercialZone updateCommercialZone(CommercialZone commercialZone, String jndiNameDs){
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try{
            this.em.getTransaction().begin();
            this.em.merge(commercialZone);
            this.em.getTransaction().commit();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return commercialZone;
    }

    /**
     * <p>method that deactivate a commercial zone</p>
     * @param zoneCode
     * @return  Object {@link com.zed.lurin.domain.jpa.CommercialZone }
     */
    @Override
    public CommercialZone deactivateCommercialZone(long zoneCode, String jndiNameDs){
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();

        CommercialZone commercialZone = null;
        try{
            this.em.getTransaction().begin();
            commercialZone = this.em.find(CommercialZone.class, zoneCode);
            commercialZone.setStatus(this.iStatusServices.getCategoryInactive(jndiNameDs));
            this.em.merge(commercialZone);
            this.em.getTransaction().commit();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return commercialZone;

    }

    /**
     * <p>method that obtain all commercial zones</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link com.zed.lurin.domain.jpa.CommercialZone }
     */
    @Override
    public List<CommercialZone> getAllCommercialZones(String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        List<CommercialZone> commercialZones = new ArrayList<>();
        try{
            this.em.getTransaction().begin();
            TypedQuery<CommercialZone> query =
                    this.em.createQuery("SELECT cz FROM CommercialZone cz", CommercialZone.class);
            commercialZones.addAll(query.getResultList());
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return commercialZones;
    }

    /**
     * <p>method that obtain a commercial zones</p>
     *
     * @param zoneCode
     * @return Object {@link com.zed.lurin.domain.jpa.CommercialZone }
     */
    @Override
    public CommercialZone getFindByIdCommercialZones(long zoneCode, String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        CommercialZone commercialZone = null;
        try{
            this.em.getTransaction().begin();
            commercialZone = this.em.find(CommercialZone.class, zoneCode);
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return commercialZone;
    }

    /**
     * <p>method that delete a commercial zone</p>
     *
     * @param zoneCode
     * @return void
     */
    @Override
    public void deleteCommercialZone(long zoneCode, String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try{
            this.em.getTransaction().begin();
            CommercialZone commercialZone = this.em.find(CommercialZone.class, zoneCode);
            this.em.remove(commercialZone);
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
