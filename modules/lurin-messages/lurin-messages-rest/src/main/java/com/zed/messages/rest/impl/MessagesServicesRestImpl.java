package com.zed.messages.rest.impl;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.zed.lurin.domain.jpa.Messages;
import com.zed.lurin.security.filters.services.DynamicJPA;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.filters.services.bean.JpaDynamicBean;
import com.zed.messages.rest.IMessagesServicesRest;
import com.zed.messages.services.IMessagesServices;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;


/**
 * <p>Stateless REST where the methods that manage the channel map are implemented</p>
 * @author Francisco Lujano
 * {@link IMessagesServicesRest}
 */
@Path("/messages")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/messages", description = "Operation to manage channel map",
        authorizations = {
                @Authorization(value = "bearer_token")
        })
public class MessagesServicesRestImpl implements IMessagesServicesRest {


    private static Logger LOGGER = Logger.getLogger(MessagesServicesRestImpl.class.getName());

    @EJB
    IMessagesServices iMessagesServices;


    @Inject
    JpaDynamicBean jpaDynamicBean;

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
    @DynamicJPA
    @Override
    @ApiOperation(value = "List all Messages in the database",
            notes = "List all Messages in the database for Messages",
            response = Messages.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all Messages Correctly")
    })
    public Response getAllMessages() {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iMessagesServices.getAllMessages(jpaDynamicBean.getJndiName()))
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
