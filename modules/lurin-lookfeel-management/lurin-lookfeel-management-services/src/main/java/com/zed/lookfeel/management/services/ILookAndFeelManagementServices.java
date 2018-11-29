package com.zed.lookfeel.management.services;

import com.zed.lurin.domain.jpa.Themes;
import com.zed.lurin.domain.jpa.Users;
import com.zed.lurin.domain.jpa.UsersControlAccess;
import com.zed.lurin.domain.jpa.view.OnlyCarriersByUserNameView;
import com.zed.lurin.domain.jpa.view.RolesByUserNameView;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * <p>Abstract Class which defines the methods manage the look&feel managements are implemented</p>
 * @author Francisco Lujano
 */
public interface ILookAndFeelManagementServices {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.lookfeel.management.services/LookAndFeelManagementServicesImpl!com.zed.lookfeel.management.services.ILookAndFeelManagementServices";
	
    /**
     * <p>method that creates a theme</p>
     * @param theme
     * @return id self-generated
     */
	Themes createTheme(Themes theme);
	
    /**
     * <p>method that updates a theme</p>
     * @param theme
     * @return Object {@link com.zed.lurin.domain.jpa.Themes}
     */
	Themes updateTheme(Themes theme);
	
    /**
     * <p>method that delete a carrier theme</p>
     * @param theme_id theme id to delete
     * @return  void
     */
	void deleteTheme(long theme_id);
	
	/**
	 * <p>method that obtain a Theme</p>
     * @param theme_id 
	 * @return Object {@link com.zed.lurin.domain.jpa.Themes}
	 */
	Themes getThemeById(long theme_id, String lang);
	
    /**
     * <p>method that obtain all themes</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.Themes}
     */
	List<Themes> getAllThemes(String lang, String token);

	/**
	 * method that returns operators per user
	 * @param username
	 * @return List by Operators
	 */
	List<OnlyCarriersByUserNameView> getOnlyCarriersByUserName(String username);

	/**
	 * method that return roles for username
	 * @param user
	 * @return
	 */
	List<RolesByUserNameView> getRoleFromUserName(Users user);

	/**
	 * Method that returns a list of results based on a token
	 * @param token
	 * @return List to {@link UsersControlAccess}
	 */
	List<UsersControlAccess> getUserControlAccessByToken(String token);

}
