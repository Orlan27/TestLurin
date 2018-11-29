package com.zed.lurin.security.ldap.services;

import com.zed.lurin.domain.dto.UserLdapDTO;

import java.util.List;

public interface ILdapServices {
    /**
     * Method that all user ldap Server
     *
     * @return List<UserLdapDTO
     */
    List<UserLdapDTO> findUserLdap();
}
