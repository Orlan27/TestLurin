package com.zed.commercial.zones.services;

import com.zed.lurin.domain.jpa.CommercialZone;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * <p>Abstract Class which defines the methods manage the commercial zones are implemented</p>
 * @author Francisco Lujano
 */
public interface ICommercialZonesServices {

    /**
     * <p>method that creates a commercial zone</p>
     * @param commercialZone
     * @param jndiNameDs
     * @return id self-generated
     */
    long createCommercialZone(CommercialZone commercialZone, String jndiNameDs);

    /**
     * <p>method that updates a commercial zone</p>
     * @param commercialZone
     * @return Object {@link com.zed.lurin.domain.jpa.CommercialZone }
     */
    CommercialZone updateCommercialZone(CommercialZone commercialZone, String jndiNameDs);

    /**
     * <p>method that deactivate a commercial zone</p>
     * @param zoneCode
     * @param jndiNameDs
     * @return  Object {@link com.zed.lurin.domain.jpa.CommercialZone }
     */
    CommercialZone deactivateCommercialZone(long zoneCode, String jndiNameDs);

    /**
     * <p>method that obtain all commercial zones</p>
     * @param jndiNameDs
     * @return  Object {@link com.zed.lurin.domain.jpa.CommercialZone }
     */
    List<CommercialZone> getAllCommercialZones(String jndiNameDs);

    /**
     * <p>method that obtain a commercial zones</p>
     * @param jndiNameDs
     * @return  Object {@link com.zed.lurin.domain.jpa.CommercialZone }
     */
    CommercialZone getFindByIdCommercialZones(long zoneCode, String jndiNameDs);

    /**
     * <p>method that delete a commercial zone</p>
     * @param zoneCode
     * @param jndiNameDs
     * @return  Object {@link com.zed.lurin.domain.jpa.CommercialZone }
     */
    void deleteCommercialZone(long zoneCode, String jndiNameDs);
}
