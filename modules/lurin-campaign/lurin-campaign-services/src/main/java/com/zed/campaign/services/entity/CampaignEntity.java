package com.zed.campaign.services.entity;

import javax.ws.rs.FormParam;

public class CampaignEntity {

    @FormParam("name")
    private String name;

    @FormParam("zoneId")
    private long zoneId;

    @FormParam("typeLoadId")
    private long typeLoadId;

    @FormParam("freeviewId")
    private long freeviewId;

    @FormParam("dateIniSchedule")
    private String dateIniSchedule;

    @FormParam("dateFinSchedule")
    private String dateFinSchedule;

    @FormParam("file")
    private byte[] file;

//    @FormParam("initialMessage")
//    private long initialMessage;
//
//    @FormParam("finalMessage")
//    private long finalMessage;

    @FormParam("priority")
    private int priority;

    @FormParam("validateBefore")
    private long validateBefore;

    @FormParam("userCreate")
    private String userCreate;

    @FormParam("schStatusid")
    private long schStatusid;

    @FormParam("carrierId")
    private long carrierId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getZoneId() {
        return zoneId;
    }

    public void setZoneId(long zoneId) {
        this.zoneId = zoneId;
    }

    public long getTypeLoadId() {
        return typeLoadId;
    }

    public void setTypeLoadId(long typeLoadId) {
        this.typeLoadId = typeLoadId;
    }

    public long getFreeviewId() {
        return freeviewId;
    }

    public void setFreeviewId(long freeviewId) {
        this.freeviewId = freeviewId;
    }

    public String getDateIniSchedule() {
        return dateIniSchedule;
    }

    public void setDateIniSchedule(String dateIniSchedule) {
        this.dateIniSchedule = dateIniSchedule;
    }

    public String getDateFinSchedule() {
        return dateFinSchedule;
    }

    public void setDateFinSchedule(String dateFinSchedule) {
        this.dateFinSchedule = dateFinSchedule;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
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

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public long getSchStatusid() {
        return schStatusid;
    }

    public void setSchStatusid(long schStatusid) {
        this.schStatusid = schStatusid;
    }

    public long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(long carrierId) {
        this.carrierId = carrierId;
    }
}
