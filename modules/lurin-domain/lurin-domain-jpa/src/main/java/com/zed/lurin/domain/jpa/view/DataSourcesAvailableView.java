package com.zed.lurin.domain.jpa.view;

import com.zed.lurin.domain.jpa.SecurityCategoryStatus;
import com.zed.lurin.domain.jpa.SourceType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a Data Sources in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "V_DS_AVAILABLE")
public class DataSourcesAvailableView {

    /**
     * data sources id
     */
    @Id
    @Column(name = "DATA_SOURCE_ID", nullable = false)
    @GenericGenerator(
            name = "dsSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "DATA_SOURCES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "dsSeqId")
    private long code;

    /**
     * jndi name
     */
    @Column(name="NAME", nullable = false)
    private String jndiName;

    /**
     * ip number
     */
    @Column(name="IP", nullable = false)
    private String ip;
    
    /**
     * port number
     */
    @Column(name="PORT", nullable = false)
    private Long port;

    /**
     * port number
     */
    @Column(name="PORT2")
    private Long port2;

    /**
     * port number
     */
    @Column(name="PORT3")
    private Long port3;
    
    /**
     * Indicates whether the record can be edited (Y) or not (N), this from the User interfaces.
     */
    @Column(name="EDITABLE", nullable = false)
    private char editable;

    /**
     * SID ORACLE
     */
    @Column(name="SID_VALUE", nullable = false)
    private String sid;

    /**
     * USERNAME ORACLE
     */
    @Column(name="USERNAME", nullable = false)
    private String userName;

    /**
     * PASSWORD ORACLE
     */
    @Column(name="PASSWORD", nullable = false)
    private String password;
    
    /**
     * Source Type
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="SOURCE_TYPE_ID",
            referencedColumnName = "SOURCE_TYPE_ID",
            nullable = true
    )
    private SourceType sourceType;
    
    /**
     * Status
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="STATUS_ID",
            referencedColumnName = "STATUS_ID",
            nullable = false
    )
    private SecurityCategoryStatus status;


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    public SecurityCategoryStatus getStatus() {
        return status;
    }

    public void setStatus(SecurityCategoryStatus status) {
        this.status = status;
    }
    
    public String getIp() {
    	return ip;
    }
    
    public void setIp(String ip) {
    	this.ip = ip;
    }
    
    public Long getPort() {
    	return port;
    }
    
    public void setPort(Long port) {
    	this.port = port;
    }
    
    public char getEditable() {
    	return editable;
    }
    
    public void setEditable(char editable) {
    	this.editable = editable;
    }
    public SourceType getSourceType() {
    	return sourceType;
    }
    
    public void setSourceType(SourceType sourceType) {
    	this.sourceType = sourceType;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPort2() {
        return port2;
    }

    public void setPort2(Long port2) {
        this.port2 = port2;
    }

    public Long getPort3() {
        return port3;
    }

    public void setPort3(Long port3) {
        this.port3 = port3;
    }
}