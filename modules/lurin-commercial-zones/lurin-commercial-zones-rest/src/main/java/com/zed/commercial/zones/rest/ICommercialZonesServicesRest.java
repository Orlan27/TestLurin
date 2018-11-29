package com.zed.commercial.zones.rest;

import com.zed.lurin.domain.jpa.CommercialZone;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>Abstract Class which defines the methods manage the commercial zones are implemented</p>
 * @author Francisco Lujano
 */
public interface ICommercialZonesServicesRest {

    /**
     * <p>method that creates a commercial zone</p>
     * @param commercialZone
     * @return {@javax.ws.rs.core.Response}
     */
    Response createCommercialZone(CommercialZone commercialZone);

    /**
     * <p>method that updates a commercial zone</p>
     * @param commercialZone
     * @return {@javax.ws.rs.core.Response}
     */
    Response updateCommercialZone(CommercialZone commercialZone);

    /**
     * <p>method that deactivate a commercial zone</p>
     * @param zoneCode
     * @return  {@javax.ws.rs.core.Response}
     */
    Response deactivateCommercialZone(@PathParam("id") long zoneCode);

    /**
     * <p>method that obtain all commercial zones</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getAllCommercialZones();

    /**
     * <p>>method that obtain a commercial zones</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getFindByIdCommercialZones(@PathParam("id") long zoneCode);

    /**
     * <p>method that delete a commercial zone</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response deleteCommercialZone(@PathParam("id") long zoneCode);

}
