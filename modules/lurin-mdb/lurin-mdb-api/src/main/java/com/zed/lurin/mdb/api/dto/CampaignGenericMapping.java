package com.zed.lurin.mdb.api.dto;


import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

/**
 * generic class for sending messages jms
 * @author Francisco Lujano
 */
public class CampaignGenericMapping implements Serializable {

    private List<Long> campaignMember;

    private long carrierId;

    private String ipServerCas;

    private String portServerCas;

    private String portServerCas2;

    private String portServerCas3;

    private int initBatch;

    private int endBatch;

    public List<Long> getCampaignMember() {
        return campaignMember;
    }

    public void setCampaignMember(List<Long> campaignMember) {
        this.campaignMember = campaignMember;
    }

    public long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(long carrierId) {
        this.carrierId = carrierId;
    }

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
