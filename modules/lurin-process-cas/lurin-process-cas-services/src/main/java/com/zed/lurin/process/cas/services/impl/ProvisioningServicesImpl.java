package com.zed.lurin.process.cas.services.impl;

import com.zed.lurin.domain.jpa.Provisioning;
import com.zed.lurin.process.cas.services.IProvisioningServicesImpl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


/**
 * <p>Stateless where the methods that manage the provisioning are implemented</p>
 * @author Francisco Lujano
 */
@Stateless
public class ProvisioningServicesImpl implements IProvisioningServicesImpl {

    /**
     * Entity Manager reference.
     */
    @PersistenceContext(unitName="lurin-admin-em")
    private EntityManager em;


    /**
     *
     * method to return the provisioning list
     *
     * @param members
     * @param carriersId
     * @return
     */
    @Override
    public List<Provisioning> getProvisioningMembersByCarrier(List<Long> members, long carriersId,  int initBatch, int endBatch){
        TypedQuery<Provisioning> query = this.em.createQuery("SELECT p " +
                "FROM Provisioning p " +
                "WHERE p.carriersId = :carriersId " +
                "AND p.memberId IN :members", Provisioning.class);

        query.setParameter("carriersId",carriersId);
        query.setParameter("members",members);

        if(initBatch>0 && endBatch>0){
            query.setFirstResult(initBatch);
            query.setMaxResults(endBatch);
        }

        return query.getResultList();

    }

    /**
     *
     * method to return the provisioning list
     * @param carriersId
     * @return
     */
    @Override
    public List<Provisioning> getProvisioningByCarrier(long carriersId, int initBatch, int endBatch){
        TypedQuery<Provisioning> query = this.em.createQuery("SELECT p " +
                "FROM Provisioning p " +
                "WHERE p.carriersId = :carriersId ", Provisioning.class);

        query.setParameter("carriersId",carriersId);
        if(initBatch>0 && endBatch>0){
            query.setFirstResult(initBatch);
            query.setMaxResults(endBatch);
        }
        return query.getResultList();

    }


    /**
     *
     * method to return the provisioning list
     * @param carriersId
     * @return
     */
    @Override
    public long totalProvisioningMembersByCarrier(long carriersId){

        TypedQuery<Long> query = this.em.createQuery("SELECT COUNT(p) " +
                "FROM Provisioning p " +
                "WHERE p.carriersId = :carriersId ", Long.class);

        query.setParameter("carriersId",carriersId);

        return query.getSingleResult();

    }


    /**
     * <p>method that delete a provisioning</p>
     * @param code
     * @return  void
     */
    @Override
    public void deleteProvisioning(long code) {
        Provisioning provisioning = this.em.find(Provisioning.class, code);
        this.em.remove(provisioning);
        this.em.flush();
    }

}
