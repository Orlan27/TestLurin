package com.zed.lookfeel.management.services.impl;


import com.zed.lurin.domain.jpa.Themes;
import com.zed.lurin.domain.jpa.ThemesDetails;
import com.zed.lurin.domain.jpa.Users;
import com.zed.lurin.domain.jpa.UsersControlAccess;
import com.zed.lurin.domain.jpa.view.OnlyCarriersByUserNameView;
import com.zed.lurin.domain.jpa.view.RolesByUserNameView;
import com.zed.lurin.domain.jpa.view.ThemesDetailsView;
import com.zed.lookfeel.management.services.ILookAndFeelManagementServices;
import com.zed.lurin.domain.services.ISecurityStatusServices;
import com.zed.lurin.security.keys.Keys;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.security.jacc.PolicyContext;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Stateless where the methods that manage the look&feel managments are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.lookfeel.management.services.ILookAndFeelManagementServices}
 */
@Stateless
public class LookAndFeelManagementServicesImpl implements ILookAndFeelManagementServices {

    /**
     * Entity Manager reference.
     */
	@PersistenceContext(unitName="lurin-security-em")
    private EntityManager em;

	@EJB
	ISecurityStatusServices iSecurityStatusServices;

    /**
     * <p>method that creates a theme</p>
     * @param theme
     * @return id self-generated
     */
	@Override
	public Themes createTheme(Themes theme) {
		List<ThemesDetailsView> details = theme.getDetails();
		this.em.persist(theme);		
		for(ThemesDetailsView detailView : details) {
			ThemesDetails detail = parseThemesDetails(detailView);
			detail.setTheme(theme.getThemeId());
			this.em.persist(detail);
		}
		return theme;
	}
	
    /**
     * <p>method that updates a theme</p>
     * @param theme
     * @return Object {@link com.zed.lurin.domain.jpa.Themes}
     */
	@Override
	public Themes updateTheme(Themes theme) {
		this.em.merge(theme);
		Query query =
				this.em.createQuery("DELETE FROM ThemesDetails td where td.theme = :theme");
		query.setParameter("theme", theme.getThemeId());
		query.executeUpdate();
		List<ThemesDetailsView> details = theme.getDetails();
		for(ThemesDetailsView detailView : details) {
			ThemesDetails detail = parseThemesDetails(detailView);
			detail.setTheme(theme.getThemeId());
			this.em.persist(detail);
		}
        return theme;
	}
	
    /**
     * <p>method that delete a theme</p>
     * @param theme_id theme id to delete
     * @return  void
     */
	@Override
	public void deleteTheme(long theme_id) {
		Themes theme = this.em.find(Themes.class, theme_id);
		Query query =
				this.em.createQuery("DELETE FROM ThemesDetails td where td.theme = :theme");
		query.setParameter("theme", theme.getThemeId());
		query.executeUpdate();
	
		this.em.remove(theme);
	}
	
	/**
	 * <p>method that obtain a Theme</p>
     * @param theme_id 
	 * @return Object {@link com.zed.lurin.domain.jpa.Themes}
	 */
	@Override
	public Themes getThemeById(long theme_id, String lang) {
		Themes theme = this.em.find(Themes.class, theme_id);
		if (theme!=null) {
			TypedQuery<ThemesDetailsView> query =
					this.em.createQuery("SELECT td FROM ThemesDetailsView td where td.theme = :theme", ThemesDetailsView.class);
			query.setParameter("theme", theme.getThemeId());
			//theme.setDetails(query.getResultList());
			theme.setDetails(query.getResultList().stream()
		            .map(themesDetailsByLanguagesTransform(lang))
		            .collect(Collectors.toList()));
		}
		return theme;
	}

    /**
     * <p>method that obtain all themes</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.Themes}
     */
	@Override
	public List<Themes> getAllThemes(String lang, String token) {
		TypedQuery<Themes> query =
				this.em.createQuery("SELECT t FROM Themes t", Themes.class);
		List<Themes> themes = query.getResultList();
		for(Themes theme:themes) {
			TypedQuery<ThemesDetailsView> query2 =
					this.em.createQuery("SELECT td FROM ThemesDetailsView td where td.theme = :theme", ThemesDetailsView.class);
			query2.setParameter("theme", theme.getThemeId());
			//theme.setDetails(query2.getResultList());
			theme.setDetails(query2.getResultList().stream()
            .map(themesDetailsByLanguagesTransform(lang))
            .collect(Collectors.toList()));
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
				List<OnlyCarriersByUserNameView> onlyCarriersByUserName = this.getOnlyCarriersByUserName(user.getLogin());

				Optional<OnlyCarriersByUserNameView> optional = onlyCarriersByUserName.stream().findFirst();
				if(optional.isPresent()){
					OnlyCarriersByUserNameView onlyCarriersByUserNameView = optional.get();
					themes = themes.stream()
							.filter(the -> the.getThemeId().equals(Long.valueOf(onlyCarriersByUserNameView.getIdTheme())))
							.collect(Collectors.toList());
				}
			}

		}

		return themes;
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
		query.setParameter("status", this.iSecurityStatusServices.getCategoryActive());

		return query.getResultList();
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
     * Method to parse ThemesDetailsView data to ThemesDetails
     */
    private ThemesDetails parseThemesDetails(ThemesDetailsView detailView) {
		ThemesDetails detail = new ThemesDetails();
		detail.setId(detailView.getId());
		detail.setTheme(detailView.getTheme());
		detail.setName(detailView.getName());
		detail.setTitle(detailView.getTitle());
		detail.setValue(detailView.getValue());
		detail.setType(detailView.getType());
		return detail;
	}
	


}
