package com.zed.lurin.quartz.dto;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * Class POJO
 */
public class CampaignScheduler {


    private List<Long> campaingMember;

    private long campaingId;

//    private FreeView freeView;

    private Date startAT;

    private String nameTrigger;

    private String nameJob;

    private TYPE_CAS_COMMAND type;

    private long carrierId;

    private String ipServerCas;

    private String portServerCas;

    private String portServerCas2;

    private String portServerCas3;

    private int initBatch;

    private int endBatch;

    public String getIpServerCas() {
        return ipServerCas;
    }

    public void setIpServerCas(String ipServerCas) {
        this.ipServerCas = ipServerCas;
    }

    public String getPortServerCas() {
        return portServerCas;
    }

    public void setPortServerCas(String portServerCas) {
        this.portServerCas = portServerCas;
    }

    public enum TYPE_CAS_COMMAND {ACTIVATION, DEACTIVATION}

    public List<Long> getCampaingMember() {
        return campaingMember;
    }

    public void setCampaingMember(List<Long> campaingMember) {
        this.campaingMember = campaingMember;
    }

    public long getCampaingId() {
        return campaingId;
    }

    public void setCampaingId(long campaingId) {
        this.campaingId = campaingId;
    }

    public Date getStartAT() {
        return startAT;
    }

    public void setStartAT(Date startAT) {
        this.startAT = startAT;
    }

    public String getNameTrigger() {
        return nameTrigger;
    }

    public void setNameTrigger(String nameTrigger) {
        this.nameTrigger = nameTrigger;
    }

    public String getNameJob() {
        return nameJob;
    }

    public void setNameJob(String nameJob) {
        this.nameJob = nameJob;
    }

    public TYPE_CAS_COMMAND getType() {
        return type;
    }

    public void setType(TYPE_CAS_COMMAND type) {
        this.type = type;
    }

    public long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(long carrierId) {
        this.carrierId = carrierId;
    }

    public String getPortServerCas2() {
        return portServerCas2;
    }

    public void setPortServerCas2(String portServerCas2) {
        this.portServerCas2 = portServerCas2;
    }

    public String getPortServerCas3() {
        return portServerCas3;
    }

    public void setPortServerCas3(String portServerCas3) {
        this.portServerCas3 = portServerCas3;
    }

    public int getInitBatch() {
        return initBatch;
    }

    public void setInitBatch(int initBatch) {
        this.initBatch = initBatch;
    }

    public int getEndBatch() {
        return endBatch;
    }

    public void setEndBatch(int endBatch) {
        this.endBatch = endBatch;
    }
}
