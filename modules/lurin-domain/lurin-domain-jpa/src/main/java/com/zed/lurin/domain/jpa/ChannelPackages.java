package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>Entity that represents a channel packages in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "fv_channel_packages")
public class ChannelPackages  {

    /**
     * Id
     */
    @Id
    @Column(name = "package_id", nullable = false)
    @GenericGenerator(
            name = "channelPckSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "FV_CHANNEL_PACKAGES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "channelPckSeqId")
    private long code;

    @Column(name="productid", nullable = false)
    private long productId;

    /**
     * Product Name the Channel Packages
     */
    @Column(name="productname", nullable = false)
    private String productName;

    /**
     * External id Channel Packages
     */
    @Column(name="externalid", nullable = false)
    private String externalId;

    /**
     * Technologies the Channel Packages
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="technology_id",
            referencedColumnName = "technology_id",
            nullable = false
    )
    private CategoryTechnologies categoryTechnologies;

    /**
     * date create the Channel Packages
     */
    @Column(name="date_create", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    /**
     * User Create the Channel Packages
     */
    @Column(name="user_create", nullable = false)
    private String userCreate;

    /**
     * date modify the Channel Packages
     */
    @Column(name="date_modify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModify;

    /**
     * User Create the Channel Packages
     */
    @Column(name="user_modify")
    private String userModify;

    /**
     * Status the Channel Packages
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="status_id",
            referencedColumnName = "status_id",
            nullable = false
    )
    private CategoryStatus status;

    @PrePersist
    void onCreate() {
        this.setDateCreate(new Timestamp((new Date()).getTime()));
    }

    @PreUpdate
    void onPersist() {
        this.setDateModify(new Timestamp((new Date()).getTime()));
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public CategoryTechnologies getCategoryTechnologies() {
        return categoryTechnologies;
    }

    public void setCategoryTechnologies(CategoryTechnologies categoryTechnologies) {
        this.categoryTechnologies = categoryTechnologies;
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

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

}


