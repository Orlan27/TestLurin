package com.zed.lurin.domain.jpa.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>Entity that represents a expired password for user name</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "v_expired_password_from_user")
public class UserExpiredPasswordView {

    /**
     * id
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
     * day for last update
     */
    @Column(name="days", nullable = false)
    private int days;

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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
