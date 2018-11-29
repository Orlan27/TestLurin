package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Llince
 * @author refactor Francisco Lujano
 */
@Entity
@Table(name = "FV_CAMPAIGNS")
public class Campaigns implements Serializable {

    /**
     * Campaigns Id
     */
    @Id
    @Column(name = "CAMPAIGN_ID", nullable = false)
    @GenericGenerator(
            name = "cmSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "FV_CAMPAIGNS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "cmSeqId")
    private Long campaignId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "zone_id",
            referencedColumnName = "zone_id",
            nullable = true
    )
    private CommercialZone commercialZone;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "type_load_id",
            referencedColumnName = "TYPE_LOAD_ID",
            nullable = true
    )
    private TypeLoad typeLoad;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "FREE_VIEW_ID",
            referencedColumnName = "FREE_VIEW_ID",
            nullable = true
    )
    private FreeView freeViewId;

    @Column(name = "date_ini_schedule", nullable = false)
    private Date dateIniSchedule;

    @Column(name = "DATE_FIN_SCHEDULE", nullable = false)
    private Date dateFinSchedule;

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "INITIAL_MESSAGE",
            referencedColumnName = "MESSAGE_ID",
            nullable = true
    )
    private Messages messagesInitial;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "FINAL_MESSAGE",
            referencedColumnName = "MESSAGE_ID",
            nullable = true
    )
    private Messages messagesFinal;

    @Column(name = "priority", nullable = false)
    private int priority;

    @Column(name = "validate_before", nullable = true)
    private long validateBefore;

    @Column(name = "DATE_CREATE", nullable = true)
    private Date dateCreate;

    @Column(name = "USER_CREATE", nullable = true)
    private String userCreate;

    @Column(name = "DATE_MODIFY", nullable = true)
    private Date dateModify;

    @Column(name = "USER_MODIFY", nullable = true)
    private String userModify;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "sch_status_id",
            referencedColumnName = "sch_status_id",
            nullable = true
    )
    private CategorySchStatus catSchStatus;

    @Column(name = "comments", nullable = true)
    private String comment;

//    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="campaigns")
//    private Set<CampaignMembers> campaignMembers = new HashSet<>(0);


    @Transient
    private long carrierId;

    @PrePersist
    void onCreate() {
        this.setDateCreate(new Timestamp((new Date()).getTime()));
    }

    @PreUpdate
    void onPersist() {
        this.setDateModify(new Timestamp((new Date()).getTime()));
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommercialZone getCommercialZone() {
        return commercialZone;
    }

    public void setCommercialZone(CommercialZone commercialZone) {
        this.commercialZone = commercialZone;
    }

    public TypeLoad getTypeLoad() {
        return typeLoad;
    }

    public void setTypeLoad(TypeLoad typeLoad) {
        this.typeLoad = typeLoad;
    }

    public FreeView getFreeViewId() {
        return freeViewId;
    }

    public void setFreeViewId(FreeView freeViewId) {
        this.freeViewId = freeViewId;
    }

    public Date getDateIniSchedule() {
        return dateIniSchedule;
    }

    public void setDateIniSchedule(Date dateIniSchedule) {
        this.dateIniSchedule = dateIniSchedule;
    }

    public Date getDateFinSchedule() {
        return dateFinSchedule;
    }

    public void setDateFinSchedule(Date dateFinSchedule) {
        this.dateFinSchedule = dateFinSchedule;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Messages getMessagesInitial() {
        return messagesInitial;
    }

    public void setMessagesInitial(Messages messagesInitial) {
        this.messagesInitial = messagesInitial;
    }

    public Messages getMessagesFinal() {
        return messagesFinal;
    }

    public void setMessagesFinal(Messages messagesFinal) {
        this.messagesFinal = messagesFinal;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getValidateBefore() {
        return validateBefore;
    }

    public void setValidateBefore(long validateBefore) {
        this.validateBefore = validateBefore;
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

    public CategorySchStatus getCatSchStatus() {
        return catSchStatus;
    }

    public void setCatSchStatus(CategorySchStatus catSchStatus) {
        this.catSchStatus = catSchStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

//    public Set<CampaignMembers> getCampaignMembers() {
//        return campaignMembers;
//    }
//
//    public void setCampaignMembers(Set<CampaignMembers> campaignMembers) {
//        this.campaignMembers = campaignMembers;
//    }

    public long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(long carrierId) {
        this.carrierId = carrierId;
    }
}
