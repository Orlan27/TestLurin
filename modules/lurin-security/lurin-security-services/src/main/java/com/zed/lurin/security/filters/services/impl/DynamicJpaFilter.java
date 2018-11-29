package com.zed.lurin.security.filters.services.impl;


import com.zed.lurin.domain.jpa.Users;
import com.zed.lurin.domain.jpa.UsersControlAccess;
import com.zed.lurin.domain.jpa.view.DataSourceUserTokenView;
import com.zed.lurin.security.filters.services.DynamicJPA;
import com.zed.lurin.security.filters.services.bean.JpaDynamicBean;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.users.services.IUsersServices;
import org.apache.log4j.Logger;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

/**
 *
 * <p>the class <code>com.zed.lurin.rest.security.core.DynamicJpaFilter</code> allowing you to intercept
 * the request before it be handled by a resource method.
 * The <code>javax.ws.rs.container.ContainerRequestFilter</code> can be used to access the
 * HTTP request headers and then extract the jpa name</p>
 *
 * {@link DynamicJpaFilter}
 * {@link ContainerRequestFilter}
 *
 * @author Francisco Lujano
 */
@DynamicJPA
@Provider
@Priority(Priorities.AUTHORIZATION)
public class DynamicJpaFilter implements ContainerRequestFilter {

    /*
     * Logger util
     */
    private static Logger LOGGER = Logger.getLogger(DynamicJpaFilter.class.getName());

    @EJB
    IUsersServices usersServices;

    @Inject
    JpaDynamicBean jpaDynamicBean;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        String carrierHeader =
                requestContext.getHeaderString(Keys.CARRIER_HEADER.toString());

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        if (!isCarrierExist(carrierHeader)) {
            abortWithCarrierNoExist(requestContext);
            return;
        }

        String token = authorizationHeader
                .substring(Keys.AUTHENTICATION_SCHEME.toString().length()).trim();

        long carrierId = 0;
        try {
            carrierHeader = carrierHeader.trim();
            carrierId = Long.valueOf(carrierHeader);
        }catch (Exception e){
            abortWithCarrierNoExist(requestContext);
            return;
        }


        List<UsersControlAccess> usersControlAccesses = this.usersServices.getUserControlAccessByToken(token);

        if(usersControlAccesses.stream().findFirst().isPresent()) {
            final Users user = usersControlAccesses.stream().findFirst().get().getUser();
            List<DataSourceUserTokenView> dataSourceUserTokenViews =
                    this.usersServices.getDataSourceFromUserAndToken(user, token, carrierId);

            if(dataSourceUserTokenViews.stream().findFirst().isPresent()){
                final String jndiName = dataSourceUserTokenViews.stream().findFirst().get().getJndiName();
                LOGGER.debug(String.format("JNDI NAME DYNAMIC [%s]",jndiName));
                jpaDynamicBean.setJndiName(jndiName);
            }else{
                abortWithUnauthorized(requestContext);
            }
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
     *
     * Method for Check if the Carrier header is valid
     *
     * @param carrier
     * @return
     */
    private boolean isCarrierExist(String carrier) {

        return carrier != null && !carrier.isEmpty();
    }

    /**
     * Method that Abort the filter chain with a 401 status code
     * and the "WWW-Authenticate" header is sent along with the response
     *
     * @param requestContext
     */
    private void abortWithCarrierNoExist(ContainerRequestContext requestContext) {

        requestContext.abortWith(
                Response.status(Response.Status.PRECONDITION_FAILED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, Keys.AUTHENTICATION_SCHEME.toString())
                        .build());
    }


}
