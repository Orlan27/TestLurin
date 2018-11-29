package com.zed.admin.rest.impl;

import com.zed.admin.rest.IAdminServicesRest;
import com.zed.admin.services.IAdminServices;
import com.zed.carriers.services.ICarriersServices;
import com.zed.lurin.domain.dto.MessageCustomDTO;
import com.zed.lurin.domain.dto.UserPasswordChangeDTO;
import com.zed.lurin.domain.jpa.Users;
import com.zed.lurin.domain.jpa.view.UserExpiredPasswordView;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.keys.Roles;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * <p>Stateless REST where the methods administrator are implemented</p>
 * @author Francisco Lujano
 * {@link IAdminServicesRest}
 */
@Path("/administrator")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/administrator", description = "Operation to manage administrator",
        authorizations = {
        @Authorization(value = "bearer_token")
})
public class AdminServicesRestImpl implements IAdminServicesRest {

    @EJB
    IAdminServices adminServices;

    @EJB
    ICoreParameterServices parameterServices;

    /**
     * Carriers Services
     */
    @EJB
    ICarriersServices carriersServices;

    /**
     * <p>method that creates a users</p>
     *
     * @param users
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.USER_ADMINISTRATION, Roles.USER_ADMINISTRATION_CREATE})
    @ApiOperation(value = "Create User",
            notes = "This method creates user if you create the user correctly it " +
                    "returns the users create",
            response = Users.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create Administrator Correctly")
    })
    public Response createUser(@ApiParam(value = "Create Administrator object", required = true) Users users,
    		@HeaderParam(value="isAdminLocal") boolean isAdminLocal) {
        Response response;
        try {

        	if (isAdminLocal) {
                response = Response.ok()
                        .entity( this.adminServices.createUsersAdminLocal(users))
                        .build();        		
        	}else {
                response = Response.ok()
                        .entity( this.adminServices.createUsers(users))
                        .build();
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
     * <p>method that update a users</p>
     *
     * @param users
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.USER_ADMINISTRATION, Roles.USER_ADMINISTRATION_UPDATE})
    @ApiOperation(value = "Update User",
            notes = "This method update user if you update the user correctly it " +
                    "returns the users updating",
            response = Users.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update Administrator Correctly")
    })
    public Response updateUser(@ApiParam(value = "Update Administrator object", required = true) Users users,
    		@HeaderParam(value="isAdminLocal") boolean isAdminLocal) {
        Response response;

        try {

        	if (isAdminLocal) {
                response = Response.ok()
                        .entity(this.adminServices.updateUsersAdminLocal(users))
                        .build();        		
        	} else {
                response = Response.ok()
                        .entity(this.adminServices.updateUsers(users))
                        .build();
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
     * <p>method validate if username exist</p>
     *
     * @param userName
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/username/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL})
    @ApiOperation(value = "Validating username if exist",
            notes = "This method Validating username if exist if exist return true else false"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "true or false")
    })
    public Response isValidUserName(
            @ApiParam(value = "Validating username if exist Administrator object", required = true)
            @PathParam("username")
            String userName) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.adminServices.isExistUserName(userName))
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
     * <p>method remove user</p>
     *
     * @param codeUser
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @DELETE
    @Path("/delete/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.USER_ADMINISTRATION, Roles.USER_ADMINISTRATION_DELETE})
    @ApiOperation(value = "Delete User From System",
            notes = "Method remove user by system"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Delete user Correctly")
    })
    public Response deleteUser(
            @ApiParam(value = "Code user to remove", required = true)
            @PathParam("code")
            long codeUser) {
        Response response;
        try {
            this.adminServices.deleteUsers(codeUser);
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

    /**
     * method return list datasource active
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/datasources")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL})
    @ApiOperation(value = "List Datasources Administrator",
            notes = "this method returns the list of active datasources in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list of active datasources")
    })
    public Response getDataSources() {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.adminServices.getDataSources())
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
     * method return list carriers active
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/carriers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL})
    @ApiOperation(value = "List Carriers Administrator",
            notes = "this method returns the list of active Carriers in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list of active Carriers")
    })
    public Response getCarriers() {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.adminServices.getCarriers())
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
     * <p>method return true if password is expired
     *
     * @param userName
     * @return
     */
    @Override
    @GET
    @Path("/isExpiredPassword/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured
    @ApiOperation(value = "Validating if password expired",
            notes = "this method returns if the passwrod expired"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "true if password expired")
    })
    public Response isExpiredPasswordForUserName(
            @ApiParam(value = "Validating password if expired", required = true)
            @PathParam("username")
            String userName) {
        Response response;
        try {

            int expiredPassword = this.parameterServices.getCoreParameterInteger(
                    Keys.USER_EXPIRED_PASSWORD.toString());

            List<UserExpiredPasswordView> userExpiredPasswordViews =
                    this.adminServices.getPasswordExpiredForUserName(userName);

            if(userExpiredPasswordViews.stream().findFirst().isPresent()){
                UserExpiredPasswordView userExpiredPasswordView =
                        userExpiredPasswordViews.stream().findFirst().get();
                response = Response.ok()
                        .entity(userExpiredPasswordView.getDays()>=expiredPassword)
                        .build();
            }else{
                response = Response.ok()
                        .entity(false)
                        .build();
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
     * method return list roles active
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/users/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.USER_ADMINISTRATION, Roles.USER_ADMINISTRATION_GET})
    @ApiOperation(value = "List Users Administrator",
            notes = "this method returns the list of active Users in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list of active Users")
    })
    public Response getUsers(@HeaderParam(value = "Authorization") String token,
    		@HeaderParam(value="isAdminLocal") boolean isAdminLocal) {
        Response response;
        try {

            response = Response.ok()
                    .entity(this.adminServices.getUsers(token, isAdminLocal))
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
     * method return list roles active
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/users/carrier/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.USER_ADMINISTRATION, Roles.USER_ADMINISTRATION_GET})
    @ApiOperation(value = "List Users Administrator",
            notes = "this method returns the list of active Users in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list of active Users")
    })
    public Response getUsersCarrier() {
        Response response;
        try {

            response = Response.ok()
                    .entity(this.carriersServices.getAllCarriersByUserName())
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
     * <p>method that update a password users</p>
     *
     * @param userPasswordChangeDTO
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @POST
    @Path("/change/password")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL})
    @ApiOperation(value = "Update Password User",
            notes = "This method update Password user",
            response = MessageCustomDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update Password User Correctly")
    })
    public Response changePasswordForUser(@ApiParam(value = "Update Password User Entity", required = true) UserPasswordChangeDTO userPasswordChangeDTO) {
        Response response;

        try {

            response = Response.ok()
                    .entity(this.adminServices.changePasswordForUser(userPasswordChangeDTO))
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
     * <p>method that get User by username</p>
     *
     * @param userName
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    @GET
    @Path("/user/get/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL})
    @ApiOperation(value = "Get User by username",
            notes = "This method return a Users by username"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Users object")
    })
    public Response getUserByUserName(
            @ApiParam(value = "Get User by username", required = true)
            @PathParam("username")
            String userName) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.adminServices.getUserByUserName(userName))
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
