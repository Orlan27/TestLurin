package com.zed.technologies.rest;

import com.zed.lurin.domain.jpa.CategoryTechnologies;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * <p>Abstract Class which defines the methods manage the technologies are implemented</p>
 * @author Francisco Lujano
 */
public interface ITechnologiesServicesRest {

    /**
     * <p>method that creates a technologies</p>
     * @param categoryTechnologies
     * @return {@javax.ws.rs.core.Response}
     */
    Response createCategoryTechnologies(CategoryTechnologies categoryTechnologies);

    /**
     * <p>method that updates a technologies</p>
     * @param categoryTechnologies
     * @return {@javax.ws.rs.core.Response}
     */
    Response updateCategoryTechnologies(CategoryTechnologies categoryTechnologies);

    /**
     * <p>method that obtain all technologies</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getAllCategoryTechnologies();

    /**
     * <p>>method that obtain a technologies</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getFindByIdCategoryTechnologies(@PathParam("id") long technologiesCode);

    /**
     * <p>method that delete a technologies</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response deleteCategoryTechnologies(@PathParam("id") long technologiesCode);

}
