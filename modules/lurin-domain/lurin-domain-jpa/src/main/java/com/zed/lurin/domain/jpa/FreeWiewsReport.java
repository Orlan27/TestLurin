package com.zed.lurin.domain.jpa;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Llince
 */
@Entity
@Table(name = "V_REPORT_FREE_VIEWS")
public class FreeWiewsReport {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "FREE_VIEW_NAME")
    private String freeViewName;
    @Column(name = "TECHNOLOGY_NAME")
    private String tecnologyName;
    @Column(name = "CHANEL")
    private String chanel;
    @Column(name = "DATE_CREATE")
    private Date createDate;
    @Column(name = "CARRIER_ID")
    private Long carrierId;
    @Column(name = "OPERADORA")
    private String operadora;
    @Column(name = "USER_CREATE")
    private String userCreate;
    @Column(name = "DATE_MODIFY")
    private Date modifyDate;
    @Column(name = "USER_MODIFY")
    private String userModify;
    @Column(name = "STATUS")
    private char status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFreeViewName() {
        return freeViewName;
    }

    public void setFreeViewName(String freeViewName) {
        this.freeViewName = freeViewName;
    }

    public String getTecnologyName() {
        return tecnologyName;
    }

    public void setTecnologyName(String tecnologyName) {
        this.tecnologyName = tecnologyName;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getUserModify() {
        return userModify;
    }

    public void setUserModify(String userModify) {
        this.userModify = userModify;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
    
}