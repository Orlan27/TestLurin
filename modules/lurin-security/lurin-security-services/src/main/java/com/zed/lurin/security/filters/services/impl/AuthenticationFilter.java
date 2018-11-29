package com.zed.lurin.security.filters.services.impl;


import com.zed.lurin.domain.jpa.Users;
import com.zed.lurin.domain.jpa.UsersControlAccess;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zed.lurin.domain.jpa.view.RolesByUserNameView;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.keys.Roles;
import com.zed.lurin.security.users.services.IUsersServices;
import org.apache.log4j.Logger;

/**
 *
 * <p>the class <code>com.zed.lurin.rest.security.core.AuthenticationFilter</code> allowing you to intercept
 * the request before it be handled by a resource method.
 * The <code>javax.ws.rs.container.ContainerRequestFilter</code> can be used to access the
 * HTTP request headers and then extract the token</p>
 *
 * {@link AuthenticationFilter}
 * {@link javax.ws.rs.container.ContainerRequestFilter}
 *
 * @author Francisco Lujano
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    /*
     * Logger util
     */
    private static Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());

    private Users users;

    @EJB
    IUsersServices usersServices;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                .substring(Keys.AUTHENTICATION_SCHEME.toString().length()).trim();

        try {
            // Validate the token
            this.validateToken(token);

            //Update Event Time
            List<UsersControlAccess> usersControlAccesses = this.usersServices.updateEventTime(
                    this.usersServices.getUserControlAccessByToken(token));

            //Set User Principal
            setPrincipalForUserName(requestContext, usersControlAccesses, token);

            // Get the resource method which matches with the requested URL
            Method resourceMethod = this.resourceInfo.getResourceMethod();

            //Extract the roles declared
            List<Roles> allowedRoles = this.extractRoles(resourceMethod);

            if(allowedRoles.size()>0) {
                //return roles by username
                List<RolesByUserNameView> rolesByUserName = this.usersServices.getRoleFromUserName(this.getUsers());

                //check permission
                this.checkPermissionsByRole(allowedRoles, rolesByUserName);
            }


        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    /**
     * method that assigns the username to the request
     * @param requestContext
     * @param usersControlAccesses
     * @param token
     */
    private void setPrincipalForUserName(ContainerRequestContext requestContext,
                                         List<UsersControlAccess> usersControlAccesses, String token) {
        if(usersControlAccesses.stream().findFirst().isPresent()) {

            this.setUsers(usersControlAccesses.stream().findFirst().get().getUser());
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return  new Principal() {
                        @Override
                        public String getName() {
                            return getUsers().getLogin();
                        }
                    };
                }

                @Override
                public boolean isUserInRole(String role) {
                    return true;
                }

                @Override
                public boolean isSecure() {
                    return true;
                }

                @Override
                public String getAuthenticationScheme() {
                    return token;
                }
            });
        }
    }


    /**
     *
     * Extract the roles from the annotated element
     *
     * @param annotatedElement
     * @return
     */
    private List<Roles> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<Roles>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<Roles>();
            } else {
                Roles[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    /**
     * checks if the user contains one or more allowed roles, but throws an exception
     * @param allowedRoles
     * @throws Exception
     */
    private void checkPermissionsByRole(List<Roles> allowedRoles,
                                        List<RolesByUserNameView> rolesByUserName) throws Exception {

        List<String> rolesByUser = rolesByUserName.stream()
                .map(RolesByUserNameView::getRole)
                .flatMap(Stream::of)
                .map(role -> role.toLowerCase())
                .collect(Collectors.toList());

        List<String> allowed = allowedRoles.stream()
                .map(Roles::toString)
                .flatMap(Stream::of)
                .map(role -> role.toLowerCase())
                .collect(Collectors.toList());

       final boolean isRoleExist =  allowed.stream().anyMatch(allow -> rolesByUser.contains(allow));

        LOGGER.debug(String.format("Role Method Definition %s and Roles System %s",allowed, rolesByUser));
        if(!isRoleExist){
            LOGGER.debug("Denied Access for not fulfilling required roles");
            throw new Exception();
        }

    }

    /**
     *
     * Method for Check if the Authorization header is valid
     * It must not be null and must be prefixed with "Bearer" plus a whitespace
     * Authentication scheme comparison must be case-insensitive
     *
     * @param authorizationHeader
     * @return
     */
    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(Keys.AUTHENTICATION_SCHEME.toString().toLowerCase() + " ");
    }

    /**
     * Method that Abort the filter chain with a 401 status code
     * and the "WWW-Authenticate" header is sent along with the response
     *
     * @param requestContext
     */
    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, Keys.AUTHENTICATION_SCHEME.toString())
                        .build());
    }

    /**
     * Check if the token exists if it does not throw an exception
     * @param token
     * @throws Exception
     */
    private void validateToken(String token) throws Exception {
        final boolean isExistToken = this.usersServices.isExistToken(token);
        LOGGER.debug(String.format("Token [%s] exist [%s] ",token, isExistToken));
        if(!isExistToken){
            throw new Exception();
        }
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
