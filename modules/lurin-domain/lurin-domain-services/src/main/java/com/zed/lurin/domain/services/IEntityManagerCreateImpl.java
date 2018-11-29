package com.zed.lurin.domain.services;

import javax.persistence.EntityManagerFactory;

public interface IEntityManagerCreateImpl {
    /**
     * Method that creates the EntityManager by jndiName
     * @param jndiName
     * @return
     */
    EntityManagerFactory createEntityManagerFactory(String jndiName);
}
