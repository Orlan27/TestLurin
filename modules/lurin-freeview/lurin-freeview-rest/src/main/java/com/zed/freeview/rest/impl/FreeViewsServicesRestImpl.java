package com.zed.freeview.rest.impl;

import com.zed.freeview.rest.IFreeViewsServicesRest;
import com.zed.freeview.services.IFreeViewsServices;
import com.zed.lurin.domain.jpa.FreeView;
import com.zed.lurin.security.filters.services.DynamicJPA;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.filters.services.bean.JpaDynamicBean;
import com.zed.lurin.security.keys.Roles;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * <p>Stateless REST where the methods that manage the free views are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.freeview.rest.IFreeViewsServicesRest}
 */
@Path("/freeview")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/freeview", description = "Operation to manage freeview",
        authorizations = {
        @Authorization(value = "bearer_token")
})
public class FreeViewsServicesRestImpl implements IFreeViewsServicesRest {

    @EJB
    IFreeViewsServices iFreeViewsServices;

    @Inject
    JpaDynamicBean jpaDynamicBean;

    /**
     * <p>method that creates a free view</p>
     *
     * @param freeView
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @DynamicJPA
    @Override
    @ApiOperation(value = "Create Freeview",
            notes = "This method creates a freeview for a campaign and if you create the freeview correctly it returns the generated id",
            response = FreeView.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create Freeview Correctly")
    })
    public Response createFreeView(@ApiParam(value = "Create Freeview object", required = true) FreeView freeView) {
        Response response;

        try {
            response = Response.ok()
                    .entity(this.iFreeViewsServices.createFreeView(freeView, jpaDynamicBean.getJndiName()))
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
     * <p>method that updates a free view</p>
     *
     * @param freeView
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_UPDATE})
    @DynamicJPA
    @Override
    @ApiOperation(value = "Update Freeview",
            notes = "This method updates a freeview for a campaign and if you update the freeview correctly it returns the data updated",
            response = FreeView.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update Freeview Correctly")
    })
    public Response updateFreeView(@ApiParam(value = "Update Freeview object", required = true) FreeView freeView) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iFreeViewsServices.updateFreeView(freeView, jpaDynamicBean.getJndiName()))
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
     * <p>method that deactivate a free view</p>
     *
     * @param freeViewId
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/deactivate/{carrier}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_DEACTIVATE})
    @DynamicJPA
    @Override
    @ApiOperation(value = "Deactivate Freeview",
            notes = "This method deactivate a freeview",
            response = FreeView.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Deactivate Freeview Correctly")
    })
    public Response deactivateFreeView(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
            long carrierId
            ,@ApiParam(value = "Id of the freeview to deactivate", required = true)
            @PathParam("id")
            long freeViewId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iFreeViewsServices.deactivateFreeView(freeViewId, jpaDynamicBean.getJndiName()))
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
     * <p>method that obtain all free views</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/{carrier}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_GET, Roles.CAMPAIGN, Roles.CAMPAIGN_GET})
    @DynamicJPA
    @Override
    @ApiOperation(value = "List all freeview in the database",
            notes = "List all freeview in the database for freeview administration",
            response = FreeView.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all Freeview Correctly")
    })
    public Response getAllFreeViews(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
            long carrierId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iFreeViewsServices.getAllFreeViews(jpaDynamicBean.getJndiName()))
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
     * <p>>method that obtain a free views</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/{carrier}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_GET})
    @DynamicJPA
    @Override
    @ApiOperation(value = "Show freeview in the database",
            notes = "Show freeview in the database for freeview administration",
            response = FreeView.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show Freeview Correctly")
    })
    public Response getFindByIdFreeViews(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
            long carrierId
            ,@ApiParam(value = "Id of the freeview to find by id", required = true)
            @PathParam("id")
            long freeViewId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iFreeViewsServices.getFindByIdFreeViews(freeViewId, jpaDynamicBean.getJndiName()))
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
     * <p>method that delete a free view</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @DELETE
    @Path("/delete/{carrier}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_DELETE})
    @DynamicJPA
    @Override
    @ApiOperation(value = "Delete freeview in the database",
            notes = "Delete freeview in the database for freeview administration",
            response = FreeView.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show Freeview Correctly")
    })
    public Response deleteFreeView(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
            long carrierId
            ,@ApiParam(value = "Id of the freeview to delete by id", required = true)
            @PathParam("id")
            long freeViewId) {
        Response response;
        try {
            this.iFreeViewsServices.deleteFreeView(freeViewId, jpaDynamicBean.getJndiName());
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
