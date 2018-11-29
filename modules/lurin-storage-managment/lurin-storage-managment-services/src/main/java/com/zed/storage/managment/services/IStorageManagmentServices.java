package com.zed.storage.managment.services;

import com.zed.lurin.domain.dto.MessageTestConnectionDTO;
import com.zed.lurin.domain.jpa.DataSources;
import com.zed.lurin.domain.jpa.SourceType;

import java.util.List;

/**
 * <p>Abstract Class which defines the methods manage the storage managments are implemented</p>
 * @author Francisco Lujano
 */
public interface IStorageManagmentServices {

    /**
     * <p>method that creates a data source</p>
     * @param dataSource
     * @return id self-generated
     */
	long createDataSource(DataSources dataSource);
	
    /**
     * <p>method that updates a data source</p>
     * @param dataSource
     * @return Object {@link com.zed.lurin.domain.jpa.DataSources}
     */
	DataSources updateDataSource(DataSources dataSource);
	
    /**
     * <p>method that deactivate a data source</p>
     * @param code
     * @return  Object {@link com.zed.lurin.domain.jpa.DataSources}
     */
	DataSources deactivateDataSource(long code);
	
    /**
     * <p>method that obtain all data sources</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.DataSources}
     */
	List<DataSources> getAllDataSources();
	
    /**
     * <p>method that obtain a data source</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.DataSources}
     */
	DataSources getFindByCodeDataSource(long code);
	
    /**
     * <p>method that delete a data source</p>
     * @param code
     * @return  void
     */
	void deleteDataSource(long code);
	
	/**
	 * <p>method that obtain all types of Data Sources that the system handles</p>
	 * @return  Object {@link com.zed.lurin.domain.jpa.SourceType}
	 */
	List<SourceType> getSourceTypes();

	/**
	 * <p>method that obtain all types of Data Sources that the system handles</p>
	 * @return  Object {@link com.zed.lurin.domain.jpa.SourceType}
	 */
	SourceType getSourceTypes(String type);
	
	/**
	 * <p>method that test data sources in determinate Ip and port</p>
	 * @param dataSource data source to test
	 * @return  MessageTestConnectionDTO
	 */
	MessageTestConnectionDTO isDataSourceListening(DataSources dataSource);

	/**
	 * <p>method that validates the dependencies of the data source</p>
	 * @param code data_source_id
	 * @return  boolean
	 */
	boolean validataDataSourceDependencies(long code);

	/**
	 * <p>method that obtain only data sources</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  Object {@link com.zed.lurin.domain.jpa.DataSources}
	 */
	DataSources getJndiNameDataSources(String jndiName);
	
}
