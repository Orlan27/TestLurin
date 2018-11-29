/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zed.lurin.security.dto;

import com.zed.lurin.domain.jpa.Users;

/**
 *
 * @author AppDes
 */
public class ResetUserPasswordDTO {
    private Users user;
    
    private String url;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
