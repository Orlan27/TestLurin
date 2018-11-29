package com.zed.lurin.domain.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Entity that represents a users in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "USERS")
public class Users {

    /**
     * Users Id
     */
    @Id
    @Column(name = "user_id", nullable = false)
    @GenericGenerator(
            name = "usersSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "USERS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "usersSeqId")
    private long code;

    /**
     * Login the Users
     */
    @Column(name="login", nullable = false)
    private String login;


    /**
     * Password the Users
     */
    @Column(name="password", nullable = false)
    private String password;

    /**
     * First name the Users
     */
    @Column(name="first_name", nullable = false)
    private String firstName;

    /**
     * Middle name the Users
     */
    @Column(name="middle_name", nullable = false)
    private String middleName;

    /**
     * Last name the Users
     */
    @Column(name="last_name", nullable = false)
    private String lastName;

    /**
     * Second last name the Users
     */
    @Column(name="second_last_name", nullable = false)
    private String secondLastName;

    /**
     * Email the Users
     */
    @Column(name="email", nullable = false)
    private String email;

    /**
     * Password expired the Users
     */
    @Column(name="password_expired", length = 2, nullable = false)
    private String passwordExpired;

    /**
     * Last Update Pass the Users
     */
    @Column(name="last_update_pass", length = 2, nullable = false)
    private Date lastUpdatePass;

    @PrePersist
    void onCreate() {
        this.setLastUpdatePass(new Timestamp((new Date()).getTime()));
    }

    @PreUpdate
    void onPersist() {
        this.setLastUpdatePass(new Timestamp((new Date()).getTime()));
    }

    /**
     * i18n Selected
     */
    @Transient
    private String lang;

    /**
     * Status the Users
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="status_id",
            referencedColumnName = "status_id",
            nullable = false
    )
    private SecurityCategoryStatus status;

    /**
     * Companies
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="company_id",
            referencedColumnName = "company_id",
            nullable = false
    )
    private Companies companies;


    /**
     * Profile
     */
    @ManyToOne(optional = false)
    @JoinColumn(name="PROFILE_ID",
            referencedColumnName = "PROFILE_ID",
            nullable=false)
    private Profile profile;


    @OneToMany(cascade=CascadeType.ALL, mappedBy="users")
    @JsonBackReference
    private Set<UserCarriers> userCarriers;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(String passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public Date getLastUpdatePass() {
        return lastUpdatePass;
    }

    public void setLastUpdatePass(Date lastUpdatePass) {
        this.lastUpdatePass = lastUpdatePass;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public SecurityCategoryStatus getStatus() {
        return status;
    }

    public void setStatus(SecurityCategoryStatus status) {
        this.status = status;
    }

    public Companies getCompanies() {
        return companies;
    }

    public void setCompanies(Companies companies) {
        this.companies = companies;
    }

    public Set<UserCarriers> getUserCarriers() {
        return userCarriers;
    }

    public void setUserCarriers(Set<UserCarriers> userCarriers) {
        this.userCarriers = userCarriers;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
