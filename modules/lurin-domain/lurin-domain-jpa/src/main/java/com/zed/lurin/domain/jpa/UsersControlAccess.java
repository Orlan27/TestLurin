package com.zed.lurin.domain.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>Entity that represents a user control access in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "USERS_CTRL_ACCESS")
public class UsersControlAccess {

    /**
     * Users Control Access Id
     */
    @Id
    @Column(name = "users_ctrl_access_id", nullable = false)
    @GenericGenerator(
            name = "usersCtrlAccessSeq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "USERS_CTRL_ACCESS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "usersCtrlAccessSeq")
    private long code;

    /**
     * time expired the Users Control Access
     */
    @Column(name="time_expired", nullable = false)
    private int timeExpired;

    /**
     * Token the Users Control Access
     */
    @Column(name="token", nullable = false)
    private String token;


    /**
     * Login time the Users Control Access
     */
    @Column(name="login_time", nullable = false)
    private Timestamp loginTime;

    /**
     * Update event time the Users Control Access
     */
    @Column(name="update_event_time")
    private Timestamp updateEventTime;

    /**
     * Logout time the Users Control Access
     */
    @Column(name="logout_time")
    private Timestamp logoutTime;

    /**
     * I18N Users Control Access
     */
    @Column(name="i18n", nullable = false)
    private String i18n;


    /**
     * Users the Users Control Access
     */
    @ManyToOne(optional = false,cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(
            name="users_id",
            referencedColumnName = "user_id",
            nullable = false
    )
    @JsonBackReference
    private Users user;

    /**
     * Status the Users Control Access
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="status_id",
            referencedColumnName = "status_id",
            nullable = false
    )
    private SecurityCategoryStatus status;

    @PrePersist
    void onCreate() {
        this.setUpdateEventTime(new Timestamp((new Date()).getTime()));
        this.setLoginTime(new Timestamp((new Date()).getTime()));
    }

    @PreUpdate
    void onPersist() {
        this.setUpdateEventTime(new Timestamp((new Date()).getTime()));
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getTimeExpired() {
        return timeExpired;
    }

    public void setTimeExpired(int timeExpired) {
        this.timeExpired = timeExpired;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getUpdateEventTime() {
        return updateEventTime;
    }

    public void setUpdateEventTime(Timestamp updateEventTime) {
        this.updateEventTime = updateEventTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public SecurityCategoryStatus getStatus() {
        return status;
    }

    public void setStatus(SecurityCategoryStatus status) {
        this.status = status;
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

}
