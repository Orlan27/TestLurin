package com.zed.lurin.domain.dto;

public class UserPasswordChangeDTO {

    /**
     * Code User
     */
    private long code;

    /**
     * Old Password User
     */
    private String oldPassword;
    /**
     * New Password User
     */
    private String newPassword;
    
    /**
     * Email User
     */
    private String email;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
