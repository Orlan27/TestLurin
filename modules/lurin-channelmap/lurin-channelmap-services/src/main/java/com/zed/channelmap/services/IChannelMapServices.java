package com.zed.channelmap.services;

import com.zed.lurin.domain.jpa.ChannelMap;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * <p>Abstract Class which defines the methods manage the channel map are implemented</p>
 * @author Francisco Lujano
 */
public interface IChannelMapServices {


    /**
     * <p>method that obtain all Channel Map</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link ChannelMap }
     */
    List<ChannelMap> getAllChannelMap();

    /**
     * method that returns by primary key
     * @param channelMapId
     * @return
     */
    ChannelMap getChannelMapById(long channelMapId);

    /**
     * method that returns by external id
     * @param externalId
     * @return
     */
    List<ChannelMap> getChannelMapByExternalId(String externalId);

    /**
     * method that returns by external id and primary key
     * @param externalId
     * @param channelMapId
     * @return
     */
    List<ChannelMap> getChannelMapByExternalIdAndId(String externalId, long channelMapId);
}
