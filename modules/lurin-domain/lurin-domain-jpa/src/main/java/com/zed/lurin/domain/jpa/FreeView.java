package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>Entity that represents a free views in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "FV_FREE_VIEWS")
public class FreeView  implements Serializable{

    /**
     * Free View Id
     */
    @Id
    @Column(name = "free_view_id", nullable = false)
    @GenericGenerator(
            name = "clFvSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "FV_FREE_VIEWS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "clFvSeqId")
    private long freeViewId;

    /**
     * Name the Free View
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * date create the Free View
     */
    @Column(name="date_create", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    /**
     * User Create the Free View
     */
    @Column(name="user_create", nullable = false)
    private String userCreate;

    /**
     * date modify the Free View
     */
    @Column(name="date_modify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModify;

    /**
     * User Create the Free View
     */
    @Column(name="user_modify")
    private String userModify;

    /**
     * List to packages id
     */
    @Transient
    private List<Long> packagesId;

    /**
     * Status the Free View
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="status_id",
            referencedColumnName = "status_id",
            nullable = false
    )
    private CategoryStatus status;

    /**
     * Technologies the Free View
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="technology_id",
            referencedColumnName = "technology_id",
            nullable = false
    )
    private CategoryTechnologies categoryTechnologies;

    /**
     * Link documentation https://vladmihalcea.com/2017/05/10/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
     */
    @ManyToMany( targetEntity=ChannelPackages.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinTable(name="fv_fv_chann_packs",
            joinColumns = {@JoinColumn(name = "free_view_id")},
            inverseJoinColumns = {@JoinColumn(name="package_id")})
    private Set<ChannelPackages> channelPackages = new HashSet<ChannelPackages>(0);



    @PrePersist
    void onCreate() {
        this.setDateCreate(new Timestamp((new Date()).getTime()));
    }

    @PreUpdate
    void onPersist() {
        this.setDateModify(new Timestamp((new Date()).getTime()));
    }

    public long getFreeViewId() {
        return freeViewId;
    }

    public void setFreeViewId(long freeViewId) {
        this.freeViewId = freeViewId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public Date getDateModify() {
        return dateModify;
    }

    public void setDateModify(Date dateModify) {
        this.dateModify = dateModify;
    }

    public String getUserModify() {
        return userModify;
    }

    public void setUserModify(String userModify) {
        this.userModify = userModify;
    }

    public List<Long> getPackagesId() {
        return packagesId;
    }

    public void setPackagesId(List<Long> packagesId) {
        this.packagesId = packagesId;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

    public CategoryTechnologies getCategoryTechnologies() {
        return categoryTechnologies;
    }

    public void setCategoryTechnologies(CategoryTechnologies categoryTechnologies) {
        this.categoryTechnologies = categoryTechnologies;
    }

    public Set<ChannelPackages> getChannelPackages() {
        return channelPackages;
    }

    public void setChannelPackages(Set<ChannelPackages> channelPackages) {
        this.channelPackages = channelPackages;
    }
}
