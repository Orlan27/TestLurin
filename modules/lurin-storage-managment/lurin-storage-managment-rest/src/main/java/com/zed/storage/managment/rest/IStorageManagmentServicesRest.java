package com.zed.storage.managment.rest;

import com.zed.lurin.domain.jpa.DataSources;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>Abstract Class which defines the methods manage the storage managments are implemented</p>
 * @author Francisco Lujano
 */
public interface IStorageManagmentServicesRest {

    /**
     * <p>method that creates a data source</p>
     * @param dataSource
     * @return {@javax.ws.rs.core.Response}
     */
    Response createDataSource(DataSources dataSource);

    /**
     * <p>method that updates a data source</p>
     * @param dataSource
     * @return {@javax.ws.rs.core.Response}
     */
    Response updateDataSource(DataSources dataSource);

    /**
     * <p>method that deactivate a data source</p>
     * @param code
     * @return  {@javax.ws.rs.core.Response}
     */
    Response deactivateDataSource(@PathParam("code") long code);

    /**
     * <p>method that obtain all data sources</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getAllDataSources();

    /**
     * <p>>method that obtain a data sources</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getFindByIdDataSource(@PathParam("code") long code);

    /**
     * <p>method that delete a data source</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response deleteDataSource(@PathParam("code") long code);

    /**
     * <p>method that obtain all types of Data Sources that the system handles</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getSourceTypes();

    /**
     * <p>method that obtain type of Data Sources that the system handles</p>
     * @param type
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getSourceTypes(String type);
    
    /**
     * <p>method that test data sources</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response isDataSourceListening(DataSources dataSource);

    /**
     * <p>method that obtain only data sources</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    Response getJndiNameDataSources(@PathParam("jndiName") String jndiName);

}
