package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>Entity that represents a commercial zone in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "FV_COMMERCIAL_ZONES")
public class CommercialZone implements Serializable {

    /**
     * Commercial Zone Id
     */
    @Id
    @Column(name = "zone_id", nullable = false)
    @GenericGenerator(
            name = "clZsSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "FV_COMMERCIAL_ZONES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "clZsSeqId")
    private long zone;

    /**
     * Name the Commercial Zone
     */
    @Column(name="commercial_zone", nullable = false)
    private String commercialZone;

    @Column(name = "channelmapid", nullable = false)
    private String  channelMapId;

    @Column(name="externalid", nullable = false)
    private String externalId;

    /**
     * date create the Commercial Zone
     */
    @Column(name="date_create", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    /**
     * User Create the Commercial Zone
     */
    @Column(name="user_create", nullable = false)
    private String userCreate;

    /**
     * date modify the Commercial Zone
     */
    @Column(name="date_modify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModify;

    /**
     * User Create the Commercial Zone
     */
    @Column(name="user_modify")
    private String userModify;

    /**
     * Status the Commercial Zone
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

    public long getZone() {
        return zone;
    }

    public void setZone(long zone) {
        this.zone = zone;
    }

    public String getCommercialZone() {
        return commercialZone;
    }

    public void setCommercialZone(String commercialZone) {
        this.commercialZone = commercialZone;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

    public String getChannelMapId() {
        return channelMapId;
    }

    public void setChannelMapId(String channelMapId) {
        this.channelMapId = channelMapId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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
}
