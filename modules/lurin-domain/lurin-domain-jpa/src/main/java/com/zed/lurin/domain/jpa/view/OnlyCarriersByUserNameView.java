package com.zed.lurin.domain.jpa.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>Entity that represents a only carriers for user name</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "v_only_carriers_from_user")
public class OnlyCarriersByUserNameView {

    /**
     * Role id
     */
    @Id
    @Column(name = "id", nullable = false)
    private long code;

    /**
     * User Id
     */
    @Column(name = "user_id", nullable = false)
    private long userId;

    /**
     * Login
     */
    @Column(name="login", nullable = false)
    private String login;

    /**
     * ID Carrier
     */
    @Column(name="carrier_id", nullable = false)
    private String idCarrier;

    /**
     * Name Carrier
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * DataSources Id
     */
    @Column(name="theme_id", nullable = true)
    private String idTheme;

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

    public String getIdCarrier() {
        return idCarrier;
    }

    public void setIdCarrier(String idCarrier) {
        this.idCarrier = idCarrier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(String idTheme) {
        this.idTheme = idTheme;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
