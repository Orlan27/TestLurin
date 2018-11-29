package com.zed.operators.managment.services;

import com.zed.lurin.domain.jpa.*;
import com.zed.lurin.domain.jpa.view.DataSourcesAvailableView;
import com.zed.operators.managment.services.exceptions.ExceptionCarrierCause;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * <p>Abstract Class which defines the methods manage the operators managments are implemented</p>
 * @author Francisco Lujano
 */
public interface IOperatorsManagmentServices {

    /**
     * <p>method that delete a carrier datasource</p>
     * @param carrier_id carrier asociated
     * @param datasource_id datasource asociated
     * @return  void
     */
	void deleteCarrierDataSources(long carrier_id, long datasource_id);

	/**
	 * <p>method that obtain a CarrierDataSources</p>
	 * @param carrierId carrier asociated
	 * @param type type Datasources
	 * @return Object {@link com.zed.lurin.domain.jpa.CarrierDataSources}
	 */
	CarrierDataSources getCarrierDataSourcesForType(long carrierId, String type);
	
	/**
	 * <p>method that obtain a CarrierDataSources</p>
     * @param carrier_id carrier asociated
     * @param datasource_id datasource asociated
	 * @return Object {@link com.zed.lurin.domain.jpa.CarrierDataSources}
	 */
	CarrierDataSources getCarrierDataSources(long carrier_id, long datasource_id);
	
    /**
     * <p>method that creates a carrier. Create admin user and carrier datasource</p>
     * @param carrier
     * @return id self-generated
     */
	long createCarrier(Carriers carrier) throws ExceptionCarrierCause;

	/**
     * <p>method that updates a carrier</p>
     * @param carrier
     * @return Object {@link com.zed.lurin.domain.jpa.Carriers}
     */
	Carriers updateCarrier(Carriers carrier);

	/**
	 * <p>method that obtain all data sources available for carrier</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  Object {@link DataSourcesAvailableView}
	 */
	List<DataSourcesAvailableView> getDataSourcesAvailable();

    /**
     * <p>method that obtain all data sources available for carrier</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @param type
     * @return  Object {@link com.zed.lurin.domain.jpa.DataSources}
     */
	List<DataSources> getDataSourcesAvailable(String type);
	
    /**
     * <p>method that obtain all countries in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.Countries}
     */
	List<Countries> getCountries();
	
    /**
     * <p>method that obtain all tables for carriers in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.Tables}
     */
	List<Tables> getTables();
	
    /**
     * <p>method that obtain all companies for carriers in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.Companies}
     */
	List<Companies> getCompanies();

	/**
     * <p>method that obtain all status foe entities in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.SecurityCategoryStatus}
     */
	List<SecurityCategoryStatus> getSecurityCategoryStatus();

	/**
	 * <p>method that obtain all carriers in database</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  List {@link com.zed.lurin.domain.jpa.Carriers}
	 */
	List<Carriers> getAllCarriers();

	/**
	 * <p>method that obtain all carriers in database</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  List {@link com.zed.lurin.domain.jpa.Carriers}
	 */
	long countAllCarriers();
}
