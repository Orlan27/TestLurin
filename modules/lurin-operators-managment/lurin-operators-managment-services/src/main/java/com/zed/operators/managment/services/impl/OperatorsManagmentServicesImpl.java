package com.zed.operators.managment.services.impl;


import com.zed.lurin.domain.jpa.*;
import com.zed.lurin.domain.jpa.view.DataSourcesAvailableView;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.security.admin.profiles.services.IAdminProfiles;
import com.zed.lurin.security.users.services.IUsersServices;
import com.zed.operators.managment.services.IOperatorsManagmentServices;
import com.zed.lurin.mail.services.IMailServices;
import com.zed.lurin.domain.services.ISecurityStatusServices;
import com.zed.lurin.security.password.services.IPasswordGeneratorService;
import com.zed.lurin.security.hashing.services.IGenerateShaHash;
import com.zed.lurin.security.keys.Keys;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

import com.zed.operators.managment.services.exceptions.CodeExceptionCarrier;
import com.zed.operators.managment.services.exceptions.ExceptionCarrierCause;
import org.apache.log4j.Logger;
/**
 * <p>Stateless where the methods that manage the operators managments are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.operators.managment.services.IOperatorsManagmentServices}
 */
@Stateless
public class OperatorsManagmentServicesImpl implements IOperatorsManagmentServices {

	 /*
     * Logger util
     */
    private static Logger LOGGER = Logger.getLogger(OperatorsManagmentServicesImpl.class.getName());

    
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
    
    /**
     * Password Generator Service
     */
	@EJB
	IPasswordGeneratorService iPasswordGeneratorService;
	
    /**
     * Generating hash password
     */
    @EJB
    IGenerateShaHash iGenerateShaHash;
 
    /**
     * Core Parameter Services
     */
    @EJB
    ICoreParameterServices iParameterServices;
    
    @EJB
    IMailServices mailServices;

    @EJB
	IAdminProfiles IAdminProfiles;

    @EJB
	IUsersServices iUsersServices;

	
    /**
     * <p>method that delete a carrier datasource</p>
     * @param carrier_id carrier asociated
     * @param datasource_id datasource asociated
     * @return  void
     */
	@Override
	public void deleteCarrierDataSources(long carrier_id, long datasource_id) {
		CarrierDataSources carrierDS = getCarrierDataSources(carrier_id, datasource_id);
		this.em.remove(carrierDS);
	}


	/**
	 * <p>method that obtain a CarrierDataSources</p>
	 * @param carrierId carrier asociated
	 * @param type type Datasources
	 * @return Object {@link com.zed.lurin.domain.jpa.CarrierDataSources}
	 */
	@Override
	public CarrierDataSources getCarrierDataSourcesForType(long carrierId, String type) {
		TypedQuery<CarrierDataSources> query =
				this.em.createQuery("SELECT cds FROM CarrierDataSources cds " +
								"where cds.carriers.code= :carrierId " +
								"and cds.dataSources.sourceType.sourceType= :type ",
						CarrierDataSources.class);
		query.setParameter("carrierId", carrierId);
		query.setParameter("type", type);

		return query.getSingleResult();
	}

	/**
	 * <p>method that obtain a CarrierDataSources</p>
     * @param carrier_id carrier asociated
     * @param datasource_id datasource asociated
	 * @return Object {@link com.zed.lurin.domain.jpa.CarrierDataSources}
	 */
	@Override
	public CarrierDataSources getCarrierDataSources(long carrier_id, long datasource_id) {
		TypedQuery<CarrierDataSources> query =
				this.em.createQuery("SELECT cds FROM CarrierDataSources cds where cds.carriers.code= :carrier_id and cds.dataSources.code= :datasource_id", 
						CarrierDataSources.class);
		query.setParameter("carrier_id", carrier_id);
		query.setParameter("datasource_id", datasource_id);
		
		return query.getSingleResult();
	}
	
    /**
     * <p>method that creates a carrier, user admin for carrier, carrier datasource and send mail registration</p>
     * @param carrier
     * @return id self-generated
     */
	@Override
	public long createCarrier(Carriers carrier) throws ExceptionCarrierCause {



		if(this.iUsersServices.isUserExitsForUserName(carrier.getAdminUser().getLogin())){
			throw new ExceptionCarrierCause(CodeExceptionCarrier.USER_EXIST, carrier.getAdminUser().getLogin());
		}

		List<Carriers>  carriersList = this.getAllCarriers();

		int percentageShare = carriersList.stream().mapToInt(ca->Long.valueOf(ca.getPercentageShare()).intValue()).sum();

		percentageShare=percentageShare+Long.valueOf(carrier.getPercentageShare()).intValue();

		if(percentageShare>100){
			throw new ExceptionCarrierCause(CodeExceptionCarrier.PERCENTAGE_EXCEEDED, carrier.getAdminUser().getLogin());
		}

		carrier.setStatus(this.iSecurityStatusServices.getCategoryActive());

		/**
		 * Get profile
		 */

		Profile profile = this.IAdminProfiles.getProfile("PROFILE_ADMIN_LOCAL");

		Set<CategoryTechnologies> categoryTechnologies = new HashSet<>();
		carrier.getCategoryTechnologies().stream().forEach(ct->{
			CategoryTechnologies categoryTechnologie = this.em.find(CategoryTechnologies.class, ct.getCode());
			categoryTechnologies.add(categoryTechnologie);
		});
		carrier.setCategoryTechnologies(categoryTechnologies);

		Set<DataSources> dataSources = new HashSet<DataSources>();
		carrier.getDataSources().stream().forEach(ds->{
			DataSources dataSource = this.em.find(DataSources.class, ds.getCode());
			dataSources.add(dataSource);
		});
		carrier.setDataSources(dataSources);


		Users adminUser = carrier.getAdminUser();
		adminUser.setProfile(profile);
		adminUser.setCompanies(carrier.getCompanies());

		/**
		 * To assign roles and carriers
		 */
		Set<UserCarriers> userCarriersSet = new HashSet<>();
		Set<UserCarriersRoles> userCarriersRolesSet = new HashSet<>();

		/**
		 * Set userCarriers and roles
		 */
		UserCarriers userCarriers =  new UserCarriers();
		userCarriers.setUsers(adminUser);
		userCarriers.setCarriers(carrier);
		userCarriers.setUserCarriersRoles(userCarriersRolesSet);

		/**
		 * Set user carrier
		 */
		userCarriersSet.add(userCarriers);

		/**
		 * Roles Object
		 */

		profile.getProfileRoles().stream().forEach(pr ->{

			UserCarriersRoles userCarriersRoles =  new UserCarriersRoles();
			userCarriersRoles.setUserCarriers(userCarriers);
			userCarriersRoles.setRole(pr.getRoles());

			userCarriersRolesSet.add(userCarriersRoles);
		});


		adminUser.setUserCarriers(userCarriersSet);

		/**
		 * Update password SHA-512 and persist users
		 */
		adminUser.setPassword(
				this.iGenerateShaHash.generateSha512(
						iPasswordGeneratorService.generatePassword(8,
								iPasswordGeneratorService.ALPHA_CAPS + iPasswordGeneratorService.ALPHA)));
		this.em.persist(carrier);
		this.em.persist(adminUser);
		this.sendRegistrationMail(adminUser);
		return carrier.getCode();
	}




	/**
     * <p>method that updates a carrier</p>
     * @param carrier
     * @return Object {@link com.zed.lurin.domain.jpa.Carriers}
     */
	@Override
	public Carriers updateCarrier(Carriers carrier) {

		/**
		 * Get profile
		 */

		Profile profile = this.IAdminProfiles.getProfile("PROFILE_ADMIN_LOCAL");

		Set<CategoryTechnologies> categoryTechnologies = new HashSet<>();
		carrier.getCategoryTechnologies().stream().forEach(ct->{
			CategoryTechnologies categoryTechnologie = this.em.find(CategoryTechnologies.class, ct.getCode());
			categoryTechnologies.add(categoryTechnologie);
		});
		carrier.setCategoryTechnologies(categoryTechnologies);

		Set<DataSources> dataSources = new HashSet<DataSources>();
		carrier.getDataSources().stream().forEach(ds->{
			DataSources dataSource = this.em.find(DataSources.class, ds.getCode());
			dataSources.add(dataSource);
		});
		carrier.setDataSources(dataSources);

		this.em.merge(carrier);

		carrier.getAdminUser().setProfile(profile);

		this.em.merge(carrier.getAdminUser());

        return carrier;
	}


	/**
	 * <p>method that obtain all data sources available for carrier</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  Object {@link DataSourcesAvailableView}
	 */
	@Override
	public List<DataSourcesAvailableView> getDataSourcesAvailable() {

		TypedQuery<DataSourcesAvailableView> query  =
				this.em.createQuery("SELECT ds FROM DataSourcesAvailableView ds", DataSourcesAvailableView.class);

		return query.getResultList();
	}
	
    /**
     * <p>method that obtain all data sources available for carrier</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.DataSources}
     */
	@Override
	public List<DataSources> getDataSourcesAvailable(String type) {
		TypedQuery<DataSources> query = null;
		if(type.equals(SourceType.TYPE.JDBC.toString()) || type.equals(SourceType.TYPE.JNDI.toString())){
			 query = this.em.createQuery("SELECT ds FROM DataSources ds " +
							"where ds.sourceType.sourceType=:sourceType " +
					 		"AND ds.status.code=:codeStatus " +
					 		"AND ds.code not in (select cds.dataSources.code from CarrierDataSources cds)", DataSources.class);
		}else{
			query = this.em.createQuery("SELECT ds FROM DataSources ds " +
					"where ds.sourceType.sourceType=:sourceType " +
					"AND ds.status.code=:codeStatus ", DataSources.class);
		}
		query.setParameter("sourceType", type);
		query.setParameter("codeStatus", this.iSecurityStatusServices.getCategoryActive().getCode());
		return query.getResultList();
	}

    /**
     * <p>method that obtain all countries in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.Countries}
     */
	@Override
	public List<Countries> getCountries() {
		TypedQuery<Countries> query =
				this.em.createQuery("SELECT c FROM Countries c ", Countries.class);
		return query.getResultList();
	}
	
    /**
     * <p>method that obtain all tables for carriers in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.Tables}
     */
	@Override
	public List<Tables> getTables() {
		TypedQuery<Tables> query =
				this.em.createQuery("SELECT t FROM Tables t ", Tables.class);
		return query.getResultList();
	}
	
    /**
     * <p>method that obtain all companies for carriers in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.Companies}
     */
	@Override
	public List<Companies> getCompanies() {
		TypedQuery<Companies> query =
				this.em.createQuery("SELECT c FROM Companies c ", Companies.class);
		return query.getResultList();
	}
	
    /**
     * <p>method that obtain all status for entities in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.SecurityCategoryStatus}
     */
	@Override
	public List<SecurityCategoryStatus> getSecurityCategoryStatus() {
		TypedQuery<SecurityCategoryStatus> query =
				this.em.createQuery("SELECT scs FROM SecurityCategoryStatus scs ", SecurityCategoryStatus.class);
		return query.getResultList();
	}
	
	/**
	 * <p>method that obtain a CarrierDataSources</p>
     * @param carrier_id carrier asociated
     * @return Object {@link com.zed.lurin.domain.jpa.CarrierDataSources}
	 */
	public CarrierDataSources getCarrierDataSourcesByCarrierId(long carrier_id) {
		TypedQuery<CarrierDataSources> query =
				this.em.createQuery("SELECT cds FROM CarrierDataSources cds where cds.carriers.code= :carrier_id", 
						CarrierDataSources.class);
		query.setParameter("carrier_id", carrier_id);
		
		return query.getSingleResult();
	}

	/**
	 * <p>method that obtain all carriers in database</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  List {@link com.zed.lurin.domain.jpa.Carriers}
	 */
	@Override
	public List<Carriers> getAllCarriers() {
		TypedQuery<Carriers> query =
				this.em.createQuery("SELECT c FROM Carriers c", Carriers.class);

		List<Carriers> carriersList = query.getResultList();
		carriersList.stream().forEach(ca ->{
			this.em.detach(ca);
		});

		return query.getResultList();
	}

	/**
	 * <p>method that obtain all carriers in database</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  List {@link com.zed.lurin.domain.jpa.Carriers}
	 */
	@Override
	public long countAllCarriers() {
		TypedQuery<Long> query = this.em.createQuery ("SELECT COUNT(c) FROM Carriers c WHERE c.status=:status", Long.class);

		query.setParameter("status", iSecurityStatusServices.getCategoryActive());

		return query.getSingleResult();
	}


   private void sendRegistrationMail(Users user) {
	   try {
		   String body = this.iParameterServices.getCoreParameterString(
	    		   Keys.REGISTRATION_MAIL_BODY.toString());
	       
	       String personalName = user.getFirstName() + " " + user.getLastName();
	       body = String.format(body, personalName, user.getLogin(), this.iGenerateShaHash.generatePlainText(user.getPassword()));
	       mailServices.send(user.getEmail(), "User account created", body);
	   } catch(Exception ex) {
		   ex.printStackTrace();
	   }

   }
}
