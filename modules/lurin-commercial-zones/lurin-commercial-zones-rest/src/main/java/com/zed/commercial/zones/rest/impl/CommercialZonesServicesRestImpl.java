package com.zed.commercial.zones.rest.impl;

import com.zed.commercial.zones.rest.ICommercialZonesServicesRest;
import com.zed.commercial.zones.services.ICommercialZonesServices;
import com.zed.lurin.domain.jpa.CommercialZone;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.zed.lurin.security.filters.services.DynamicJPA;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.filters.services.bean.JpaDynamicBean;
import com.zed.lurin.security.keys.Roles;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;


/**
 * <p>Stateless REST where the methods that manage the commercial zones are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.commercial.zones.rest.ICommercialZonesServicesRest}
 */
@Path("/commercial")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/commercial", description = "Operation to manage commercial zones",
        authorizations = {
                @Authorization(value = "bearer_token")
        })
public class CommercialZonesServicesRestImpl implements ICommercialZonesServicesRest {


    private static Logger LOGGER = Logger.getLogger(CommercialZonesServicesRestImpl.class.getName());

    /**
     * Context Security
     */
    @Context
    SecurityContext securityContext;

    @EJB
    ICommercialZonesServices iCommercialZonesServices;

    @Inject
    JpaDynamicBean jpaDynamicBean;

    /**
     * <p>method that creates a commercial zone</p>
     *
     * @param commercialZone
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @Override
    @DynamicJPA
    @ApiOperation(value = "Create Commercial Zone",
            notes = "The method creates a commercial zone and if it is created correctly it returns the generated id",
            response = CommercialZone.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create Commercial Zone Correctly")
    })
    public Response createCommercialZone(@ApiParam(value = "Create Commercial Zone object", required = true) CommercialZone commercialZone) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iCommercialZonesServices.createCommercialZone(commercialZone, jpaDynamicBean.getJndiName()))
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
     * @param commercialZone
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @DynamicJPA
    @Override
    @ApiOperation(value = "Update Commercial Zone",
            notes = "The method update a commercial zone and if you update the commercial zone correctly it returns the data updated",
            response = CommercialZone.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update Commercial Zone Correctly")
    })
    public Response updateCommercialZone(@ApiParam(value = "Update Commercial Zone object", required = true) CommercialZone commercialZone) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iCommercialZonesServices.updateCommercialZone(commercialZone, jpaDynamicBean.getJndiName()))
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
     * <p>method that deactivate a commercial zone</p>
     *
     * @param zoneCode
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/deactivate/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @DynamicJPA
    @Override
    @ApiOperation(value = "Deactivate Commercial Zone",
            notes = "This method deactivate a Commercial Zone",
            response = CommercialZone.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Deactivate Commercial Zone Correctly")
    })
    public Response deactivateCommercialZone(
            @ApiParam(value = "Id of the Commercial Zone to deactivate", required = true)
            @PathParam("id")
            long zoneCode) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iCommercialZonesServices.deactivateCommercialZone(zoneCode, jpaDynamicBean.getJndiName()))
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
     * <p>method that obtain all commercial zones</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE, Roles.CAMPAIGN, Roles.CAMPAIGN_GET})
    @DynamicJPA
    @Override
    @ApiOperation(value = "List all Commercial Zone in the database",
            notes = "List all Commercial Zone in the database for Commercial Zone administration",
            response = CommercialZone.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all Commercial Zone Correctly")
    })
    public Response getAllCommercialZones() {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iCommercialZonesServices.getAllCommercialZones(jpaDynamicBean.getJndiName()))
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
     * <p>>method that obtain a commercial zones</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @DynamicJPA
    @Override
    @ApiOperation(value = "Show Commercial Zone in the database",
            notes = "Show Commercial Zone in the database for Commercial Zone administration",
            response = CommercialZone.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show Commercial Zone Correctly")
    })
    public Response getFindByIdCommercialZones(
           @ApiParam(value = "Id of the Commercial Zone to find by id", required = true)
           @PathParam("id") long zoneCode) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iCommercialZonesServices.getFindByIdCommercialZones(zoneCode, jpaDynamicBean.getJndiName()))
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
    @DynamicJPA
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @Override
    @ApiOperation(value = "Delete Commercial Zone in the database",
            notes = "Delete Commercial Zone in the database for Commercial Zone administration",
            response = CommercialZone.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show Commercial Zone Correctly")
    })
    public Response deleteCommercialZone(
            @ApiParam(value = "Id of the Commercial Zone to delete by id", required = true)
            @PathParam("id") long zoneCode) {
        Response response;
        try {
            this.iCommercialZonesServices.deleteCommercialZone(zoneCode, jpaDynamicBean.getJndiName());
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
