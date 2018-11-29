package com.zed.lurin.domain.services.impl;

import com.zed.lurin.domain.jpa.SecurityCategoryStatus;
import com.zed.lurin.domain.services.ISecurityStatusServices;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * <p>Stateless where the methods that manage the status are implemented</p>
 * @author Francisco Lujano
 */
@Stateless
public class SecurityStatusServicesImpl implements ISecurityStatusServices {

    /**
     * Entity Manager reference.
     */
    @PersistenceContext(unitName="lurin-security-em")
    private EntityManager em;

    /**
     *
     * <p>method that returns the persisted active status</p>
     *
     * @return {@link SecurityCategoryStatus}
     */
    @Override
    public SecurityCategoryStatus getCategoryActive(){
        TypedQuery<SecurityCategoryStatus> query =
                this.em.createQuery("SELECT cs " +
                        "FROM SecurityCategoryStatus cs " +
                        "WHERE cs.status = :status", SecurityCategoryStatus.class);

        query.setParameter("status",SecurityCategoryStatus.STATUS.ACTIVE.toString());
        return query.getSingleResult();
    }

    /**
     *
     * <p>method that returns the persisted inactive status</p>
     *
     * @return {@link SecurityCategoryStatus}
     */
    @Override
    public SecurityCategoryStatus getCategoryInactive(){
        TypedQuery<SecurityCategoryStatus> query =
                this.em.createQuery("SELECT cs " +
                        "FROM SecurityCategoryStatus cs " +
                        "WHERE cs.status = :status", SecurityCategoryStatus.class);

        query.setParameter("status",SecurityCategoryStatus.STATUS.INACTIVE.toString());
        return query.getSingleResult();
    }

    /**
     *
     * <p>method that returns the requested statuss</p>
     *
     * @return {@link SecurityCategoryStatus}
     */
    @Override
    public SecurityCategoryStatus getCategoryByName(String status){
        TypedQuery<SecurityCategoryStatus> query =
                this.em.createQuery("SELECT cs " +
                        "FROM SecurityCategoryStatus cs " +
                        "WHERE cs.status = :status", SecurityCategoryStatus.class);

        query.setParameter("status",status);
        return query.getSingleResult();
    }
}
