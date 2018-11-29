package com.zed.lurin.domain.jpa.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>Entity that represents a menus for user name</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "V_ROLES_FROM_USER")
public class RolesByUserNameView {

    /**
     * Role id
     */
    @Id
    @Column(name = "id", nullable = false)
    private long code;

    /**
     * Login
     */
    @Column(name="login", nullable = false)
    private String login;

    /**
     * role
     */
    @Column(name="role", nullable = false)
    private String role;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
