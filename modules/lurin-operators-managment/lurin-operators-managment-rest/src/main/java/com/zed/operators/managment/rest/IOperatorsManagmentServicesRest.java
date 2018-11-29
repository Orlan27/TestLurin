package com.zed.operators.managment.rest;

import com.zed.lurin.domain.jpa.Carriers;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>Abstract Class which defines the methods manage the operators managments are implemented</p>
 * @author Francisco Lujano
 */
public interface IOperatorsManagmentServicesRest {

	 /**
     * <p>method that creates a carrier</p>
     * @param carrier
     * @return {@javax.ws.rs.core.Response}
     */
	Response createCarrier(Carriers carrier);
	
    /**
     * <p>method that updates a carrier</p>
     * @param carrier
     * @return {@javax.ws.rs.core.Response}
     */
	Response updateCarrier(Carriers carrier);
	
    /**
     * <p>method that delete a carrier</p>
     * @return  {@javax.ws.rs.core.Response}
     */
	Response deleteCarrier(long code);
	
    /**
     * <p>method that obtain all carriers</p>
     * @return  {@javax.ws.rs.core.Response}
     */
	Response getAllCarriers(String lang, String token);
	
    /**
     * <p>>method that obtain a carrier</p>
     * @return  {@javax.ws.rs.core.Response}
     */
	Response getFindByIdCarrier(long code, String lang);
	
	/**
     * <p>method that obtain all data sources available for carrier</p>
	 * @param type datasource
     * @return  {@javax.ws.rs.core.Response}
     */
	Response getDataSourcesAvailable(String type);

	/**
	 * <p>method that obtain all data sources available for carrier</p>
	 * @return  {@javax.ws.rs.core.Response}
	 */
	Response getDataSourcesAvailableMerge();
	
	/**
     * <p>method that obtain all countries in database</p>
     * @return  {@javax.ws.rs.core.Response}
     */
	Response getCountries();
	
	/**
     * <p>method that obtain all tables for carriers in database</p>
     * @return  {@javax.ws.rs.core.Response}
     */
	Response getTables();

	/**
     * <p>method that obtain all tables for carriers in database</p>
     * @return  {@javax.ws.rs.core.Response}
     */
	Response getCompanies();

	/**
     * <p>method that obtain all status for entities in database</p>
     * @return  {@javax.ws.rs.core.Response}
     */
	Response getSecurityCategoryStatus();

 
}
