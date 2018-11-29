package com.zed.lurin.parameter.rest.impl;

import com.zed.lurin.parameter.rest.ICoreParameterServicesRest;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.domain.jpa.CoreParameter;
import com.zed.lurin.domain.jpa.ParametersCategory;

import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.*;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.SecurityContext;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * <p>Stateless REST where the methods that manage the global parameters are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.storage.managment.rest.ICoreParameterServicesRest}
 */
@Path("/globalparameters")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/globalparameters", description = "Operation to manage global parameters",
        authorizations = {
        @Authorization(value = "bearer_token")
})
public class CoreParameterServicesRestImpl implements ICoreParameterServicesRest {

	/**
     * Context Security
     */
    @Context
    SecurityContext securityContext;
    
	@EJB
    ICoreParameterServices iCoreParameterServices;


    /**
     * <p>method that updates a core parameter</p>
     * @param coreParameter
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.GLOBAL_PARAMETERS, Roles.GLOBAL_PARAMETERS_UPDATE})
    @Override
    @ApiOperation(value = "Update CoreParameter",
            notes = "This method updates a core parameter and if you update it correctly it returns the parameter updated",
            response = CoreParameter.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update CoreParameter Correctly")
    })
    public Response updateCoreParameter(CoreParameter coreParameter) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iCoreParameterServices.updateCoreParameter(coreParameter))
                    .build();
        }catch (IllegalArgumentException ex) {
            response = Response.ok()
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();

        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }

        return response;
    }

    /**
     * <p>method that obtain all core parameters</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.GLOBAL_PARAMETERS, Roles.GLOBAL_PARAMETERS_GET})
    @Override
    @ApiOperation(value = "List all Core Parameters in the database",
            notes = "List all Core Parameters in the database for global parameters administration",
            response = CoreParameter.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all CoreParameter Correctly")
    })
    public Response getAllCoreParameter() {
    	Response response;
        try {
            List<CoreParameter> listcoreParameter = this.iCoreParameterServices.getListCoreParameterbyCategory(Keys.GLOBAL_PARAMETERS_CATEGORY.toString());
            if(listcoreParameter.stream().findFirst().isPresent()){

                response = Response.ok().entity(listcoreParameter).build();
            }else{
                response = Response.ok().status(Response.Status.FORBIDDEN).build();
            }

        }catch (IllegalArgumentException ex) {
            response = Response.ok()
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();

        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }

        return response;
    }

    /**
     * <p>>method that obtain a core parameter</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.GLOBAL_PARAMETERS, Roles.GLOBAL_PARAMETERS_GET})
    @Override
    @ApiOperation(value = "Show a core parameter in the database",
            notes = "Show a core parameter in the database for global parameter administration",
            response = CoreParameter.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show CoreParameter Correctly")
    })
    public Response getFindByCodeParameter(
    		@ApiParam(value = "Code of the core parameter to find by code", required = true)
    		@PathParam("code") String code) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iCoreParameterServices.getFindByCodeParameter(code))
                    .build();

        }catch (IllegalArgumentException ex) {
            response = Response.ok()
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();

        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }

        return response;
    }
    
    /**
     * <p>method that obtain all parameters categories</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getAllCategories")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.GLOBAL_PARAMETERS, Roles.GLOBAL_PARAMETERS_GET})
    @Override
    @ApiOperation(value = "List all parameters categories in the database",
            notes = "List all parameters categories in the database for global parameters administration",
            response = ParametersCategory.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all ParametersCategory Correctly")
    })
    public Response getAllParametersCategories() {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iCoreParameterServices.getAllParametersCategories())
                    .build();

        }catch (IllegalArgumentException ex) {
            response = Response.ok()
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();

        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }

        return response;
    }


}
