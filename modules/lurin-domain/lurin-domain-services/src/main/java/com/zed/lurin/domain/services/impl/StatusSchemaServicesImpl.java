package com.zed.lurin.domain.services.impl;

import com.zed.lurin.domain.jpa.CategorySchStatus;
import com.zed.lurin.domain.services.IEntityManagerCreateImpl;
import com.zed.lurin.domain.services.IStatusSchemaServices;
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
 * <p>Stateless where the methods that manage the status are implemented</p>
 * @author Francisco Lujano
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class StatusSchemaServicesImpl implements IStatusSchemaServices{


    private static Logger LOGGER = Logger.getLogger(StatusSchemaServicesImpl.class.getName());

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
     * @return {@link CategorySchStatus}
     */
    @Override
    public CategorySchStatus getCategoryNew(String jndiNameDs){

        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        CategorySchStatus categorySchStatus = null;
        try{
            this.em.getTransaction().begin();
            TypedQuery<CategorySchStatus> query =
                    em.createQuery("SELECT cs " +
                            "FROM CategorySchStatus cs " +
                            "WHERE cs.status = :status", CategorySchStatus.class);
            query.setParameter("status", CategorySchStatus.STATUS.NEW.toString());

            categorySchStatus = query.getSingleResult();
            this.em.getTransaction().commit();

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return categorySchStatus;
    }

    /**
     *
     * <p>method that returns the persisted inactive status</p>
     * @param jndiNameDs
     * @return {@link CategorySchStatus}
     */
    @Override
    public CategorySchStatus getCategoryCanceled(String jndiNameDs){

        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        CategorySchStatus categorySchStatus = null;

        try{
            this.em.getTransaction().begin();
            TypedQuery<CategorySchStatus> query =
                    em.createQuery("SELECT cs " +
                            "FROM CategorySchStatus cs " +
                            "WHERE cs.status = :status", CategorySchStatus.class);

            query.setParameter("status", CategorySchStatus.STATUS.CANCELED.toString());
            categorySchStatus = query.getSingleResult();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return categorySchStatus;

    }

    /**
     *
     * <p>method that returns the requested statuss</p>
     * @param jndiNameDs
     * @return {@link CategorySchStatus}
     */
    @Override
    public CategorySchStatus getCategoryByName(String status, String jndiNameDs){

        CategorySchStatus categorySchStatus = null;
        try{
            this.em.getTransaction().begin();
            TypedQuery<CategorySchStatus> query =
                    em.createQuery("SELECT cs " +
                            "FROM CategorySchStatus cs " +
                            "WHERE cs.status = :status", CategorySchStatus.class);

            query.setParameter("status",status);
            categorySchStatus = query.getSingleResult();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }


        return categorySchStatus;
    }
    
    /**
     * <p>method that obtain all CategorySchStatus</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link CategorySchStatus }
     */
    @Override
    public List<CategorySchStatus> getAllCategorySchStatus(String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        List<CategorySchStatus> categorySchStatus =  new ArrayList<>();
        try{
            this.em.getTransaction().begin();
            TypedQuery<CategorySchStatus> query =
                    this.em.createQuery("SELECT c FROM CategorySchStatus c", CategorySchStatus.class);

            categorySchStatus.addAll(query.getResultList());

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return categorySchStatus;
    }
}
