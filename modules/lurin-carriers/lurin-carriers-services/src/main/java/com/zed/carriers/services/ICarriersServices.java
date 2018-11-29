package com.zed.carriers.services;

import com.zed.lurin.domain.jpa.Users;
import com.zed.lurin.domain.jpa.UsersControlAccess;
import com.zed.lurin.domain.jpa.view.CarriersByUserNameView;
import com.zed.lurin.domain.jpa.Carriers;
import com.zed.lurin.domain.jpa.Themes;
import com.zed.lurin.domain.jpa.view.OnlyCarriersByUserNameView;
import com.zed.lurin.domain.jpa.view.RolesByUserNameView;

import java.util.List;

/**
 * Abstract Class that contains the operator management methods
 * @author Francisco Lujano
 */
public interface ICarriersServices {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.carriers.services/CarriersServicesImpl!com.zed.carriers.services.ICarriersServices";

    /**
     * method that returns operators per user
     * @param username
     * @return List by Operators
     */
    List<CarriersByUserNameView> getCarriersByUserName(String username);
    
    /**
     * <p>method that create a carrier</p>
     * @param carrier
     * @return id self-generated
     */
    long createCarrier(Carriers carrier);
    
    /**
     * <p>method that updates a carrier</p>
     * @param carrier
     * @return Object {@link com.zed.lurin.domain.jpa.Carriers}
     */
    Carriers updateCarrier(Carriers carrier);
    
    
    /**
     * <p>method that delete a carrier</p>
     * @param code
     * @return  void
     */
    void deleteCarrier(long code);
    
    /**
     * <p>method that obtain all carriers in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  List {@link com.zed.lurin.domain.jpa.Carriers}
     */
    List<Carriers> getAllCarriers(String lang, String token);
    
    /**
     * <p>method that obtain a carrier</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.Carriers}
     */
    Carriers getFindByCodeCarrier(long code, String lang);
    
	/**
	 * <p>method that validates the dependencies of the carriers</p>
	 * @param code carrier_id
	 * @return  boolean
	 */
    boolean validateDependencies(long code);
    

	/**
	 * <p>method that obtain a Theme by default for operators without theme assigned or 
	 * for users with more than one operator assigned</p>
	 * @param lang 
	 * @return Object {@link com.zed.lurin.domain.jpa.Themes}
	 */
	Themes getDefaultTheme(String lang);

    /**
     * method that returns only operators for user
     * @param username
     * @return List by Operators
     */
    List<OnlyCarriersByUserNameView> getOnlyCarriersByUserName(String username);

    /**
     * method that returns operators per user
     * @return List by Operators
     */
    List<OnlyCarriersByUserNameView> getAllCarriersByUserName();

    /**
     * method that returns only carrier
     * @return List by Operators
     */
    Carriers findById(long code);

    /**
     * <p>method that obtain all carriers in database</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  List {@link com.zed.lurin.domain.jpa.Carriers}
     */
    List<Carriers> getCarriers();

    /**
     * Method that returns a list of results based on a token
     * @param token
     * @return List to {@link UsersControlAccess}
     */
    List<UsersControlAccess> getUserControlAccessByToken(String token);

    /**
     * method that return roles for username
     * @param user
     * @return
     */
    List<RolesByUserNameView> getRoleFromUserName(Users user);
}
