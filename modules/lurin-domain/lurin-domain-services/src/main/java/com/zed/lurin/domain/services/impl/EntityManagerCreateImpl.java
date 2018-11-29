package com.zed.lurin.domain.services.impl;

import com.zed.lurin.domain.services.IEntityManagerCreateImpl;
import org.hibernate.ejb.HibernatePersistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Stateless where the methods that manage the entity manager are implemented</p>
 * @author Francisco Lujano
 */
@Stateless
public class EntityManagerCreateImpl implements IEntityManagerCreateImpl {


    /**
     * Method that creates the EntityManager by jndiName
     * @param jndiName
     * @return
     */
    @Override
    public EntityManagerFactory createEntityManagerFactory(String jndiName) {

        Map map = new HashMap();
        map.put(HibernatePersistence.JTA_DATASOURCE, jndiName);

        return new HibernatePersistence().createEntityManagerFactory("lurin-freeview-em",map);
    }

}
