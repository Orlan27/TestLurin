package com.zed.technologies.services;


import com.zed.lurin.domain.jpa.CategoryTechnologies;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * <p>Abstract Class which defines the methods manage the technologies are implemented</p>
 * @author Francisco Lujano
 */
public interface ITechnologiesServices {

    /**
     * <p>method that creates a technologies</p>
     * @param technologies
     * @return id self-generated*/
    long createCategoryTechnologies(CategoryTechnologies technologies);

    /**
     * <p>method that updates a technologies</p>
     * @param technologies
     * @return Object {@link com.zed.lurin.domain.jpa.CategoryTechnologies }*/
    CategoryTechnologies updateCategoryTechnologies(CategoryTechnologies technologies);


    /**
     * <p>method that obtain all technologiess</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.CategoryTechnologies }*/
    List<CategoryTechnologies> getAllCategoryTechnologies();

    /**
     * <p>method that obtain a technologiess</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.CategoryTechnologies }*/
    CategoryTechnologies getFindByIdCategoryTechnologies(long technologiesCode);

    /**
     * <p>method that delete a technologies</p>
     * @param technologiesCode
     * @return  Object {@link com.zed.lurin.domain.jpa.CategoryTechnologies }*/
    void deleteCategoryTechnologies(long technologiesCode);
}
