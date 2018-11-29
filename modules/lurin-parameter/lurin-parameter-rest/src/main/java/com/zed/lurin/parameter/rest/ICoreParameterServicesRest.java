package com.zed.lurin.parameter.rest;

import com.zed.lurin.domain.jpa.CoreParameter;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>Abstract Class which defines the methods that manage the global parameters are implemented</p>
 * @author Francisco Lujano
 */
public interface ICoreParameterServicesRest {

    /**
     * <p>method that updates a core parameter</p>
     * @param coreParameter
     * @return {@javax.ws.rs.core.Response}
     */
    Response updateCoreParameter(CoreParameter coreParameter);

    /**
     * <p>method that obtain all core parameters</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getAllCoreParameter();

    /**
     * <p>>method that obtain a core parameter</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getFindByCodeParameter(@PathParam("code") String code);
    
    /**
     * <p>method that obtain all parameters categories</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getAllParametersCategories();

}
