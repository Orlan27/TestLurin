package com.zed.lurin.parameter.services;

import com.zed.lurin.domain.jpa.CoreParameter;
import com.zed.lurin.domain.jpa.ParametersCategory;

import javax.ejb.Local;
import java.util.List;

/**
 * <p>Abstract Class where the methods that manage the core parameters are implemented</p>
 * @author Francisco Lujano
 */
@Local
public interface ICoreParameterServices {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.parameter.services/CoreParameterServicesImpl!com.zed.lurin.parameter.services.ICoreParameterServices";

    /**
     * method that returns the long value assigned to a key
     * @param key
     * @return int value
     */
    int getCoreParameterInteger(String key);

    /**
     * method that returns the core parameter object assigned to a key
     * @param key
     * @return {@link CoreParameter}
     */
    CoreParameter getCoreParameter(String key);

    /**
     * method that returns the String value assigned to a key
     * @param key
     * @return String value
     */
    String getCoreParameterString(String key);
    
    /**
     * <p>method that updates a core parameter</p>
     * @param coreParameter
     * @return Object {@link com.zed.lurin.domain.jpa.CoreParameter}
     */
	CoreParameter updateCoreParameter(CoreParameter coreParameter);
	
    /**
     * <p>method that obtain all core parameters</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.CoreParameter}
     */
	List<CoreParameter> getAllCoreParameter();
	
    /**
     * <p>method that obtain a core parameter</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.CoreParameter}
     */
	CoreParameter getFindByCodeParameter(String code);
       
    /**
     * <p>method that obtain all core parameters</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.CoreParameter}
     */
	
	List<CoreParameter> getListCoreParameterbyCategory(String category);
	
	/**
     * <p>method that obtain all parameters categories</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.ParametersCategory}
     */
    List<ParametersCategory> getAllParametersCategories();
	
}
