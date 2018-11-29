package com.zed.lurin.domain.jpa.view;

import com.zed.lurin.domain.jpa.Carriers;
import com.zed.lurin.domain.jpa.SecurityCategoryStatus;

import javax.persistence.*;

/**
 * <p>Entity that represents a users in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "v_ds_from_user_token")
public class DataSourceUserTokenView {

    /**
     * View From User Token Id
     */
    @Id
    @Column(name = "id", nullable = false)
    private long code;

    /**
     * jndi name the View From User Token
     */
    @Column(name="jndi_name", nullable = false)
    private String jndiName;


    /**
     * Password the View From User Token
     */
    @Column(name="login", nullable = false)
    private String login;

    /**
     * First name the View From User Token
     */
    @Column(name="token", nullable = false)
    private String token;


    /**
     * Status User the View From User Token
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="user_status",
            referencedColumnName = "status_id",
            nullable = false
    )
    private SecurityCategoryStatus statusUser;

    /**
     * Status Data Source the View From User Token
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="ds_status",
            referencedColumnName = "status_id",
            nullable = false
    )
    private SecurityCategoryStatus statusDataSource;

    /**
     * Status User Ctrl Access the View From User Token
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="user_ctrl_acs_sts",
            referencedColumnName = "status_id",
            nullable = false
    )
    private SecurityCategoryStatus statusUserCtrlAccess;

    /**
     * Carriers
     */
    @Column(name="carrier_id", nullable = false)
    private long carrierId;


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SecurityCategoryStatus getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(SecurityCategoryStatus statusUser) {
        this.statusUser = statusUser;
    }

    public SecurityCategoryStatus getStatusDataSource() {
        return statusDataSource;
    }

    public void setStatusDataSource(SecurityCategoryStatus statusDataSource) {
        this.statusDataSource = statusDataSource;
    }

    public SecurityCategoryStatus getStatusUserCtrlAccess() {
        return statusUserCtrlAccess;
    }

    public void setStatusUserCtrlAccess(SecurityCategoryStatus statusUserCtrlAccess) {
        this.statusUserCtrlAccess = statusUserCtrlAccess;
    }

    public long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(long carrierId) {
        this.carrierId = carrierId;
    }
}
