package com.zed.lurin.domain.services;

import com.zed.lurin.domain.jpa.CategorySchStatus;

import javax.persistence.EntityManager;

import java.util.List;

/**
 * <p>Abstract Class where the methods that manage the status are implemented</p>
 * @author Francisco Lujano
 */
public interface IStatusSchemaServices {

    /**
     *
     * <p>method that returns the persisted new status</p>
     * @param jndiNameDs
     * @return {@link CategorySchStatus}
     */
    CategorySchStatus getCategoryNew(String jndiNameDs);

    /**
     *
     * <p>method that returns the persisted canceled status</p>
     * @param jndiNameDs
     * @return {@link CategorySchStatus}
     */
    CategorySchStatus getCategoryCanceled(String jndiNameDs);

    /**
     *
     * <p>method that returns the requested statuss</p>
     * @param jndiNameDs
     * @return {@link CategorySchStatus}
     */
    CategorySchStatus getCategoryByName(String status, String jndiNameDs);
    
    /**
     * <p>method that obtain all CategorySchStatus</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link CategorySchStatus }
     */
    List<CategorySchStatus> getAllCategorySchStatus(String jndiNameDs);
}
