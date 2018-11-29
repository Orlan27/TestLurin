package com.zed.channel.packages.services;

import com.zed.lurin.domain.jpa.ChannelPackages;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public interface IChannelPackageServices {
    /**
     * <p>method that creates a channel package</p>
     * @param channelPackages
     * @return id self-generated
     */
    long createChannelPackages(ChannelPackages channelPackages, String jndiNameDs);

    /**
     * <p>method that updates a channel package</p>
     * @param channelPackages
     * @return Object {@link ChannelPackages }
     */
    ChannelPackages updateChannelPackages(ChannelPackages channelPackages, String jndiNameDs);

    /**
     * <p>method that deactivate a channel package</p>
     * @param channelPackagesId
     * @return  Object {@link ChannelPackages }
     */
    ChannelPackages deactivateChannelPackages(long channelPackagesId, String jndiNameDs);

    /**
     * <p>method that obtain all channel packages</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return List {@link ChannelPackages }
     */
    List<ChannelPackages> getAllChannelPackages(String jndiNameDs);

    /**
     * <p>method that obtain a channel packages</p>
     *
     * @param channelPackagesId
     * @return Object {@link ChannelPackages }
     */
    ChannelPackages getFindByIdChannelPackages(long channelPackagesId, String jndiNameDs);

    /**
     * <p>method that delete a channel package</p>
     *
     * @param channelPackagesId
     * @return void
     */
    void deleteChannelPackages(long channelPackagesId, String jndiNameDs);

}
