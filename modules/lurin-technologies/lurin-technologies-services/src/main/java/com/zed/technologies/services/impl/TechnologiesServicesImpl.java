package com.zed.technologies.services.impl;


import com.zed.technologies.services.ITechnologiesServices;
import com.zed.lurin.domain.jpa.CategoryTechnologies;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * <p>Stateless where the methods that manage the technologies are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.technologies.services.ITechnologiesServices}
 */
@Stateless
public class TechnologiesServicesImpl implements ITechnologiesServices {

    private static Logger LOGGER = Logger.getLogger(TechnologiesServicesImpl.class.getName());

    /**
     * Entity Manager reference.
     */
    @PersistenceContext(unitName = "lurin-security-em")
    private EntityManager em;

    /**
     * <p>method that creates a technologies</p>
     * @param technologies
     * @return id self-generated
     */
    @Override
    public long createCategoryTechnologies(CategoryTechnologies technologies){
        this.em.persist(technologies);
        return technologies.getCode();
    }

    /**
     * <p>method that updates a technologies</p>
     * @param technologies
     * @return Object {@link com.zed.lurin.domain.jpa.CategoryTechnologies}
     */
    @Override
    public CategoryTechnologies updateCategoryTechnologies(CategoryTechnologies technologies){
        this.em.merge(technologies);
        return technologies;
    }

    /**
     * <p>method that obtain all technologies</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link com.zed.lurin.domain.jpa.CategoryTechnologies }
     */
    @Override
    public List<CategoryTechnologies> getAllCategoryTechnologies() {
        TypedQuery<CategoryTechnologies> query =
                this.em.createQuery("SELECT cz FROM CategoryTechnologies cz", CategoryTechnologies.class);
        return query.getResultList();
    }

    /**
     * <p>method that obtain a technologies</p>
     *
     * @param technologiesCode
     * @return Object {@link com.zed.lurin.domain.jpa.CategoryTechnologies }
     */
    @Override
    public CategoryTechnologies getFindByIdCategoryTechnologies(long technologiesCode) {
        CategoryTechnologies categoryTechnologies = this.em.find(CategoryTechnologies.class, technologiesCode);;
        return categoryTechnologies;

    }

    /**
     * <p>method that delete a technologies</p>
     *
     * @param technologiesCode
     * @return void
     */
    @Override
    public void deleteCategoryTechnologies(long technologiesCode) {
        CategoryTechnologies technologies = this.em.find(CategoryTechnologies.class, technologiesCode);
        this.em.remove(technologies);
    }
}
