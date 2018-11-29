package com.zed.channel.packages.rest.impl;

import com.zed.channel.packages.rest.IChannelPackageServicesRest;
import com.zed.channel.packages.services.IChannelPackageServices;
import com.zed.lurin.domain.jpa.ChannelPackages;
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
 * <p>Stateless REST where the methods that manage the channel packages are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.channel packages.rest.IChannelPackageServicesRest}
 */
@Path("/channel/packages")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/channel/packages", description = "Operation to manage channel packages",
        authorizations = {
        @Authorization(value = "bearer_token")
})
public class ChannelPackageServicesRestImpl implements IChannelPackageServicesRest {

    @EJB
    IChannelPackageServices iChannelPackageServices;

    @Inject
    JpaDynamicBean jpaDynamicBean;

    /**
     * <p>method that creates a channel packages</p>
     *
     * @param channelPackages
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_CREATE})
    @DynamicJPA
    @ApiOperation(value = "Create Channel Packages",
            notes = "This method creates a channel packages for a campaign and if you create the channel packages correctly it returns the generated id",
            response = ChannelPackages.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create Channel Packages Correctly")
    })
    public Response createChannelPackages(@ApiParam(value = "Create Channel Packages object", required = true) ChannelPackages channelPackages) {
        Response response;

        try {
            response = Response.ok()
                    .entity(this.iChannelPackageServices.createChannelPackages(channelPackages, jpaDynamicBean.getJndiName()))
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
     * <p>method that updates a channel packages</p>
     *
     * @param channelPackages
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_UPDATE})
    @DynamicJPA
    @ApiOperation(value = "Update Channel Packages",
            notes = "This method updates a channel packages for a campaign and if you update the channel packages correctly it returns the data updated",
            response = ChannelPackages.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update Channel Packages Correctly")
    })
    public Response updateChannelPackages(@ApiParam(value = "Update Channel Packages object", required = true) ChannelPackages channelPackages) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iChannelPackageServices.updateChannelPackages(channelPackages, jpaDynamicBean.getJndiName()))
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
     * <p>method that deactivate a channel packages</p>
     *
     * @param channelPackagesId
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/deactivate/{carrier}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_DEACTIVATE})
    @DynamicJPA
    @ApiOperation(value = "Deactivate Channel Packages",
            notes = "This method deactivate a channel packages",
            response = ChannelPackages.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Deactivate Channel Packages Correctly")
    })
    public Response deactivateChannelPackages(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId
            , @ApiParam(value = "Id of the channel packages to deactivate", required = true)
            @PathParam("id")
                    long channelPackagesId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iChannelPackageServices.deactivateChannelPackages(channelPackagesId, jpaDynamicBean.getJndiName()))
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
     * <p>method that obtain all channel packages</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/get/{carrier}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_GET,Roles.FREEVIEW, Roles.FREEVIEW_GET})
    @DynamicJPA
    @ApiOperation(value = "List all channel packages in the database",
            notes = "List all channel packages in the database for channel packages administration",
            response = ChannelPackages.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all Channel Packages Correctly")
    })
    public Response getAllChannelPackages(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iChannelPackageServices.getAllChannelPackages(jpaDynamicBean.getJndiName()))
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
     * <p>>method that obtain a channel packages</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/get/{carrier}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_GET, Roles.FREEVIEW, Roles.FREEVIEW_GET})
    @DynamicJPA
    @ApiOperation(value = "Show channel packages in the database",
            notes = "Show channel packages in the database for channel packages administration",
            response = ChannelPackages.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show Channel Packages Correctly")
    })
    public Response getFindByIdChannelPackages(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId
            , @ApiParam(value = "Id of the channel packages to find by id", required = true)
            @PathParam("id")
                    long channelPackagesId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iChannelPackageServices.getFindByIdChannelPackages(channelPackagesId, jpaDynamicBean.getJndiName()))
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
     * <p>method that delete a channel packages</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @Override
    @DELETE
    @Path("/delete/{carrier}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.FREEVIEW, Roles.FREEVIEW_DELETE})
    @DynamicJPA
    @ApiOperation(value = "Delete channel packages in the database",
            notes = "Delete channel packages in the database for channel packages administration",
            response = ChannelPackages.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show Channel Packages Correctly")
    })
    public Response deleteChannelPackages(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId
            , @ApiParam(value = "Id of the channel packages to delete by id", required = true)
            @PathParam("id")
                    long channelPackagesId) {
        Response response;
        try {
            this.iChannelPackageServices.deleteChannelPackages(channelPackagesId, jpaDynamicBean.getJndiName());
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
