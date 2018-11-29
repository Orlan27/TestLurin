package com.zed.channelmap.rest.impl;

import com.zed.channelmap.rest.IChannelMapServicesRest;
import com.zed.channelmap.services.IChannelMapServices;
import com.zed.lurin.domain.jpa.ChannelMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.zed.lurin.security.filters.services.Secured;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;


/**
 * <p>Stateless REST where the methods that manage the channel map are implemented</p>
 * @author Francisco Lujano
 * {@link IChannelMapServicesRest}
 */
@Path("/channelmap")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/channel", description = "Operation to manage channel map",
        authorizations = {
                @Authorization(value = "bearer_token")
        })
public class ChannelMapServicesRestImpl implements IChannelMapServicesRest {


    private static Logger LOGGER = Logger.getLogger(ChannelMapServicesRestImpl.class.getName());

    @EJB
    IChannelMapServices iChannelMapServices;

    /**
     * <p>method that obtain all channel map</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured
    @Override
    @ApiOperation(value = "List all Channel Map in the database",
            notes = "List all Channel Map in the database for Channel Map",
            response = ChannelMap.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all Channel Map Correctly")
    })
    public Response getAllChannelMap() {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iChannelMapServices.getAllChannelMap())
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
     * method that returns by primary key
     *
     * @param channelMapId
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured
    @Override
    @ApiOperation(value = "Object Channel Map in the database by id",
            notes = "Object Channel Map in the database for Channel Map by id",
            response = ChannelMap.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Channel Map Correctly")
    })
    public Response getChannelMapById(
            @ApiParam(value = "Id of channel map", required = true) @PathParam("id") long channelMapId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iChannelMapServices.getChannelMapById(channelMapId))
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
     * method that returns by external id
     *
     * @param externalId
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/external/{externalId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured
    @Override
    @ApiOperation(value = "List all Channel Map in the database by external id ",
            notes = "List all Channel Map in the database for Channel Map by external id",
            response = ChannelMap.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all Channel Map Correctly")
    })
    public Response getChannelMapByExternalId(@ApiParam(value = "External Id of channel map", required = true) @PathParam("externalId")  String externalId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iChannelMapServices.getChannelMapByExternalId(externalId))
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
     * method that returns by external id and primary key
     *
     * @param externalId
     * @param channelMapId
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/{id}/external/{externalId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured
    @Override
    @ApiOperation(value = "List Channel Map in the database by external id and primary key",
            notes = "List Channel Map in the database for Channel Map by external id and primary key",
            response = ChannelMap.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all Channel Map Correctly")
    })
    public Response getChannelMapByExternalIdAndId(
            @ApiParam(value = "External Id of channel map", required = true)
            @PathParam("externalId")
            String externalId,
            @ApiParam(value = "Id of channel map", required = true)
            @PathParam("id")
            long channelMapId) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iChannelMapServices.getChannelMapByExternalIdAndId(externalId, channelMapId))
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
