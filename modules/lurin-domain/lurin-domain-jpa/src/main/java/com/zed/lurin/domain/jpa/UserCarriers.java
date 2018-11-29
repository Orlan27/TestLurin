package com.zed.lurin.domain.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Entity that represents a user carriers roles in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "USER_CARRIERS")
public class UserCarriers {

    /**
     * user carriers roles Id
     */
    @Id
    @Column(name = "user_carrier_id", nullable = false)
    @GenericGenerator(
            name = "userCarrierId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "USER_CARRIERS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "userCarrierId")
    private long code;

    /**
     * Users
     */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name="user_id")
    @JsonBackReference
    private Users users;

    /**
     * Carriers
     */
    @ManyToOne
    @JoinColumn(name="carrier_id")
    private Carriers carriers;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="userCarriers")
    private Set<UserCarriersRoles> userCarriersRoles = new HashSet<UserCarriersRoles>(0);


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Carriers getCarriers() {
        return carriers;
    }

    public void setCarriers(Carriers carriers) {
        this.carriers = carriers;
    }

    public Set<UserCarriersRoles> getUserCarriersRoles() {
        return userCarriersRoles;
    }

    public void setUserCarriersRoles(Set<UserCarriersRoles> userCarriersRoles) {
        this.userCarriersRoles = userCarriersRoles;
    }
}
