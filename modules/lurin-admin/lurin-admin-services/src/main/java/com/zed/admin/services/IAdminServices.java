package com.zed.admin.services;

import com.zed.lurin.domain.dto.MessageCustomDTO;
import com.zed.lurin.domain.dto.UserPasswordChangeDTO;
import com.zed.lurin.domain.jpa.*;
import com.zed.lurin.domain.jpa.view.UserExpiredPasswordView;

import java.util.List;

public interface IAdminServices {
    /**
     *
     * validate if username exists in database
     *
     * @param userName
     * @return
     */
    boolean isExistUserName(String userName);

    /**
     *
     * creating user
     *
     * @param users
     * @return
     */
    Users createUsers(Users users);
    
    /**
    *
    * creating user admin local for operators
    *
    * @param users
    * @return
    */
   Users createUsersAdminLocal(Users users);

    /**
     *
     * update user
     *
     * @param users
     * @return
     */
    Users updateUsers(Users users);

    /**
    *
    * update user admin local
    *
    * @param users
    * @return
    */
   Users updateUsersAdminLocal(Users users);

    /**
     *
     * delete user
     *
     * @param userCode
     * @return
     */
    void deleteUsers(long userCode);

    /**
     * method return list users active
     *
     * @return
     */
    List<Users> getUsers(String token, boolean isAdminLocal);

    /**
     * method return list users carriers active
     *
     * @return
     */
    List<UserCarriers> getUsersCarriers();

    /**
     * method return list datasource active
     * @return
     */
    List<DataSources> getDataSources();

    /**
     * method return list carriers active
     * @return
     */
    List<Carriers> getCarriers();

    /**
     * method return list roles active
     * @return
     */
    List<Roles> getRoles();


    /**
     * method return true if password expired
     * @param userName
     * @return List UserExpiredPasswordView
     */
    List<UserExpiredPasswordView> getPasswordExpiredForUserName(String userName);

    /**
     * Method for update password from user
     * @param userPasswordChangeDTO
     * @return
     */
    MessageCustomDTO changePasswordForUser(UserPasswordChangeDTO userPasswordChangeDTO);
    
    /**
    *
    * Get User in database by username
    *
    * @param userName
    * @return
    */
   Users getUserByUserName(String userName);
}
