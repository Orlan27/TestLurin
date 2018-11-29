package com.zed.lookfeel.management.rest.impl;

import com.zed.lookfeel.management.rest.ILookAndFeelManagementServicesRest;
import com.zed.lookfeel.management.services.ILookAndFeelManagementServices;
import com.zed.lookfeel.management.rest.entity.ImageEntity;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.domain.jpa.Themes;

import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;
import com.zed.lurin.security.keys.Keys;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.SecurityContext;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

/**
 * <p>Stateless REST where the methods that manage the look and feel management are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.lookfeel.management.rest.ILookAndFeelManagementServicesRest}
 */
@Path("/lookfeelmanagement")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/lookfeelmanagement", description = "Operations to manage the look and feel of user view",
        authorizations = {
        @Authorization(value = "bearer_token")
})
public class LookAndFeelManagementServicesRestImpl implements ILookAndFeelManagementServicesRest {

	 /*
     * Logger util
     */
    private static Logger LOGGER = Logger.getLogger(LookAndFeelManagementServicesRestImpl.class.getName());
    /**
     * Context Security
     */
    @Context
    SecurityContext securityContext;
    
    @EJB
    ICoreParameterServices iCoreParameterServices;
	
	@EJB
	ILookAndFeelManagementServices iLookAndFeelManagementServices;
	
	 /**
     * <p>method that creates a theme</p>
     * @param theme
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.THEMES_ADMINISTRATION, Roles.THEMES_ADMINISTRATION_CREATE})
    @Override
    @ApiOperation(value = "Create Themes",
            notes = "This method creates a theme for the look and feel of an operator. If you create the theme correctly it returns the generated id",
            response = Themes.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create Themes Correctly")
    })
    public Response createTheme(@ApiParam(value = "Create Themes object", required = true) Themes theme) {
    	Response response;

        try {
            response = Response.ok()
                    .entity(this.iLookAndFeelManagementServices.createTheme(theme))
                    .build();
        }catch (IllegalArgumentException ex) {
            response = Response.ok()
                            .status(Response.Status.BAD_REQUEST)
                            .entity(ex.getMessage()).build();

        } catch (Exception ex) {
            response = Response.ok()
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(ex.getMessage()).build();
        }
        LOGGER.info("return rest");
        return response;
    }

    /**
     * <p>method that updates a theme</p>
     * @param theme
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.THEMES_ADMINISTRATION, Roles.THEMES_ADMINISTRATION_UPDATE})
    @Override
    @ApiOperation(value = "Update Themes",
            notes = "This method updates a theme for the look and feel of an operator. If you update the theme correctly it returns the data updated",
            response = Themes.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update Themes Correctly")
    })
    public Response updateTheme(Themes theme) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iLookAndFeelManagementServices.updateTheme(theme))
                    .build();
        }catch (IllegalArgumentException ex) {
            response = Response.ok()
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();

        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }

        return response;
    }
    
    /**
     * <p>method that delete a theme</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.THEMES_ADMINISTRATION, Roles.THEMES_ADMINISTRATION_DELETE})
    @Override
    @ApiOperation(value = "Delete theme in the database",
            notes = "Delete theme in the database for look and feel management",
            response = Themes.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Delete Themes Correctly")
    })
    public Response deleteTheme(
    		@ApiParam(value = "Id of the theme to delete by theme_id", required = true)
    		@PathParam("id")long id) {
    	Response response;
        try {
        	//if (!this.iLookAndFeelManagementServices.validateThemeDependencies(id)) {
        		this.iLookAndFeelManagementServices.deleteTheme(id);
        		response = Response.ok().build();            
        	/*}else {
        		Map<String, String> map = new HashMap<String, String>();
        		map.put("code","0004");
        		map.put("message","Theme has dependent operator");
        		response = Response.ok()
                        .status(Response.Status.PRECONDITION_FAILED)
                        .entity(map).build();
        	}*/
        }catch (IllegalArgumentException ex) {
            response = Response.ok()
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();

        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }

        return response;
    }
    
    /**
     * <p>>method that obtain a theme</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.THEMES_ADMINISTRATION, Roles.THEMES_ADMINISTRATION_GET})
    @Override
    @ApiOperation(value = "Show a theme in the database",
            notes = "Show a theme in the database for look and feel management",
            response = Themes.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show Themes Correctly")
    })
    public Response getFindByThemeId(
    		@ApiParam(value = "Id of the theme to find by theme_id", required = true)
    		@PathParam("id") long id,
    		@HeaderParam(value="lang") String lang) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iLookAndFeelManagementServices.getThemeById(id, lang))
                    .build();

        }catch (IllegalArgumentException ex) {
            response = Response.ok()
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();

        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }

        return response;
    }
    
    /**
     * <p>method that obtain all themes</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.THEMES_ADMINISTRATION, Roles.THEMES_ADMINISTRATION_GET, Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all themes in the database",
            notes = "List all themes in the database for look and feel management",
            response = Themes.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all Themes Correctly")
    })
    public Response getAllThemes(@HeaderParam(value="lang") String lang,
                                 @HeaderParam(value = "Authorization") String token) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iLookAndFeelManagementServices.getAllThemes(lang, token))
                    .build();

        }catch (IllegalArgumentException ex) {
            response = Response.ok()
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();

        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }
        return response;
    }
    
    /**
     * <p>method to save an image that is accessible by URL in the system</p>
     *
     * @param imageEntity
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/saveImage")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.THEMES_ADMINISTRATION, Roles.THEMES_ADMINISTRATION_CREATE})
    @ApiOperation(value = "Save imagen in server dir",
            notes = "Method to save an image that is accessible by URL in the system. If you save the image correctly it returns the generated url",
            response = ImageEntity.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create Image Correctly")
    })
    public Response saveImage(
            @ApiParam(value = "Multipart save image object", required = true)
            @MultipartForm ImageEntity imageEntity) {
        Response response;
        try {
        	LOGGER.info("saveImage rest");
        	
        	String urlUploadFile = this.iCoreParameterServices.getCoreParameterString(Keys.UPLOAD_URL_IMG.toString());

        	final File rootFile = new File(urlUploadFile);
            if (!rootFile.exists()) {

                boolean res = rootFile.mkdirs();
                if (!res) {
                	String msgError = String.format("Couldn't create the file upload img directory: %s",rootFile.getAbsolutePath());
                    LOGGER.error(msgError);
                    throw new Exception(msgError);
                }
            }
        	
        	final File fileFinal = new File(urlUploadFile, imageEntity.getName());
        	if (fileFinal.exists()) {
        		boolean res = fileFinal.delete();
        		if (!res) {
        			String msgError = String.format("Error. File exists, but could not be deleted: %s",fileFinal.getAbsolutePath());
        			LOGGER.error(msgError);
        			throw new Exception(msgError);
        		}
        	}
        	try (FileOutputStream fos = new FileOutputStream(fileFinal)){
        		byte[] filebytes = imageEntity.getFile();
        		fos.write(filebytes);
        	}catch (Exception e){
        		LOGGER.error( String.format("Error file creation: %s",e.getMessage()));
        		throw e;
        	}
        	
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("url", "/img/"+imageEntity.getName());
        	response = Response.ok()
        			.entity(map).build();
        } catch (IllegalArgumentException ex) {
            response = Response.ok()
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();

        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }
        LOGGER.info("return saveImage rest");
        return response;
    }
}
