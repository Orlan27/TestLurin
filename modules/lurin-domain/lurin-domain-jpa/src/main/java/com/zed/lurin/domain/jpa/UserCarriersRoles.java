package com.zed.lurin.domain.jpa;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a user carriers roles in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "USER_CARRIERS_ROLES")
public class UserCarriersRoles {

    /**
     * user carriers roles Id
     */
    @Id
    @Column(name = "user_carriers_roles_id", nullable = false)
    @GenericGenerator(
            name = "usersCarRolesSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "USER_CARRIERS_ROLES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "usersCarRolesSeqId")
    private long code;


    /**
     * Roles
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="user_carrier_id",
            referencedColumnName = "user_carrier_id",
            nullable = false
    )
//    @JsonManagedReference("userCarriers-UserCarriers")
    private UserCarriers userCarriers;


    /**
     * Roles
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="ROLE_ID",
            referencedColumnName = "ROLE_ID",
            nullable = false
    )
    private Roles role;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public UserCarriers getUserCarriers() {
        return userCarriers;
    }

    public void setUserCarriers(UserCarriers userCarriers) {
        this.userCarriers = userCarriers;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
