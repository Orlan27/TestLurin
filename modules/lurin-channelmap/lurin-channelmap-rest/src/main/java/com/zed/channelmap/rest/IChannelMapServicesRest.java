package com.zed.channelmap.rest;

import com.zed.lurin.domain.jpa.ChannelMap;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * <p>Abstract Class which defines the methods manage the channel map are implemented</p>
 * @author Francisco Lujano
 */
public interface IChannelMapServicesRest {

    /**
     * <p>method that obtain all channel map</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getAllChannelMap();

    /**
     * method that returns by primary key
     * @param channelMapId
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getChannelMapById(long channelMapId);

    /**
     * method that returns by external id
     * @param externalId
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getChannelMapByExternalId(String externalId);

    /**
     * method that returns by external id and primary key
     * @param externalId
     * @param channelMapId
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getChannelMapByExternalIdAndId(String externalId, long channelMapId);

}
