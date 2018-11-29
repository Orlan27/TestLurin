package com.zed.channel.packages.rest;

import com.zed.lurin.domain.jpa.ChannelPackages;
import com.zed.lurin.security.filters.services.DynamicJPA;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface IChannelPackageServicesRest {
    /**
     * <p>method that creates a channel packages</p>
     *
     * @param channelPackages
     * @return {@javax.ws.rs.core.Response}
     */
    Response createChannelPackages(@ApiParam(value = "Create Channel Packages object", required = true) ChannelPackages channelPackages);

    /**
     * <p>method that updates a channel packages</p>
     *
     * @param channelPackages
     * @return {@javax.ws.rs.core.Response}
     */
    Response updateChannelPackages(@ApiParam(value = "Update Channel Packages object", required = true) ChannelPackages channelPackages);

    /**
     * <p>method that deactivate a channel packages</p>
     *
     * @param channelPackagesId
     * @return {@javax.ws.rs.core.Response}
     */
    Response deactivateChannelPackages(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId
            , @ApiParam(value = "Id of the channel packages to deactivate", required = true)
            @PathParam("id")
                    long channelPackagesId);

    /**
     * <p>method that obtain all channel packages</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    Response getAllChannelPackages(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId);

    /**
     * <p>>method that obtain a channel packages</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getFindByIdChannelPackages(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId
            , @ApiParam(value = "Id of the channel packages to find by id", required = true)
            @PathParam("id")
                    long channelPackagesId);

    /**
     * <p>method that delete a channel packages</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response deleteChannelPackages(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId
            , @ApiParam(value = "Id of the channel packages to delete by id", required = true)
            @PathParam("id")
                    long channelPackagesId);
}
