package com.zed.lurin.process.cas.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Llince
 */
@Deprecated
public class CampaingCAS implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String idTransaction;
    private String idSource;
    private String idDest;
    private String hostCas;
    private String portCas;
    private Date iniDate;
    private Date endDate;
    private TYPE_CAS_COSTUMERS type;

    public enum TYPE_CAS_COSTUMERS {
        SMARTCAD, IDCLIENT
    }
    private List<Costumers> listCostumers;
    private TYPE_CAS_EVENT event;

    public enum TYPE_CAS_EVENT {
        CANCELED, UPDATE, CREATE, SUSPEND
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdSource() {
        return idSource;
    }

    public void setIdSource(String idSource) {
        this.idSource = idSource;
    }

    public String getIdDest() {
        return idDest;
    }

    public void setIdDest(String idDest) {
        this.idDest = idDest;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHostCas() {
        return hostCas;
    }

    public String getPortCas() {
        return portCas;
    }

    public Date getIniDate() {
        return iniDate;
    }

    public void setIniDate(Date iniDate) {
        this.iniDate = iniDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TYPE_CAS_COSTUMERS getType() {
        return type;
    }

    public void setType(TYPE_CAS_COSTUMERS type) {
        this.type = type;
    }

    public List<Costumers> getListCostumers() {
        return listCostumers;
    }

    public void setListCostumers(List<Costumers> listCostumers) {
        this.listCostumers = listCostumers;
    }

    public TYPE_CAS_EVENT getEvent() {
        return event;
    }

    public void setEvent(TYPE_CAS_EVENT event) {
        this.event = event;
    }
    



}
