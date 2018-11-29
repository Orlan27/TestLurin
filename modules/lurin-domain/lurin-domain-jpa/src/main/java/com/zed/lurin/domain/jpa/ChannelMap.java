package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a category status in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "TV_CHANNELMAP")
public class ChannelMap {

    /**
     * id
     */
    @Id
    @Column(name = "channelmapid", nullable = false)
    private long  code;

    @Column(name="channelmapcrmid", nullable = false)
    private String channelMapCRMID;

    @Column(name="channelmapname", nullable = false)
    private String channelMapName;

    @Column(name="externalid", nullable = false)
    private String externalId;

    @Column(name="controllerid", nullable = false)
    private String controllerId;

    @Column(name="networkid", nullable = false)
    private String networkId;

    @Column(name="pid", nullable = false)
    private String pid;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getChannelMapCRMID() {
        return channelMapCRMID;
    }

    public void setChannelMapCRMID(String channelMapCRMID) {
        this.channelMapCRMID = channelMapCRMID;
    }

    public String getChannelMapName() {
        return channelMapName;
    }

    public void setChannelMapName(String channelMapName) {
        this.channelMapName = channelMapName;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}


