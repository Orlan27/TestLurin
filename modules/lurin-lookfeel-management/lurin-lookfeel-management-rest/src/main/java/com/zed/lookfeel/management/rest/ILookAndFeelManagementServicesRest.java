package com.zed.lookfeel.management.rest;

import com.zed.lurin.domain.jpa.Themes;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;
import com.zed.lookfeel.management.rest.entity.ImageEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>Abstract Class which defines the methods manage the operators managments are implemented</p>
 * @author Francisco Lujano
 */
public interface ILookAndFeelManagementServicesRest {

	 /**
     * <p>method that creates a theme</p>
     * @param theme
     * @return {@javax.ws.rs.core.Response}
     */
	Response createTheme(Themes theme);
	
    /**
     * <p>method that updates a theme</p>
     * @param theme
     * @return {@javax.ws.rs.core.Response}
     */
	Response updateTheme(Themes theme);
	
    /**
     * <p>method that delete a theme</p>
     * @return  {@javax.ws.rs.core.Response}
     */
	Response deleteTheme(long id);
	
    /**
     * <p>>method that obtain a theme</p>
     * @return  {@javax.ws.rs.core.Response}
     */
	Response getFindByThemeId(long id, String lang);
	
    /**
     * <p>method that obtain all themes</p>
     * @return  {@javax.ws.rs.core.Response}
     */
	Response getAllThemes(String lang, String token);
	
    /**
     * @title method to save an image that is accessible by URL in the system
     * @param imageEntity
     * @return {@javax.ws.rs.core.Response}
     */
    Response saveImage(ImageEntity imageEntity);
 
}
