package com.zed.lurin.parameter.services.impl;

import com.zed.lurin.domain.jpa.CoreParameter;
import com.zed.lurin.domain.jpa.ParametersCategory;
import com.zed.lurin.parameter.services.ICoreParameterServices;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;


/**
 * <p>Stateless where the methods that manage the core parameters are implemented</p>
 * @author Francisco Lujano
 */
@Stateless
public class CoreParameterServicesImpl implements ICoreParameterServices {

    /**
     * Entity Manager reference.
     */
    @PersistenceContext(unitName="lurin-admin-em")
    private EntityManager em;

    /**
     * method that returns the String value assigned to a key
     * @param key
     * @return String value
     */
    @Override
    public String getCoreParameterString(String key){
        TypedQuery<CoreParameter> query =
                this.em.createQuery("SELECT cp " +
                        "FROM CoreParameter cp " +
                        "WHERE cp.key = :key", CoreParameter.class);

        query.setParameter("key",key);

        CoreParameter coreParameter = query.getSingleResult();

        return coreParameter.getValue();
    }

    /**
     * method that returns the long value assigned to a key
     * @param key
     * @return int value
     */
    @Override
    public int getCoreParameterInteger(String key){
        TypedQuery<CoreParameter> query =
                this.em.createQuery("SELECT cp " +
                        "FROM CoreParameter cp " +
                        "WHERE cp.key = :key", CoreParameter.class);

        query.setParameter("key",key);

        CoreParameter coreParameter = query.getSingleResult();

        return Integer.parseInt(coreParameter.getValue());
    }

    /**
     * method that returns the core parameter object assigned to a key
     * @param key
     * @return {@link CoreParameter}
     */
    @Override
    public CoreParameter getCoreParameter(String key){
        TypedQuery<CoreParameter> query =
                this.em.createQuery("SELECT cp " +
                        "FROM CoreParameter cp " +
                        "WHERE cp.key = :key", CoreParameter.class);

        query.setParameter("key",key);

        return query.getSingleResult();
    }
    
    /**
     * <p>method that updates a core parameter</p>
     * @param coreParameter
     * @return Object {@link com.zed.lurin.domain.jpa.CoreParameter}
     */
	@Override
	public CoreParameter updateCoreParameter(CoreParameter coreParameter) {
		this.em.merge(coreParameter);
        return coreParameter;
	}
	
    /**
     * <p>method that obtain all core parameters</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.CoreParameter}
     */
	@Override
	public List<CoreParameter> getAllCoreParameter() {
		TypedQuery<CoreParameter> query =
				this.em.createQuery("SELECT cp FROM CoreParameter cp", CoreParameter.class);
		return query.getResultList();
	}
	
    /**
     * <p>method that obtain a core parameter</p>
     * @return  Object {@link com.zed.lurin.domain.jpa.CoreParameter}
     */
	@Override
	public CoreParameter getFindByCodeParameter(String code) {
		return this.em.find(CoreParameter.class, code);
	}
        
        
    /**
     * <p>method that obtain all core parameters</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.CoreParameter}
     */
    @Override
    public List<CoreParameter> getListCoreParameterbyCategory(String category) {
        TypedQuery<CoreParameter> query =
                this.em.createQuery("SELECT cp " +
                        "FROM CoreParameter cp " +
                        "WHERE cp.category.name = :category", CoreParameter.class);

        query.setParameter("category",category);

        List<CoreParameter> listcoreParameter = query.getResultList();

        return listcoreParameter;
       
    }
    
    /**
     * <p>method that obtain all parameters categories</p>
     * <p>Note: criteria documentation jpa in
     * <a href="http://www.objectdb.com/java/jpa/query/jpql/select">JPA Select</a></p>
     * @return  Object {@link com.zed.lurin.domain.jpa.ParametersCategory}
     */
    @Override
    public List<ParametersCategory> getAllParametersCategories() {
    	TypedQuery<ParametersCategory> query =
    			this.em.createQuery("SELECT pc FROM ParametersCategory pc", ParametersCategory.class);
    	return query.getResultList();
    }

}
