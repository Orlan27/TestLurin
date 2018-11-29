package com.zed.type.load.rest;

import com.zed.lurin.domain.jpa.TypeLoad;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public interface ITypeLoadServicesRest {
    /**
     * <p>method that creates a type load</p>
     *
     * @param typeLoad
     * @return {@javax.ws.rs.core.Response}
     */
    Response createTypeLoad(@ApiParam(value = "Create type load object", required = true) TypeLoad typeLoad);

    /**
     * <p>method that updates a type load</p>
     *
     * @param typeLoad
     * @return {@javax.ws.rs.core.Response}
     */
    Response updateTypeLoad(@ApiParam(value = "Update type load object", required = true) TypeLoad typeLoad);

    /**
     * <p>method that obtain all type load</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    Response getAllTypeLoad(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId);

    /**
     * <p>>method that obtain a type load</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getFindByIdTypeLoad(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId
            , @ApiParam(value = "Id of the type load to find by id", required = true)
            @PathParam("id")
                    long typeLoadId);

    /**
     * <p>method that delete a type load</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response deleteTypeLoad(
            @ApiParam(value = "Id of carriers ", required = true)
            @PathParam("carrier")
                    long carrierId
            , @ApiParam(value = "Id of the type load to delete by id", required = true)
            @PathParam("id")
                    long typeLoadId);
}
