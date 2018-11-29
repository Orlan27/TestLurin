package com.zed.lurin.process.cas.services;

import com.zed.lurin.domain.jpa.Provisioning;

import java.util.List;

public interface IProvisioningServicesImpl {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.process.cas.services/ProvisioningServicesImpl!com.zed.lurin.process.cas.services.IProvisioningServicesImpl";

    /**
     *
     * method to return the provisioning list
     *
     * @param members
     * @param carriersId
     * @return
     */
    List<Provisioning> getProvisioningMembersByCarrier(List<Long> members, long carriersId,  int initBatch, int endBatch);


    /**
     *
     * method to return the provisioning list
     * @param carriersId
     * @return
     */
    List<Provisioning> getProvisioningByCarrier(long carriersId, int initBatch, int endBatch);

    /**
     * <p>method that delete a provisioning</p>
     * @param code
     * @return  void
     */
    void deleteProvisioning(long code);

    /**
     *
     * method to return the count provisioning
     * @param carriersId
     * @return
     */
    long totalProvisioningMembersByCarrier(long carriersId);
}
