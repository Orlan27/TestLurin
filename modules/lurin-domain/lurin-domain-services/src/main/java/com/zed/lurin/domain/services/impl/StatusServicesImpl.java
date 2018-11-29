package com.zed.lurin.domain.services.impl;

import com.zed.lurin.domain.jpa.CategoryStatus;
import com.zed.lurin.domain.services.IEntityManagerCreateImpl;
import com.zed.lurin.domain.services.IStatusServices;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * <p>Stateless where the methods that manage the status are implemented</p>
 * @author Francisco Lujano
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class StatusServicesImpl implements IStatusServices {

    private static Logger LOGGER = Logger.getLogger(StatusServicesImpl.class.getName());

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
     *
     * <p>method that returns the persisted active status</p>
     * @param jndiNameDs
     * @return {@link com.zed.lurin.domain.jpa.CategoryStatus}
     */
    @Override
    public CategoryStatus getCategoryActive(String jndiNameDs){
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        CategoryStatus categoryStatus = null;
        try{
            this.em.getTransaction().begin();
            TypedQuery<CategoryStatus> query =
                    em.createQuery("SELECT cs " +
                            "FROM CategoryStatus cs " +
                            "WHERE cs.status = :status", CategoryStatus.class);
            query.setParameter("status",CategoryStatus.STATUS.ACTIVE.toString());
            categoryStatus = query.getSingleResult();

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return categoryStatus;
    }

    /**
     *
     * <p>method that returns the persisted inactive status</p>
     * @param jndiNameDs
     * @return {@link com.zed.lurin.domain.jpa.CategoryStatus}
     */
    @Override
    public CategoryStatus getCategoryInactive(String jndiNameDs){

        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        CategoryStatus categoryStatus = null;
        try{
            this.em.getTransaction().begin();
            TypedQuery<CategoryStatus> query =
                    em.createQuery("SELECT cs " +
                            "FROM CategoryStatus cs " +
                            "WHERE cs.status = :status", CategoryStatus.class);

            query.setParameter("status",CategoryStatus.STATUS.INACTIVE.toString());
            categoryStatus = query.getSingleResult();

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }


        return categoryStatus;

    }

    /**
     *
     * <p>method that returns the requested statuss</p>
     * @param jndiNameDs
     * @return {@link com.zed.lurin.domain.jpa.CategoryStatus}
     */
    @Override
    public CategoryStatus getCategoryByName(String status, String jndiNameDs){

        CategoryStatus  categoryStatus = null;
        try{
            this.em.getTransaction().begin();
            TypedQuery<CategoryStatus> query =
                    em.createQuery("SELECT cs " +
                            "FROM CategoryStatus cs " +
                            "WHERE cs.status = :status", CategoryStatus.class);

            query.setParameter("status",status);

            categoryStatus = query.getSingleResult();

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }



        return categoryStatus;
    }
}
