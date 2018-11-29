package com.zed.admin.rest;

import com.zed.lurin.domain.dto.UserPasswordChangeDTO;
import com.zed.lurin.domain.jpa.Users;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface IAdminServicesRest {
    /**
     * <p>method that creates a users</p>
     *
     * @param users
     * @return {@javax.ws.rs.core.Response}
     */
    Response createUser(Users users, boolean isAdminLocal);

    /**
     * <p>method that update a users</p>
     *
     * @param users
     * @return {@javax.ws.rs.core.Response}
     */
    Response updateUser(Users users, boolean isAdminLocal);

    /**
     * <p>method validate if username exist</p>
     *
     * @param userName
     * @return {@javax.ws.rs.core.Response}
     */
    Response isValidUserName(String userName);

    /**
     * <p>method remove user</p>
     *
     * @param codeUser
     * @return {@javax.ws.rs.core.Response}
     */
    Response deleteUser(long codeUser);


    /**
     * <p>method return list datasource active</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    Response getDataSources();

    /**
     * <p>method return list carriers active</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    Response getCarriers();

    /**
     * <p>method return list roles active</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
//    Response getRoles();

    /**
     * <p>method return true if password is expired
     * @param userName
     * @return
     */
    Response isExpiredPasswordForUserName(String userName);

    /**
     * <p>method return list users active</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    Response getUsers(String token, boolean isAdminLocal);

    /**
     * <p>method that update a password users</p>
     *
     * @param userPasswordChangeDTO
     * @return {@javax.ws.rs.core.Response}
     */
    Response changePasswordForUser(UserPasswordChangeDTO userPasswordChangeDTO);


    /**
     * Method return relations for users
     * @return
     */
    Response getUsersCarrier();
    
    /**
     * <p>method that get User by username</p>
     *
     * @param userName
     * @return {@javax.ws.rs.core.Response}
     */
    Response getUserByUserName(String userName);
}
