package com.zed.lurin.security.users.services;

import com.zed.lurin.domain.jpa.ResetUserPass;
import com.zed.lurin.domain.jpa.SecurityCategoryStatus;
import com.zed.lurin.domain.jpa.Users;
import com.zed.lurin.domain.jpa.UsersControlAccess;
import com.zed.lurin.domain.jpa.view.DataSourceUserTokenView;
import com.zed.lurin.domain.jpa.view.MenuByUserNameView;
import com.zed.lurin.domain.jpa.view.RolesByUserNameView;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Abstract Class where the methods that manage the users  are implemented</p>
 * @author Francisco Lujano
 */
public interface IUsersServices {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.security.services/UsersServicesImpl!com.zed.lurin.security.users.services.IUsersServices";

    /**
     * method that creates a new access control for a user
     * @param usersControlAccess
     * @return Object UsersControlAccess
     */
    UsersControlAccess createUserControlAccess(UsersControlAccess usersControlAccess);

    /**
     * Method that a user returns by his username
     * @param userName
     * @return {@link Users}
     */
    Users getUserForUserName(String userName);

    /**
     * Method that validates if the token exists
     * @param token
     * @return true is existe | false is not exist
     */
    boolean isExistToken(String token);

    /**
     * Method that returns a list of results based on a token
     * @param token
     * @return List to {@link UsersControlAccess}
     */
    List<UsersControlAccess> getUserControlAccessByToken(String token);

    /**
     * method that deactivates a token
     * @param usersControlAccessList
     * @return List token deactive
     */
    List<UsersControlAccess> deactivateTokens(List<UsersControlAccess> usersControlAccessList);

    /**
     * Method that updates a user access control object
     * @param usersControlAccess
     * @return
     */
    UsersControlAccess updateUsersControlAccess(UsersControlAccess usersControlAccess);

    /**
     * Method that look for the inactive token in the system to close them
     * @param now
     * @param codeActiveStatus
     */
    void setUserControlAccessInvalidate(Timestamp now, long codeActiveStatus);

    /**
     * method that update event time for user control access
     * @param usersControlAccessList
     * @return List the user control update
     */
    List<UsersControlAccess> updateEventTime(List<UsersControlAccess> usersControlAccessList);

    /**
     * Method that validates login and password in system
     * @param user
     * @return
     */
    List<Users> validateUserNameAndPassword(Users user);

    /**
     * method that validates login and token in system for logout
     * @param user
     * @param token
     * @return
     */
    List<UsersControlAccess> validateUserNameAndToken(Users user, String token);

    /**
     * method that return datasources asigned by user
     * @param user
     * @param token
     * @param carrierId
     * @return
     */
    List<DataSourceUserTokenView> getDataSourceFromUserAndToken(Users user, String token, long carrierId);

    /**
     * method that return menu for username
     * @param user
     * @return
     */
    List<MenuByUserNameView> getMenuFromUserName(Users user);

    /**
     * method that return roles for username
     * @param user
     * @return
     */
    List<RolesByUserNameView> getRoleFromUserName(Users user);

    /**
     * Method that returns a list of results based on a user
     * @param user
     * @return List to {@link UsersControlAccess}
     */
    List<UsersControlAccess> getUserControlAccessByUser(Users user);
    
    
     /**
     * Method that validates the expiration of the user password
     */
    void validateExpirationUsersPassword();
    
    /**
     * Method that resets the user's password
     * @param user
     */
    void resetUsersPassword(Users user);
     /**
     * Method that send mail for reset the user's password
     * @param user
     */
    void sendresetUsersPassword(Users user,String url);
    /**
     * Method that get ResetUserPass
     * @param idUser
     */
    ResetUserPass getResetUserPass(long idUser);
    
      /**
     * Method that resets the user's password
     * @param user
     */
    void setUsersPassword(Users user);

    /**
     * Method that valid if user exist for username
     * @param userName
     * @return {@link Users}
     */
    boolean isUserExitsForUserName(String userName);
    
	
	String sendRecoverPasswordMail(String email);

}
