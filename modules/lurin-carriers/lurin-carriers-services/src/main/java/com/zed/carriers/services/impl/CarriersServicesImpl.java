package com.zed.carriers.services.impl;

import com.zed.carriers.services.ICarriersServices;
import com.zed.lurin.domain.jpa.*;
import com.zed.lurin.domain.jpa.view.OnlyCarriersByUserNameView;
import com.zed.lurin.domain.jpa.view.RolesByUserNameView;
import com.zed.lurin.domain.services.ISecurityStatusServices;
import com.zed.lurin.domain.jpa.view.CarriersByUserNameView;
import com.zed.lurin.domain.jpa.view.ThemesDetailsView;
import com.zed.lurin.security.keys.Keys;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
/**
 * Class that contains the operator management methods
 * @author Francisco Lujano
 */
@Stateless
public class CarriersServicesImpl implements ICarriersServices {

	/**
	 * LOGGER
	 */
	private static Logger LOGGER = Logger.getLogger(CarriersServicesImpl.class.getName());
    /**
     * Entity Manager reference.
     */
    @PersistenceContext(unitName = "lurin-security-em")
    private EntityManager em;


    /**
     * Services the status Methods
     */
    @EJB
    ISecurityStatusServices iSecurityStatusServices;

	/**
	 * Services Security Status
	 */
	@EJB
	ISecurityStatusServices securityStatusServices;


	/**
	 * method that returns operators per user
     * @param username
     * @return List by Operators
     */
    @Override
    public List<CarriersByUserNameView> getCarriersByUserName(String username) {
        TypedQuery<CarriersByUserNameView> query =
                this.em.createQuery("SELECT vcu " +
                                "FROM CarriersByUserNameView vcu " +
                                "WHERE vcu.login=:login ",
                        CarriersByUserNameView.class);

        query.setParameter("login", username);

        return query.getResultList();
    }
    
    /**
     * <p>method that creates a carrier</p>
     * @param carrier
     * @return id self-generated
     */
	@Override
	public long createCarrier(Carriers carrier) {
		carrier.setStatus(this.iSecurityStatusServices.getCategoryActive());
		this.em.persist(carrier);
		return carrier.getCode();
	}
	
    /**
     * <p>method that updates a carrier</p>
     * @param carrier
     * @return Object {@link com.zed.lurin.domain.jpa.Carriers}
     */
	@Override
	public Carriers updateCarrier(Carriers carrier) {
		this.em.merge(carrier);
        return carrier;
	}
	
    /**
     * <p>method that delete a carrier</p>
     * @param code
     * @return  void
     */
	@Override
	public void deleteCarrier(long code) {
		Carriers carrier = this.em.find(Carriers.class, code);
		this.em.remove(carrier);
	}	
	
    /**
     * <p>method that obtain all carriers in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  List {@link com.zed.lurin.domain.jpa.Carriers}
     */
	@Override
	public List<Carriers> getAllCarriers(String lang, String token) {
		TypedQuery<Carriers> query =
				this.em.createQuery("SELECT c FROM Carriers c", Carriers.class);
		List<Carriers> carriersList = query.getResultList();
		for (Carriers carriers : carriersList) {
			TypedQuery<Users> query2 =
					this.em.createQuery("SELECT u FROM UserCarriersRoles ucr inner join ucr.userCarriers uc inner join uc.users u "
							+ "where uc.carriers.code = :carrier and ucr.role.name = :role", Users.class);
			query2.setParameter("carrier", carriers.getCode());
			query2.setParameter("role", "admin_local");
			try {

				Users users = query2.getSingleResult();

				carriers.setAdminUser(users);
		   		//Role Object
				TypedQuery<Roles> query4 =
						this.em.createQuery("SELECT r FROM Roles r where r.name= :role", 
								Roles.class);
				query4.setParameter("role", "admin_local");

			}catch (Exception ex) {
				LOGGER.error(String.format("Carrier %s Code[%s] not user related user", carriers.getName(), carriers.getCode()));
			}
			//If the operator does not have an assigned theme, put the default theme
			if (carriers.getThemes()==null) {
				carriers.setThemes(this.getDefaultTheme(lang));
			}
		}

		token = token.substring(Keys.AUTHENTICATION_SCHEME.toString().length()).trim();

		List<UsersControlAccess> usersControlAccesses = this.getUserControlAccessByToken(token);

		if(usersControlAccesses.stream().findFirst().isPresent()) {
			final Users user = usersControlAccesses.stream().findFirst().get().getUser();

			List<RolesByUserNameView> rolesByUserNameViews = this.getRoleFromUserName(user);

			List<String> rolesByUser = rolesByUserNameViews.stream()
					.map(RolesByUserNameView::getRole)
					.flatMap(Stream::of)
					.map(role -> role.toLowerCase())
					.collect(Collectors.toList());

			List<String> superUsersRoles = Arrays.asList(com.zed.lurin.security.keys.Roles.ADMIN_LOCAL.toString().toLowerCase());

			final boolean isRoleExist =  superUsersRoles.stream().anyMatch(allow -> rolesByUser.contains(allow));
			if(isRoleExist){
				carriersList = carriersList.stream()
						.filter(ca->ca.getAdminUser() != null)
						.filter(ca->ca.getAdminUser().getLogin().equals(user.getLogin()))
						.collect(Collectors.toList());
			}

		}

		return carriersList;
	}
	
    /**
     * <p>method that obtain a carrier</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.Carriers}
     */
	@Override
	public Carriers getFindByCodeCarrier(long code, String lang) {
		Carriers carriers = this.em.find(Carriers.class, code);
		TypedQuery<Users> query2 =
				this.em.createQuery("SELECT u FROM UserCarriersRoles ucr inner join ucr.userCarriers uc inner join uc.users u "
						+ "where uc.carriers.code = :carrier and ucr.role.name = :role", Users.class);
		query2.setParameter("carrier", code);
		query2.setParameter("role", "admin_local");
		try {
			carriers.setAdminUser(query2.getSingleResult());
			//Role Object
			TypedQuery<Roles> query4 =
					this.em.createQuery("SELECT r FROM Roles r where r.name= :role", 
							Roles.class);
			query4.setParameter("role", "admin_local");
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		//If the operator does not have an assigned theme, put the default theme
		if (carriers.getThemes()==null) {
			carriers.setThemes(this.getDefaultTheme(lang));
		}
		return carriers; //return this.em.find(Carriers.class, code);
	}

	/**
	 * <p>method that validates the dependencies of the carriers</p>
	 * @param code carrier_id
	 * @return  boolean
	 */
	@Override
	public boolean validateDependencies(long code) {
		TypedQuery<CarrierDataSources> query =
				this.em.createQuery("SELECT cds FROM CarrierDataSources cds where cds.carriers.code = :carrier", CarrierDataSources.class);
		query.setParameter("carrier", code);
		
		if (query.getResultList().size()<=0) {
			TypedQuery<UserCarriers> query2 =
					this.em.createQuery("SELECT uc FROM UserCarriers uc where uc.carriers.code = :carrier", UserCarriers.class);
			query2.setParameter("carrier", code);
			return query2.getResultList().size()>0;
		}else
			return true;		
		
	}
	
	
	/**
	 * <p>method that obtain a Theme by default for operators without theme assigned or 
	 * for users with more than one operator assigned</p>
	 * @return Object {@link com.zed.lurin.domain.jpa.Themes}
	 */
	@Override
	public Themes getDefaultTheme(String lang) {
		TypedQuery<Themes> query =
                this.em.createQuery("SELECT t " +
                        "FROM Themes t " +
                        "WHERE t.name = :default", Themes.class);

        query.setParameter("default","DEFAULT-THEME");

        Themes theme = query.getSingleResult();
		if (theme!=null) {
			TypedQuery<ThemesDetailsView> query2 =
					this.em.createQuery("SELECT td FROM ThemesDetailsView td where td.theme = :theme", ThemesDetailsView.class);
			query2.setParameter("theme", theme.getThemeId());
			//COMPONERtheme.setDetails(query2.getResultList());
			theme.setDetails(query2.getResultList().stream()
		            .map(themesDetailsByLanguagesTransform(lang))
		            .collect(Collectors.toList()));
		}
		return theme;
	}
	
	/**
	 * method that returns operators per user
	 * @param username
	 * @return List by Operators
	 */
	@Override
	public List<OnlyCarriersByUserNameView> getOnlyCarriersByUserName(String username) {
		TypedQuery<OnlyCarriersByUserNameView> query =
				this.em.createQuery("SELECT vcu " +
								"FROM OnlyCarriersByUserNameView vcu " +
								"WHERE vcu.login=:login ",
						OnlyCarriersByUserNameView.class);

		query.setParameter("login", username);

		return query.getResultList();
	}


	/**
	 * method that returns operators per user
	 * @return List by Operators
	 */
	@Override
	public List<OnlyCarriersByUserNameView> getAllCarriersByUserName() {
		TypedQuery<OnlyCarriersByUserNameView> query =
				this.em.createQuery("SELECT vcu " +
								"FROM OnlyCarriersByUserNameView vcu ",
						OnlyCarriersByUserNameView.class);

		return query.getResultList();
	}


	/**
	 * method that returns only carrier
	 * @return List by Operators
	 */
	@Override
	public Carriers findById(long code) {

		return this.em.find(Carriers.class, code) ;
	}
	
    /**
     * Method Functional Programming for select default languajes
     * @param lang
     * @return
     */
    private Function<ThemesDetailsView, ThemesDetailsView> themesDetailsByLanguagesTransform(String lang) {
        return detail-> {
            switch (lang.toLowerCase()) {
            case "es":
            	if (detail.getLangES() != null && !detail.getLangES().isEmpty()) {
                	detail.setLangAssigned(detail.getLangES());            		
            	}else {
            		detail.setLangAssigned(detail.getTitle());
            	}
                break;
            case "en":
            	if (detail.getLangEN() != null && !detail.getLangEN().isEmpty()) {
            		detail.setLangAssigned(detail.getLangEN());
            	}else {
            		detail.setLangAssigned(detail.getTitle());
            	}
                break;
            case "pr":
            	if (detail.getLangPR() != null && !detail.getLangPR().isEmpty()) {
            		detail.setLangAssigned(detail.getLangPR());
            	}else {
            		detail.setLangAssigned(detail.getTitle());
            	}
                break;
            default:
            	if (detail.getDefaultI18n() != null && !detail.getDefaultI18n().isEmpty()) {
            		detail.setLangAssigned(detail.getDefaultI18n());
            	}else {
            		detail.setLangAssigned(detail.getTitle());
            	}
                break;
            }
            return detail;
        };
    }

	/**
	 * <p>method that obtain all carriers in database</p>
	 * <p>Note: criteria documentation jpa in
	 * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
	 * @return  List {@link com.zed.lurin.domain.jpa.Carriers}
	 */
	@Override
	public List<Carriers> getCarriers() {
		TypedQuery<Carriers> query =
				this.em.createQuery("SELECT c FROM Carriers c", Carriers.class);

		return query.getResultList();
	}

	/**
	 * Method that returns a list of results based on a token
	 * @param token
	 * @return List to {@link UsersControlAccess}
	 */
	@Override
	public List<UsersControlAccess> getUserControlAccessByToken(String token){
		TypedQuery<UsersControlAccess> query =
				this.em.createQuery("SELECT u " +
						"FROM UsersControlAccess u join u.status us " +
						"WHERE u.token = :token AND u.status=:status", UsersControlAccess.class);

		query.setParameter("token",token);
		query.setParameter("status", this.securityStatusServices.getCategoryActive());

		return query.getResultList();
	}

	/**
	 * method that return roles for username
	 * @param user
	 * @return
	 */
	@Override
	public List<RolesByUserNameView> getRoleFromUserName(Users user){
		TypedQuery<RolesByUserNameView> query =
				this.em.createQuery("SELECT m " +
								"FROM RolesByUserNameView m " +
								"WHERE m.login=:login",
						RolesByUserNameView.class);

		query.setParameter("login", user.getLogin());

		return query.getResultList();
	}
}
