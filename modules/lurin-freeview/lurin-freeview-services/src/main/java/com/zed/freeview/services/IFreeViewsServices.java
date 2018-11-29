package com.zed.freeview.services;

import com.zed.lurin.domain.jpa.FreeView;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * <p>Abstract Class which defines the methods manage the free views are implemented</p>
 * @author Francisco Lujano
 */
public interface IFreeViewsServices {

    /**
     * <p>method that creates a free view</p>
     * @param freeView
     * @param jndiNameDs
     * @return id self-generated
     */
    long createFreeView(FreeView freeView, String jndiNameDs);

    /**
     * <p>method that updates a free view</p>
     * @param freeView
     * @return Object {@link com.zed.lurin.domain.jpa.FreeView}
     */
    FreeView updateFreeView(FreeView freeView, String jndiNameDs);

    /**
     * <p>method that deactivate a free view</p>
     * @param freeViewId
     * @return  Object {@link com.zed.lurin.domain.jpa.FreeView}
     */
    FreeView deactivateFreeView(long freeViewId, String jndiNameDs);

    /**
     * <p>method that obtain all free views</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.FreeView}
     */
    List<FreeView> getAllFreeViews(String jndiNameDs);

    /**
     * <p>method that obtain a free views</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.FreeView}
     */
    FreeView getFindByIdFreeViews(long freeViewId, String jndiNameDs);

    /**
     * <p>method that delete a free view</p>
     * @param freeViewId
     * @return  Object {@link com.zed.lurin.domain.jpa.FreeView}
     */
    void deleteFreeView(long freeViewId, String jndiNameDs);
}
