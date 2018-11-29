package com.zed.security.rest.impl;

import com.zed.lurin.domain.dto.UserLdapDTO;
import com.zed.lurin.domain.jpa.*;
import com.zed.lurin.security.admin.profiles.services.IAdminProfiles;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.zed.lurin.domain.jpa.view.MenuByUserNameView;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.security.dto.ResetUserPasswordDTO;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.ldap.services.ILdapServices;
import com.zed.lurin.security.token.services.ITokenSecurityServices;
import com.zed.lurin.security.users.services.IUsersServices;
import com.zed.security.rest.ISecurityServicesRest;
import com.zed.lurin.security.keys.Roles;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * <p>Stateless REST where the methods that manage the security are implemented</p>
 * @author Francisco Lujano
 * {@link ISecurityServicesRest}
 */
@Path("/security")
@Stateless
@Api(
    basePath = "/api/rest/",
    value = "/security", description = "Operation to manage Security")
public class SecurityServicesRestImpl implements ISecurityServicesRest {


    private static Logger LOGGER = Logger.getLogger(SecurityServicesRestImpl.class.getName());

    /**
     * Context Security
     */
    @Context
    SecurityContext securityContext;

    /**
     * Security Services
     */
    @EJB
    IUsersServices usersServices;
      /**
     * IAdminProfiles Services
     */
    @EJB
    IAdminProfiles profiles;

    /**
     * Token Services
     */
    @EJB
    ITokenSecurityServices tokenSecurityServices;

    /**
     * Core Parameter Services
     */
    @EJB
    ICoreParameterServices parameterServices;

    /**
     * Ldap Services
     */
    @EJB
    ILdapServices iLdapServices;

    /**
     * <p>method that creates a commercial zone</p>
     *
     * @param user
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    @ApiOperation(value = "Login User in the system",
            notes = "This method logs the user if it complies with security rules",
            response = UsersControlAccess.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful login")
    })
    public Response loginUser(@ApiParam(value = "Users object", required = true) Users user) {
        Response response;
        try {

            List<Users> users = this.usersServices.validateUserNameAndPassword(user);
            if(users.stream().findFirst().isPresent()){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
                String date = sdf.format(new Date());

                final int timeExpired = this.parameterServices.getCoreParameterInteger(
                        Keys.KEY_SESSION_TIMEOUT_INACTIVE.toString());

                final String token = this.tokenSecurityServices.createToken(user.getLogin(), date.toString());

                UsersControlAccess usersControlAccess = new UsersControlAccess();
                usersControlAccess.setUser(users.stream().findFirst().get());
                usersControlAccess.setToken(token);
                usersControlAccess.setTimeExpired(timeExpired);
                usersControlAccess.setI18n(user.getLang());
                this.usersServices.createUserControlAccess(usersControlAccess);

                response = Response.ok().entity(usersControlAccess).build();

            }else{
                response = Response.ok().status(Response.Status.UNAUTHORIZED).build();
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
     * <p>method that updates a commercial zone</p>
     *
     * @param user
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/logout")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    @ApiOperation(value = "Logout User in the system",
            notes = "This method disregards the system user",
            response = Users.class,
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "successful logout")
    })
    public Response logoutUser(@ApiParam(value = "Users object", required = true) Users user) {

        Response response;
        try {

            List<UsersControlAccess> usersControlAccessList =
                    this.usersServices.validateUserNameAndToken(user, this.securityContext.getAuthenticationScheme());
            if(usersControlAccessList.stream().findFirst().isPresent()){
                this.usersServices.deactivateTokens(usersControlAccessList);
                response = Response.ok().status(Response.Status.OK).build();
            }else{
                response = Response.ok().build();
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
     * <p>method that get menu by username</p>
     * @param user
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/menu")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    @ApiOperation(value = "method that returns the menu per user",
            notes = "this method returns the menu per user logged in the system",
            response = Users.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list with the user's menu")
    })
    public Response getMenuByUserName(@ApiParam(value = "Users object", required = true) Users user) {

        Response response;
        try {

            List<MenuByUserNameView> menuByUserNameViews = this.usersServices.getMenuFromUserName(user);
            if(menuByUserNameViews.stream().findFirst().isPresent()){

                response = Response.ok().entity(menuByUserNameViews).build();
            }else{
                response = Response.ok().status(Response.Status.FORBIDDEN).build();
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


    @POST
    @Path("/resetPassword")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response resetPassword(@ApiParam(value = "Users object", required = true) Users user) {
      Response response;
        try {
            this.usersServices.resetUsersPassword(user);
           response = Response.ok().status(Response.Status.ACCEPTED).build();

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

    @POST
    @Path("/changePassword")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response setChangePassword(@ApiParam(value = "Users object", required = true) Users user) {
      Response response;
        try {
            this.usersServices.setUsersPassword(user);
           response = Response.ok().status(Response.Status.ACCEPTED).build();

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

    @POST
    @Path("/sendResetPassword")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
      @ApiOperation(value = "Method that send mail for reset the user's password",
            notes = "this method send mail for reset the user's password",
            response = Response.class,
            responseContainer = "Status",
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list with the user's menu")
    })
    @Override
    public Response sendResetPassword(@ApiParam(value = "ResetUserPasswordDTO object", required = true) ResetUserPasswordDTO reset) {
        Response response;
        try {
      this.usersServices.sendresetUsersPassword(reset.getUser(), reset.getUrl());
           response = Response.ok().status(Response.Status.ACCEPTED).build();

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



    @POST
    @Path("/isExpireUrl")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
      @ApiOperation(value = "Method that consults the expiration of url",
            notes = "this method that consults the expiration of url",
            response = ResetUserPass.class,
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "consults the expiration ")
    })
    @Override
    public Response isExpireUrl(@ApiParam(value = "Users object", required = true) Users user) {
        Response response;
        try {
            ResetUserPass reset = usersServices.getResetUserPass(user.getCode());
             response = Response.ok().entity(reset).build();
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


    @GET
    @Path("/getPolitics")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
      @ApiOperation(value = "Method that consults the parmeter politics",
            notes = "this method that consults the parmeter politics",
            response = ResetUserPass.class,
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "consults the parmeter politics ")
    })
    @Override
    public Response getParameterPolitics() {
           Response response;
        try {

            List<CoreParameter> listcoreParameter = this.parameterServices.getListCoreParameterbyCategory(Keys.POLITIC_PARAMETERS_CATEGORY.toString());
            if(listcoreParameter.stream().findFirst().isPresent()){

                response = Response.ok().entity(listcoreParameter).build();
            }else{
                response = Response.ok().status(Response.Status.FORBIDDEN).build();
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

    @POST
    @Path("/updatePolitics")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
      @ApiOperation(value = "Method that update the parmeter politics",
            notes = "this method that update the parmeter politics",
            response = ResetUserPass.class,
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "update the parmeter politics ")
    })
    @Override
    public Response updateParameterPolitics(CoreParameter coreParameter) {
         Response response;
        try {

           this.parameterServices.updateCoreParameter(coreParameter);
           response = Response.ok().status(Response.Status.ACCEPTED).build();

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
     * <p>method that get all profiles</p>
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getRoles")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    @ApiOperation(value = "Method that returns the all roles",
            notes = "This method returns all roles",
            response = Roles.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list all roles")
    })
    public Response getAllRoles() {

        Response response;
        try {
            response = Response.ok().entity(this.profiles.getAllRoles()).build();
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
     * method is responsible for returning all profiles
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getVisibleRoles/{lang}")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.PROFILE_MANAGEMENT, Roles.PROFILE_MANAGEMENT_GET})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Method that returns the all visible roles",
            notes = "This method returns all visible of roles",
            response = Roles.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list all visible roles")
    })
    @Override
    public Response getAllVisibleRoles(
            @ApiParam(value = "Languages to print front end", required = true)
            @PathParam("lang") String lang) {
        Response response;
        try {
            response = Response.ok().entity(this.profiles.getAllVisibleRoles(lang)).build();
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
     * Method that creates the profile in the system
     *
     * @param profile
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/createProfile")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Method creates a profile in the system",
            notes = "Method creates a profile in the system",
            response = Profile.class,
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "New profile information created")
    })
    @Override
    public Response createProfile(Profile profile) {
        Response response;
        try {

            response = Response.ok().entity(this.profiles.createProfile(profile)).build();

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
     * Method that updates the profile in the system
     *
     * @param profile
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/updateProfile")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Method updates a profile in the system",
            notes = "Method updates a profile in the system",
            response = Profile.class,
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Profile with updated information")
    })
    @Override
    public Response updateProfile(Profile profile) {
        Response response;
        try {

            response = Response.ok().entity(this.profiles.updateProfile(profile)).build();

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
     * Method that erases a profile
     *
     * @param profileId
     */
    @DELETE
    @Path("/deleteProfile/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL})
    @ApiOperation(value = "delete a system profile",
            notes = "delete a system profile",
            response = Profile.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Deleting the profile correctly")
    })
    @Override
    public Response deleteProfile(
            @ApiParam(value = "Id of the profile to delete", required = true)
            @PathParam("id") long profileId) {

        Response response;
        try {
            this.profiles.deleteProfile(profileId);
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
     * Method that lists all profiles
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getProfiles")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.PROFILE_MANAGEMENT, Roles.PROFILE_MANAGEMENT_GET, Roles.USER_ADMINISTRATION, Roles.USER_ADMINISTRATION_GET})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Method that returns the all profiles",
            notes = "This method returns all of profiles",
            response = Profile.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list all profiles")
    })
    @Override
    public Response getProfiles() {
        Response response;
        try {
            response = Response.ok().entity(this.profiles.getProfiles()).build();
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
     * Method that lists all category profiles
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getCategoryProfiles")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.PROFILE_MANAGEMENT, Roles.PROFILE_MANAGEMENT_GET, Roles.USER_ADMINISTRATION, Roles.USER_ADMINISTRATION_GET})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Method that returns the all category profiles",
            notes = "This method returns all category of profiles",
            response = Profile.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list all profiles")
    })
    @Override
    public Response getCategoryProfiles() {
        Response response;
        try {
            response = Response.ok().entity(this.profiles.getCategoryProfiles()).build();
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
     * Method that all user ldap Server
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getLdapUsers")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Method that returns the all user ldap Server ",
            notes = "This method returns all user ldap Server",
            response = UserLdapDTO.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "list all user ldap Server")
    })
    @Override
    public Response getLdapUsers() {
        Response response;
        try {
            response = Response.ok().entity(this.iLdapServices.findUserLdap()).build();
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
