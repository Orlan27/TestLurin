/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zed.lurin.domain.dto;

/**
 *
 * @author llince
 */
public class RequestReportCampaignDto {
     private String idStb;
     private String idCliente;
     private String campaignId;
     //private String campaignName;
     private String freeViewId;
     //private String freeViewName;
     private String idCarrier;
     private String statusId;
     private String schStatusId;
     //private String statusName;
     private String typeReport; // PDF ,XLS,CSV 

    public String getIdStb() {
        return idStb;
    }

    public void setIdStb(String idStb) {
        this.idStb = idStb;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getFreeViewId() {
        return freeViewId;
    }

    public void setFreeViewId(String freeViewId) {
        this.freeViewId = freeViewId;
    }

    public String getIdCarrier() {
        return idCarrier;
    }

    public void setIdCarrier(String idCarrier) {
        this.idCarrier = idCarrier;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }    

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }
    
    public String getSchStatusId() {
        return schStatusId;
    }

    public void setSchStatusId(String schStatusId) {
        this.schStatusId = schStatusId;
    }
    
}

