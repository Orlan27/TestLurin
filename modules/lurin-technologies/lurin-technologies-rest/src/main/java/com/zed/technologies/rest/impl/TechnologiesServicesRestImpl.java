package com.zed.technologies.rest.impl;

import com.zed.lurin.domain.jpa.CategoryTechnologies;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;
import com.zed.technologies.rest.ITechnologiesServicesRest;
import com.zed.technologies.services.ITechnologiesServices;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;


/**
 * <p>Stateless REST where the methods that manage the technologies are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.technologies.rest.ITechnologiesServicesRest}
 */
@Path("/technologies")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/technologies", description = "Operation to manage technologies",
        authorizations = {
                @Authorization(value = "bearer_token")
        })
public class TechnologiesServicesRestImpl implements ITechnologiesServicesRest {


    private static Logger LOGGER = Logger.getLogger(TechnologiesServicesRestImpl.class.getName());

    /**
     * Context Security
     */
    @Context
    SecurityContext securityContext;

    @EJB
    ITechnologiesServices iTechnologiesServices;

    /**
     * <p>method that creates a commercial zone</p>
     *
     * @param categoryTechnologies
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @Override
    @ApiOperation(value = "Create Category Technologies",
            notes = "The method creates a commercial zone and if it is created correctly it returns the generated id",
            response = CategoryTechnologies.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create Category Technologies Correctly")
    })
    public Response createCategoryTechnologies(@ApiParam(value = "Create Category Technologies object", required = true) CategoryTechnologies categoryTechnologies) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iTechnologiesServices.createCategoryTechnologies(categoryTechnologies))
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
     * <p>method that updates a commercial zone</p>
     *
     * @param categoryTechnologies
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @Override
    @ApiOperation(value = "Update Category Technologies",
            notes = "The method update a commercial zone and if you update the commercial zone correctly it returns the data updated",
            response = CategoryTechnologies.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update Category Technologies Correctly")
    })
    public Response updateCategoryTechnologies(@ApiParam(value = "Update Category Technologies object", required = true) CategoryTechnologies categoryTechnologies) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iTechnologiesServices.updateCategoryTechnologies(categoryTechnologies))
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
     * <p>method that obtain all technologies</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE, Roles.CAMPAIGN, Roles.CAMPAIGN_GET,
            Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all Category Technologies in the database",
            notes = "List all Category Technologies in the database for Category Technologies administration",
            response = CategoryTechnologies.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all Category Technologies Correctly")
    })
    public Response getAllCategoryTechnologies() {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iTechnologiesServices.getAllCategoryTechnologies())
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
     * <p>>method that obtain a technologies</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @Override
    @ApiOperation(value = "Show Category Technologies in the database",
            notes = "Show Category Technologies in the database for Category Technologies administration",
            response = CategoryTechnologies.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show Category Technologies Correctly")
    })
    public Response getFindByIdCategoryTechnologies(
           @ApiParam(value = "Id of the Category Technologies to find by id", required = true)
           @PathParam("id") long technologiesCode) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iTechnologiesServices.getFindByIdCategoryTechnologies(technologiesCode))
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
     * <p>method that delete a commercial zone</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @Override
    @ApiOperation(value = "Delete Category Technologies in the database",
            notes = "Delete Category Technologies in the database for Category Technologies administration",
            response = CategoryTechnologies.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show Category Technologies Correctly")
    })
    public Response deleteCategoryTechnologies(
            @ApiParam(value = "Id of the Category Technologies to delete by id", required = true)
            @PathParam("id") long technologiesCode) {
        Response response;
        try {
            this.iTechnologiesServices.deleteCategoryTechnologies(technologiesCode);
            response = Response.ok().build();

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
