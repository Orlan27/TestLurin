/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zed.lurin.domain.jpa;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Llince
 */
@Entity
@Table(name = "V_REPORT_CAMPAIGN_CLIENTS")
public class CampaignReport {

    @Id
    @Column(name = "STBID")
    private Long idStb;
    @Column(name = "IDCLIENTE")
    private Long idClient;
    @Column(name = "PROVISIONING")
    private String isProvisioning;  // SI o NO
    @Column(name = "CAMPAIGN_ID")
    private Long campaignId;
    @Column(name = "CAMPAIGN")
    private String campaignName;
    @Column(name = "FREEVIEW_ID")
    private Long freeViewId;
    @Column(name = "FREVIEW")
    private String freeViewName;
    @Column(name = "COMMERCIAL_ZONE")
    private String comercialZone;
    @Column(name = "DATE_INI_SCHEDULE")
    private Date campaignIni;
    @Column(name = "DATE_FIN_SCHEDULE")
    private Date canpmaignEnd;
    @Column(name = "OPERADORA")
    private String operadora;
    @Column(name = "CARRIER_ID")
    private Long carrierId;
    @Column(name = "STATUS_ID")
    private Long statusId;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "SCH_STATUS_ID")
    private Long schStatusId;
    @Column(name = "SCH_STATUS")
    private String schStatus;

    public Long getIdStb() {
        return idStb;
    }

    public void setIdStb(Long idStb) {
        this.idStb = idStb;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getIsProvisioning() {
        return isProvisioning;
    }

    public void setIsProvisioning(String isProvisioning) {
        this.isProvisioning = isProvisioning;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Long getFreeViewId() {
        return freeViewId;
    }

    public void setFreeViewId(Long freeViewId) {
        this.freeViewId = freeViewId;
    }
    
    public String getFreeViewName() {
        return freeViewName;
    }

    public void setFreeViewName(String freeViewName) {
        this.freeViewName = freeViewName;
    }

    public String getComercialZone() {
        return comercialZone;
    }

    public void setComercialZone(String comercialZone) {
        this.comercialZone = comercialZone;
    }

    public Date getCampaignIni() {
        return campaignIni;
    }

    public void setCampaignIni(Date campaignIni) {
        this.campaignIni = campaignIni;
    }

    public Date getCanpmaignEnd() {
        return canpmaignEnd;
    }

    public void setCanpmaignEnd(Date canpmaignEnd) {
        this.canpmaignEnd = canpmaignEnd;
    }

    public String getOperadora() {
        return operadora;
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getSchStatusId() {
        return schStatusId;
    }

    public void setSchStatusId(Long schStatusId) {
        this.schStatusId = schStatusId;
    }
    
    public String getSchStatus() {
        return schStatus;
    }

    public void setSchStatus(String schStatus) {
        this.schStatus = schStatus;
    }

}
