package com.zed.type.load.services.impl;


import com.zed.lurin.domain.jpa.TypeLoad;
import com.zed.type.load.services.ITypeLoadServices;
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
 * <p>Stateless where the methods that manage the type load are implemented</p>
 * @author Francisco Lujano
 * {@link ITypeLoadServices}
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TypeLoadServicesImpl implements ITypeLoadServices {

    private static Logger LOGGER = Logger.getLogger(TypeLoadServicesImpl.class.getName());

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
     * @param typeLoad
     * @return id self-generated
     */
    @Override
    public long createTypeLoad(TypeLoad typeLoad, String jndiNameDs){

        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();

        try{
            this.em.getTransaction().begin();
            this.em.persist(typeLoad);
            this.em.getTransaction().commit();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return typeLoad.getCode();
    }

    /**
     * <p>method that updates a channel package</p>
     * @param typeLoad
     * @return Object {@link TypeLoad }
     */
    @Override
    public TypeLoad updateTypeLoad(TypeLoad typeLoad, String jndiNameDs){
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();

        try{
            this.em.getTransaction().begin();
            this.em.merge(typeLoad);
            this.em.getTransaction().commit();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return typeLoad;
    }


    /**
     * <p>method that obtain all type load</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link TypeLoad }
     */
    @Override
    public List<TypeLoad> getAllTypeLoad(String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        List<TypeLoad> typeLoad =  new ArrayList<>();
        try{
            this.em.getTransaction().begin();
            TypedQuery<TypeLoad> query =
                    this.em.createQuery("SELECT cz FROM TypeLoad cz", TypeLoad.class);
            typeLoad.addAll(query.getResultList());
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return typeLoad;
    }

    /**
     * <p>method that obtain a type load</p>
     *
     * @param typeLoadId
     * @return Object {@link TypeLoad }
     */
    @Override
    public TypeLoad getFindByIdTypeLoad(long typeLoadId, String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        TypeLoad typeLoad = null;
        try{
            this.em.getTransaction().begin();
            typeLoad = this.em.find(TypeLoad.class, typeLoadId);
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return typeLoad;
    }

    /**
     * <p>method that delete a channel package</p>
     *
     * @param typeLoadId
     * @return void
     */
    @Override
    public void deleteTypeLoad(long typeLoadId, String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try{
            this.em.getTransaction().begin();
            TypeLoad typeLoad = this.em.find(TypeLoad.class, typeLoadId);
            this.em.remove(typeLoad);
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
