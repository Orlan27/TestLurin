package com.zed.lurin.domain.services;

import com.zed.lurin.domain.jpa.CategoryStatus;

/**
 * <p>Abstract Class where the methods that manage the status are implemented</p>
 * @author Francisco Lujano
 */
public interface IStatusServices {

    /**
     *
     * <p>method that returns the persisted active status</p>
     * @param jndiNameDs
     * @return {@link com.zed.lurin.domain.jpa.CategoryStatus}
     */
    CategoryStatus getCategoryActive(String jndiNameDs);

    /**
     *
     * <p>method that returns the persisted inactive status</p>
     * @param jndiNameDs
     * @return {@link com.zed.lurin.domain.jpa.CategoryStatus}
     */
    CategoryStatus getCategoryInactive(String jndiNameDs);

    /**
     *
     * <p>method that returns the requested statuss</p>
     * @param jndiNameDs
     * @return {@link com.zed.lurin.domain.jpa.CategoryStatus}
     */
    CategoryStatus getCategoryByName(String status, String jndiNameDs);
}
