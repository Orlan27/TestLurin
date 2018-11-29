package com.zed.freeview.rest;

import com.zed.lurin.domain.jpa.FreeView;
import com.zed.lurin.security.filters.services.DynamicJPA;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>Abstract Class which defines the methods manage the free views are implemented</p>
 * @author Francisco Lujano
 */
public interface IFreeViewsServicesRest {

    /**
     * <p>method that creates a free view</p>
     * @param freeView
     * @return {@javax.ws.rs.core.Response}
     */
    Response createFreeView(FreeView freeView);

    /**
     * <p>method that updates a free view</p>
     * @param freeView
     * @return {@javax.ws.rs.core.Response}
     */
    Response updateFreeView(FreeView freeView);

    /**
     * <p>method that deactivate a free view</p>
     * @param freeViewId
     * @return  {@javax.ws.rs.core.Response}
     */
    Response deactivateFreeView(long freeViewId, long carrierId);

    /**
     * <p>method that obtain all free views</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getAllFreeViews(long carrierId);

    /**
     * <p>>method that obtain a free views</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getFindByIdFreeViews(long carrierId, long freeViewId);

    /**
     * <p>method that delete a free view</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response deleteFreeView(long carrierId, long freeViewId);




}
