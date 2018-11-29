package com.zed.storage.managment.services.impl;

import com.zed.lurin.domain.dto.MessageTestConnectionDTO;
import com.zed.lurin.domain.jpa.DataSources;
import com.zed.lurin.domain.jpa.SourceType;
import com.zed.lurin.domain.jpa.CarrierDataSources;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.security.hashing.services.IGenerateShaHash;
import com.zed.lurin.security.keys.Keys;
import com.zed.storage.managment.services.IStorageManagmentServices;
import com.zed.lurin.domain.services.ISecurityStatusServices;
import com.zed.wildfly.manager.services.IWildflyManagerServices;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/**
 * <p>Stateless where the methods that manage the storage managments are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.storage.managment.services.IStorageManagmentServices}
 */
@Stateless
public class StorageManagmentServicesImpl implements IStorageManagmentServices {

	/**
	 * LOGGER
	 */
	private static Logger LOGGER = Logger.getLogger(StorageManagmentServicesImpl.class.getName());

	private static String URL_CONNECTION_JDBC = "jdbc:oracle:thin:@%s:%s:%s";

	@EJB
	private IGenerateShaHash iGenerateShaHash;

    /**
     * Entity Manager reference.
     */
	@PersistenceContext(unitName="lurin-security-em")
    private EntityManager em;

    /**
     * Services the status methods
     */
    @EJB
    ISecurityStatusServices iSecurityStatusServices;

    @EJB
	IWildflyManagerServices iWildflyManagerServices;

    @EJB
	ICoreParameterServices parameterServices;

	@EJB
	IGenerateShaHash generateShaHash;
    
    /**
     * <p>method that creates a data source</p>
     * @param dataSource
     * @return id self-generated
     */
	@Override
	public long createDataSource(DataSources dataSource) {

		String host = parameterServices.getCoreParameterString(Keys.WILDFLY_JBOSS_CLI_HOST.toString());
		int port = parameterServices.getCoreParameterInteger(Keys.WILDFLY_JBOSS_CLI_PORT.toString());
		String jndiPrefix  = parameterServices.getCoreParameterString(Keys.WILDFLY_JBOSS_CLI_JNDI_PREFIX.toString());
		boolean isSecured  = Boolean.parseBoolean(parameterServices.getCoreParameterString(Keys.WILDFLY_JBOSS_CLI_IS_SECURED.toString()));


		if(isSecured){
			String username  = parameterServices.getCoreParameterString(Keys.WILDFLY_JBOSS_CLI_USERNAME.toString());
			String password  = parameterServices.getCoreParameterString(Keys.WILDFLY_JBOSS_CLI_PASSWORD.toString());
			iWildflyManagerServices.createDataSource(host, port, dataSource, jndiPrefix, username, password);
		}else{
			iWildflyManagerServices.createDataSource(host, port, dataSource, jndiPrefix);
		}


		dataSource.setJndiName(jndiPrefix+dataSource.getJndiName());
		dataSource.setStatus(this.iSecurityStatusServices.getCategoryActive());
		dataSource.setPassword(this.generateShaHash.generateSha512(dataSource.getPassword()));

		this.em.persist(dataSource);
		return dataSource.getCode();
	}
	
    /**
     * <p>method that updates a data source</p>
     * @param dataSource
     * @return Object {@link com.zed.lurin.domain.jpa.DataSources}
     */
	@Override
	public DataSources updateDataSource(DataSources dataSource) {
		dataSource.setPassword(this.generateShaHash.generateSha512(dataSource.getPassword()));
		this.em.merge(dataSource);
        return dataSource;
	}
	
    /**
     * <p>method that deactivate a data source</p>
     * @param code
     * @return  Object {@link com.zed.lurin.domain.jpa.DataSources}
     */
	@Override
	public DataSources deactivateDataSource(long code) {
		DataSources dataSource = this.em.find(DataSources.class, code);
		dataSource.setStatus(this.iSecurityStatusServices.getCategoryInactive());
		this.em.merge(dataSource);
		return dataSource;
	}
	
    /**
     * <p>method that obtain all data sources</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.DataSources}
     */
	@Override
	public List<DataSources> getAllDataSources() {
		TypedQuery<DataSources> query =
				this.em.createQuery("SELECT ds FROM DataSources ds", DataSources.class);

		List<DataSources> dataSources = query.getResultList();

		dataSources.stream().forEach(ds->{
			this.em.detach(ds);
			if(ds.getPassword()!=null) {
				ds.setPassword(this.iGenerateShaHash.generatePlainText(ds.getPassword()));
			}
		});

		return dataSources;
	}
	
    /**
     * <p>method that obtain a data source</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.DataSources}
     */
	@Override
	public DataSources getFindByCodeDataSource(long code) {
		DataSources dataSources = this.em.find(DataSources.class, code);
		this.em.detach(dataSources);
		if(dataSources.getPassword()!=null) {
        	dataSources.setPassword(this.iGenerateShaHash.generatePlainText(dataSources.getPassword()));

		}
		return dataSources;
	}
	
    /**
     * <p>method that delete a data source</p>
     * @param code
     * @return  void
     */
	@Override
	public void deleteDataSource(long code) {
		DataSources dataSource = this.em.find(DataSources.class, code);
		this.em.remove(dataSource);
	}
	
	/**
	 * <p>method that obtain all types of Data Sources that the system handles</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  Object {@link com.zed.lurin.domain.jpa.SourceType}
	 */
	@Override
	public List<SourceType> getSourceTypes() {
		TypedQuery<SourceType> query =
				this.em.createQuery("SELECT st FROM SourceType st ", SourceType.class);

		return query.getResultList();
	}

	/**
	 * <p>method that obtain all types of Data Sources that the system handles</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  Object {@link com.zed.lurin.domain.jpa.SourceType}
	 */
	@Override
	public SourceType getSourceTypes(String type) {
		TypedQuery<SourceType> query =
				this.em.createQuery("SELECT st FROM SourceType st where sourceType=:sourceType ", SourceType.class);
		query.setParameter("sourceType", type);

		SourceType  sourceType = null;
		List<SourceType> sourceTypes = query.getResultList();
		if (sourceTypes.stream().findFirst().isPresent()) {
			sourceType = sourceTypes.stream().findFirst().get();
		}

		return sourceType;
	}

	/**
	 * <p>method that test data sources in determinate Ip and port</p>
	 * <p>Note: java.net.Socket
	 * @param dataSource data source to test
	 * @return  MessageTestConnectionDTO
	 */
	@Override
	public MessageTestConnectionDTO isDataSourceListening(DataSources dataSource){
		MessageTestConnectionDTO messageTestConnectionDTO;
		if(dataSource.getSourceType().getSourceType().equals(SourceType.TYPE.JNDI.toString())
				|| dataSource.getSourceType().getSourceType().equals(SourceType.TYPE.JDBC.toString())){
			messageTestConnectionDTO = this.testConnectionJDBC(dataSource);
		}else{
			messageTestConnectionDTO =  this.testConnectionTCP(dataSource);
		}

		return messageTestConnectionDTO;
	}
	
	/**
	 * <p>method that validates the dependencies of the data source</p>
	 * @param code data_source_id
	 * @return  boolean
	 */
	@Override
	public boolean validataDataSourceDependencies(long code) {
		TypedQuery<CarrierDataSources> query =
				this.em.createQuery("SELECT cds FROM CarrierDataSources cds where cds.dataSources.code = :datasource", CarrierDataSources.class);
		query.setParameter("datasource", code);
		
		return query.getResultList().size()>0;
	}


	/**
	 * <p>method that obtain only data sources</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  Object {@link com.zed.lurin.domain.jpa.DataSources}
	 */
	@Override
	public DataSources getJndiNameDataSources(String jndiName) {
		TypedQuery<DataSources> query =
				this.em.createQuery("SELECT ds FROM DataSources ds WHERE jndiName=:jndiName", DataSources.class);
		query.setParameter("jndiName", jndiName);
		return query.getSingleResult();
	}



	private MessageTestConnectionDTO testConnectionJDBC(DataSources dataSources){

		MessageTestConnectionDTO messageTestConnectionDTO = new MessageTestConnectionDTO();

		LOGGER.info("-------- Oracle JDBC Connection Testing ------");

		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			LOGGER.error(String.format("The JDBC driver is not found [%s]", e.getMessage()) );
			factoryMessageTestJDBC(messageTestConnectionDTO, false,
					MessageTestConnectionDTO.MESSAGES_KEYS.JDBC_DRIVER_IS_NOT_FOUND);
			return messageTestConnectionDTO;
		}

		LOGGER.info("Oracle JDBC Driver Registered!");

		String connectUrl = String.format(URL_CONNECTION_JDBC, dataSources.getIp(), dataSources.getPort(), dataSources.getSid());
		try (Connection connection = DriverManager.getConnection(connectUrl, dataSources.getUserName(), dataSources.getPassword())) {
			PreparedStatement preparedStatement = connection.prepareStatement("select 1 from dual");
			preparedStatement.executeQuery();

			factoryMessageTestJDBC(messageTestConnectionDTO, true,
					MessageTestConnectionDTO.MESSAGES_KEYS.SUCCESSFUL_CONNECTION);

		} catch (SQLException e) {
			LOGGER.error(String.format("The connection failed [%s] SQLState [%s] VendorError [%s]", e.getMessage(), e.getSQLState(), e.getErrorCode()));

			MessageTestConnectionDTO.MESSAGES_KEYS messages = MessageTestConnectionDTO.MESSAGES_KEYS.CONNECTION_FAILED;
			if(e.getErrorCode()==1017){
				messages = MessageTestConnectionDTO.MESSAGES_KEYS.INVALID_USERNAME_PASSWORD;
			}
			if(e.getErrorCode()==12505){
				messages = MessageTestConnectionDTO.MESSAGES_KEYS.INVALID_SID;
			}

			factoryMessageTestJDBC(messageTestConnectionDTO, false,messages);
		}

		return messageTestConnectionDTO;
	}



	private MessageTestConnectionDTO testConnectionTCP(DataSources dataSources){

		MessageTestConnectionDTO messageTestConnectionDTO = new MessageTestConnectionDTO();

		LOGGER.info("-------- TCP Connection Testing ------");
		InetAddress inet = null;
		try {
			inet = InetAddress.getByName(dataSources.getIp());

		} catch (UnknownHostException e) {
			messageTestConnectionDTO.setValid(false);
			messageTestConnectionDTO.setMessage(MessageTestConnectionDTO.MESSAGES_KEYS.UNKNOWN_HOST.toString());
			messageTestConnectionDTO.setParams(new ArrayList<>());
			return messageTestConnectionDTO;
		}

		List<String> portsValid =  new ArrayList<>();

		if ((dataSources.getPort() != null && dataSources.getPort() != 0)) {
			portsValid.add(String.valueOf(dataSources.getPort()));
		}
		if ((dataSources.getPort2() != null && dataSources.getPort2() != 0)) {
			portsValid.add(String.valueOf(dataSources.getPort2()));
		}
		if ((dataSources.getPort3() != null && dataSources.getPort3() != 0)) {
			portsValid.add(String.valueOf(dataSources.getPort3()));
		}

		InetAddress finalInet = inet;
		HashSet<String> params = new HashSet<>();
		portsValid.stream().forEach(p -> {

			Socket socket = null;
			try {
				SocketAddress socketAddress = new InetSocketAddress(finalInet, Integer.parseInt(p));
				socket = new Socket();
				socket.connect(socketAddress, 5000);
			} catch (Exception e) {
				LOGGER.error(String.format("Network error for the port [%s]", p));
				params.add(p);
			}finally {
				if(socket!=null){
					try {socket.close();} catch (IOException e) {}
				}
			}
		});

		if(params.size()>0){
			messageTestConnectionDTO.setValid(false);
			MessageTestConnectionDTO.MESSAGES_KEYS messages =
					(params.size()==1)?
						MessageTestConnectionDTO.MESSAGES_KEYS.CONNECTION_FAILED_ONLY_ONE:
							MessageTestConnectionDTO.MESSAGES_KEYS.CONNECTION_FAILED_MORE_THAN_ONE;
			messageTestConnectionDTO.setMessage(messages.toString());
			messageTestConnectionDTO.setParams(params.stream().collect(Collectors.toList()));
		}else{
			messageTestConnectionDTO.setValid(true);
			messageTestConnectionDTO.setMessage(MessageTestConnectionDTO.MESSAGES_KEYS.SUCCESSFUL_CONNECTION.toString());
			messageTestConnectionDTO.setParams(new ArrayList<>());
		}




		return messageTestConnectionDTO;
	}

	/**
	 * Method Factory Message Test Connection JDBC
	 * @param messageTestConnectionDTO
	 * @param isValid
	 * @param messageKey
	 */
	private void factoryMessageTestJDBC(MessageTestConnectionDTO messageTestConnectionDTO, boolean isValid,
										MessageTestConnectionDTO.MESSAGES_KEYS messageKey) {
		messageTestConnectionDTO.setValid(isValid);
		messageTestConnectionDTO.setMessage(messageKey.toString());
		messageTestConnectionDTO.setParams(new ArrayList<>());
	}


//	/**
//	 * Method Functional Programming for select default languajes
//	 * @return
//	 */
//	private Function<DataSourcesView, DataSourcesView> passwordGetTransform() {
//		return ds-> {
//
//			if(ds.getPassword()!=null){
//				ds.setPassword(this.iGenerateShaHash.generatePlainText(ds.getPassword()));
//			}
//			return ds;
//		};
//	}

}