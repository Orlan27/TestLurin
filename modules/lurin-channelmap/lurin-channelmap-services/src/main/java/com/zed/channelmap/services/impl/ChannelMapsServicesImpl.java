package com.zed.channelmap.services.impl;


import com.zed.channelmap.services.IChannelMapServices;
import com.zed.lurin.domain.jpa.ChannelMap;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * <p>Stateless where the methods that manage the Channel Map are implemented</p>
 * @author Francisco Lujano
 * {@link IChannelMapServices}
 */
@Stateless
public class ChannelMapsServicesImpl implements IChannelMapServices {

    /**
     * Entity Manager reference.
     */
    @PersistenceContext(name = "lurin-admin-em")
    private EntityManager em;


    /**
     * <p>method that obtain all Channel Map</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link ChannelMap }
     */
    @Override
    public List<ChannelMap> getAllChannelMap() {
        TypedQuery<ChannelMap> query =
                this.em.createQuery("SELECT cm FROM ChannelMap cm", ChannelMap.class);
        return query.getResultList();

    }

    /**
     * method that returns by primary key
     * @param channelMapId
     * @return
     */
    @Override
    public ChannelMap getChannelMapById(long channelMapId) {
        return this.em.find(ChannelMap.class, channelMapId);
    }

    /**
     * method that returns by external id
     * @param externalId
     * @return
     */
    @Override
    public List<ChannelMap> getChannelMapByExternalId(String externalId) {
        TypedQuery<ChannelMap> query =
                this.em.createQuery("SELECT cm FROM ChannelMap cm " +
                        "WHERE cm.externalId=:externalId", ChannelMap.class);
        query.setParameter("externalId",externalId);
        return query.getResultList();

    }

    /**
     * method that returns by external id and primary key
     * @param externalId
     * @param channelMapId
     * @return
     */
    @Override
    public List<ChannelMap> getChannelMapByExternalIdAndId(String externalId, long channelMapId) {
        TypedQuery<ChannelMap> query =
                this.em.createQuery("SELECT cm FROM ChannelMap cm " +
                        "WHERE cm.externalId=:externalId AND cm.code=:channelMapId", ChannelMap.class);
        query.setParameter("externalId",externalId);
        query.setParameter("channelMapId",channelMapId);
        return query.getResultList();

    }
}
