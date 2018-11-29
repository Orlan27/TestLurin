package com.zed.type.load.rest.impl;

import com.zed.type.load.rest.ITypeLoadServicesRest;
import com.zed.type.load.services.ITypeLoadServices;
import com.zed.lurin.domain.jpa.TypeLoad;
import com.zed.lurin.security.filters.services.DynamicJPA;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.filters.services.bean.JpaDynamicBean;
import com.zed.lurin.security.keys.Roles;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * <p>Stateless REST where the methods that manage the type load are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.type load.rest.ITypeLoadServicesRest}
 */
@Path("/type/load")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/type/load", description = "Operation to manage type load",
        authorizations = {
        @Authorization(value = "bearer_token")
})
public class TypeLoadServicesRestImpl implements ITypeLoadServicesRest {

    @EJB
    ITypeLoadServices iTypeLoadServices;

    @Inject
    JpaDynamicBean jpaDynamicBean;

    /**
     * <p>method that creates a type load</p>
     *
     * @param typeLoad
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured
    @DynamicJPA
    @ApiOperation(value = "Create type load",
            notes = "This method creates a type load for a campaign and if you create the type load correctly it returns the generated id",
            response = TypeLoad.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create type load Correctly")
    })
    public Response createTypeLoad(@ApiParam(value = "Create type load object", required = true) TypeLoad typeLoad) {
        Response response;

        try {
            response = Response.ok()
                    .entity(this.iTypeLoadServices.createTypeLoad(typeLoad, jpaDynamicBean.getJndiName()))
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
     * <p>method that updates a type load</p>
     *
     * @param typeLoad
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_UPDATE})
    @DynamicJPA
    @ApiOperation(value = "Update type load",
            notes = "This method updates a type load for a campaign and if you update the type load correctly it returns the data updated",
            response = TypeLoad.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update type load Correctly")
    })
    public Response updateTypeLoad(@ApiParam(value = "Update type load object", required = true) TypeLoad typeLoad) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iTypeLoadServices.updateTypeLoad(typeLoad, jpaDynamicBean.getJndiName()))
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
     * <p>method that obtain all type load</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/get/{carrier}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_GET,Roles.FREEVIEW, Roles.FREEVIEW_GET, Roles.CAMPAIGN, Roles.CAMPAIGN_GET})
    @DynamicJPA
    @ApiOperation(value = "List all type load in the database",
            notes = "List all type load in the database for type load administration",
            response = TypeLoad.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all type load Correctly")
    })
    public Response getAllTypeLoad(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iTypeLoadServices.getAllTypeLoad(jpaDynamicBean.getJndiName()))
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
     * <p>>method that obtain a type load</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/get/{carrier}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_GET, Roles.FREEVIEW, Roles.FREEVIEW_GET})
    @DynamicJPA
    @ApiOperation(value = "Show type load in the database",
            notes = "Show type load in the database for type load administration",
            response = TypeLoad.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show type load Correctly")
    })
    public Response getFindByIdTypeLoad(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId
            , @ApiParam(value = "Id of the type load to find by id", required = true)
            @PathParam("id")
                    long typeLoadId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iTypeLoadServices.getFindByIdTypeLoad(typeLoadId, jpaDynamicBean.getJndiName()))
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
     * <p>method that delete a type load</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @Override
    @DELETE
    @Path("/delete/{carrier}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_DELETE})
    @DynamicJPA
    @ApiOperation(value = "Delete type load in the database",
            notes = "Delete type load in the database for type load administration",
            response = TypeLoad.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show type load Correctly")
    })
    public Response deleteTypeLoad(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId
            , @ApiParam(value = "Id of the type load to delete by id", required = true)
            @PathParam("id")
                    long typeLoadId) {
        Response response;
        try {
            this.iTypeLoadServices.deleteTypeLoad(typeLoadId, jpaDynamicBean.getJndiName());
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
