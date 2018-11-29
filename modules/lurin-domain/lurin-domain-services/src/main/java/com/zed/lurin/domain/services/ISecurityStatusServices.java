package com.zed.lurin.domain.services;

import com.zed.lurin.domain.jpa.SecurityCategoryStatus;

/**
 * <p>Abstract Class where the methods that manage the status are implemented</p>
 * @author Francisco Lujano
 */
public interface ISecurityStatusServices {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.domain.services/SecurityStatusServicesImpl!com.zed.lurin.domain.services.ISecurityStatusServices";

    /**
     *
     * <p>method that returns the persisted active status</p>
     *
     * @return {@link SecurityCategoryStatus}
     */
    SecurityCategoryStatus getCategoryActive();

    /**
     *
     * <p>method that returns the persisted inactive status</p>
     *
     * @return {@link SecurityCategoryStatus}
     */
    SecurityCategoryStatus getCategoryInactive();

    /**
     *
     * <p>method that returns the requested statuss</p>
     *
     * @return {@link SecurityCategoryStatus}
     */
    SecurityCategoryStatus getCategoryByName(String status);
}
