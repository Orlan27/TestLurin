package com.zed.type.load.services;

import com.zed.lurin.domain.jpa.TypeLoad;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public interface ITypeLoadServices {
    /**
     * <p>method that creates a channel package</p>
     * @param typeLoad
     * @return id self-generated
     */
    long createTypeLoad(TypeLoad typeLoad, String jndiNameDs);

    /**
     * <p>method that updates a channel package</p>
     * @param typeLoad
     * @return Object {@link TypeLoad }
     */
    TypeLoad updateTypeLoad(TypeLoad typeLoad, String jndiNameDs);

    /**
     * <p>method that obtain all type load</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link TypeLoad }
     */
    List<TypeLoad> getAllTypeLoad(String jndiNameDs);

    /**
     * <p>method that obtain a type load</p>
     *
     * @param typeLoadId
     * @return Object {@link TypeLoad }
     */
    TypeLoad getFindByIdTypeLoad(long typeLoadId, String jndiNameDs);

    /**
     * <p>method that delete a channel package</p>
     *
     * @param typeLoadId
     * @return void
     */
    void deleteTypeLoad(long typeLoadId, String jndiNameDs);

}
