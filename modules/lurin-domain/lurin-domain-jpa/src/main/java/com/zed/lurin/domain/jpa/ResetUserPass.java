/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zed.lurin.domain.jpa;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Llince
 */
@Entity
@Table(name = "RESET_USER_PASSWORD")
public class ResetUserPass {
      /**
     * ResetUserPass Id
     */
    @Id
    @Column(name = "ID_RESET", nullable = false)
    @GenericGenerator(
            name = "resetSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "RESET_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "resetSeqId")
    private long code;
    
    @Column(name="ID_USER", nullable = false)
    private long idUser;
    @Column(name="URL", nullable = false)
    private String url;
    
   @Column(name="EXPIRED_URL", nullable = false)
    private String expiredUrl;
    
    @Column(name="CREATE_URL_DATE", nullable = false)
    private Date createUrl;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpiredUrl() {
        return expiredUrl;
    }

    public void setExpiredUrl(String expiredUrl) {
        this.expiredUrl = expiredUrl;
    }

    public Date getCreateUrl() {
        return createUrl;
    }

    public void setCreateUrl(Date createUrl) {
        this.createUrl = createUrl;
    }
    
    
}
