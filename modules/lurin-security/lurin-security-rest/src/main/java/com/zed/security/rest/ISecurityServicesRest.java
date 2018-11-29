package com.zed.security.rest;
import com.zed.lurin.domain.jpa.*;

import java.util.List;

import com.zed.lurin.security.dto.ResetUserPasswordDTO;
import javax.ws.rs.core.Response;

/**
 * <p>Abstract Class which defines the methods manage the security are implemented</p>
 * @author Francisco Lujano
 */
public interface ISecurityServicesRest {

    /**
     * <p>method that login user</p>
     * @param user
     * @return {@javax.ws.rs.core.Response}
     */
    Response loginUser(Users user);

    /**
     * <p>method that logout user</p>
     * @param user
     * @return {@javax.ws.rs.core.Response}
     */
    Response logoutUser(Users user);


    /**
     * <p>method that get menu by username</p>
     * @param user
     * @return {@javax.ws.rs.core.Response}
     */
    Response getMenuByUserName(Users user);


       /**
     * <p>Method that resets the user's password</p>
     * @param user
     * @return {@javax.ws.rs.core.Response}
     */
    Response resetPassword(Users user);
        /**
     * <p>Method that change the user's password</p>
     * @param user
     * @return {@javax.ws.rs.core.Response}
     */
    Response setChangePassword(Users user);
    
       /**
     * <p>Method that send mail for reset the user's password</p>
     * @param reset
     * @return {@javax.ws.rs.core.Response}
     */
    Response sendResetPassword(ResetUserPasswordDTO reset);
      /**
     * <p>Method that consults the expiration of url</p>
     * @param user
     * @return {@javax.ws.rs.core.Response}
     */
    Response isExpireUrl(Users user);
    
    
    Response getParameterPolitics();
    
    Response updateParameterPolitics(CoreParameter coreParameter);

    /**
     * method is responsible for returning all profiles
     * @return {@javax.ws.rs.core.Response}
     */
    Response getAllRoles();

    /**
     * method is responsible for returning all visible profiles
     * @param lang
     * @return {@javax.ws.rs.core.Response}
     */
    Response getAllVisibleRoles(String lang);

    /**
     * Method that creates the profile in the system
     * @param profile
     * @return {@javax.ws.rs.core.Response}
     */
    Response createProfile(Profile profile);

    /**
     * Method that updates the profile in the system
     * @param profile
     * @return {@javax.ws.rs.core.Response}
     */
    Response updateProfile(Profile profile);

    /**
     * Method that erases a profile
     * @param profileId
     * @return {@javax.ws.rs.core.Response}
     */
    Response deleteProfile(long profileId);

    /**
     * Method that lists all profiles
     * @return {@javax.ws.rs.core.Response}
     */
    Response getProfiles();

    /**
     * Method that lists all user ldap Server
     * @return {@javax.ws.rs.core.Response}
     */
    Response getLdapUsers();

    /**
     * Method that lists all category profiles
     *
     * @return {@javax.ws.rs.core.Response}
     */
    Response getCategoryProfiles();
}
