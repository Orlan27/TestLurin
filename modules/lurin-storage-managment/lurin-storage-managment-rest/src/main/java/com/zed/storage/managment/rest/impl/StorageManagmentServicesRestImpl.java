package com.zed.storage.managment.rest.impl;

import com.zed.lurin.domain.dto.MessageTestConnectionDTO;
import com.zed.storage.managment.rest.IStorageManagmentServicesRest;
import com.zed.storage.managment.services.IStorageManagmentServices;
import com.zed.lurin.domain.jpa.DataSources;

import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;
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

/**
 * <p>Stateless REST where the methods that manage the storage managments are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.storage.managment.rest.IStorageManagmentServicesRest}
 */
@Path("/storagemanagment")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/storagemanagment", description = "Operation to manage storagemanagment",
        authorizations = {
        @Authorization(value = "bearer_token")
})
public class StorageManagmentServicesRestImpl implements IStorageManagmentServicesRest {

    /**
     * Context Security
     */
    @Context
    SecurityContext securityContext;
    
	@EJB
    IStorageManagmentServices iStorageManagmentServices;

	 /**
     * <p>method that creates a data source</p>
     * @param dataSource
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_CREATE})
    @Override
    @ApiOperation(value = "Create DataSources",
            notes = "This method creates a data source for an operator or CAS and if you create the data source correctly it returns the generated id",
            response = DataSources.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create DataSource Correctly")
    })
    public Response createDataSource(@ApiParam(value = "Create DataSources object", required = true) DataSources dataSource) {
    	Response response;

        try {
            response = Response.ok()
                    .entity(this.iStorageManagmentServices.createDataSource(dataSource))
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
     * <p>method that updates a data source</p>
     * @param dataSource
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_UPDATE})
    @Override
    @ApiOperation(value = "Update DataSources",
            notes = "This method updates a data source for an operator or CAS and if you update the data source correctly it returns the data updated",
            response = DataSources.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update DataSources Correctly")
    })
    public Response updateDataSource(DataSources dataSource) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iStorageManagmentServices.updateDataSource(dataSource))
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
     * <p>method that deactivate a data source</p>
     * @param code
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/deactivate/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_DEACTIVATE})
    @Override
    @ApiOperation(value = "Deactivate DataSource",
            notes = "This method deactivate a data source",
            response = DataSources.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Deactivate DataSource Correctly")
    })
    public Response deactivateDataSource(
    		@ApiParam(value = "code  of the data source to deactivate", required = true)
    		@PathParam("code") long code) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iStorageManagmentServices.deactivateDataSource(code))
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
     * <p>method that obtain all data sources</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all data sources in the database",
            notes = "List all data sources in the database for storage managment administration",
            response = DataSources.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all DataSources Correctly")
    })
    public Response getAllDataSources() {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iStorageManagmentServices.getAllDataSources())
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
     * <p>>method that obtain a data sources</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "Show a data source in the database",
            notes = "Show a data source in the database for storage managment administration",
            response = DataSources.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show DataSource Correctly")
    })
    public Response getFindByIdDataSource(
    		@ApiParam(value = "Code of the data source to find by code", required = true)
    		@PathParam("code") long code) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iStorageManagmentServices.getFindByCodeDataSource(code))
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
     * <p>method that delete a data source</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @DELETE
    @Path("/delete/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_DELETE})
    @Override
    @ApiOperation(value = "Delete data source in the database",
            notes = "Delete data sources in the database for storage managment administration",
            response = DataSources.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Delete DataSources Correctly")
    })
    public Response deleteDataSource(
    		@ApiParam(value = "Code of the data source to delete by code", required = true)
    		@PathParam("code")long code) {
    	Response response;
        try {
        	if (!this.iStorageManagmentServices.validataDataSourceDependencies(code)) {
        		this.iStorageManagmentServices.deleteDataSource(code);
        		response = Response.ok().build();            
        	}else {
        		Map<String, String> map = new HashMap<String, String>();
        		map.put("code","0004");
        		map.put("message","Data Source has dependent operators");
        		response = Response.ok()
                        .status(Response.Status.PRECONDITION_FAILED)
                        .entity(map).build();
        	}
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
     * <p>method that obtain all connection types for data sources</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getSourceTypes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all types for data sources in the database",
            notes = "List all types for data sources in the database for storage managment administration",
            response = DataSources.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all SourceTypes Correctly")
    })
    public Response getSourceTypes() {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iStorageManagmentServices.getSourceTypes())
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
     * <p>method that obtain all connection types for data sources</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getSourceTypes/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all types for data sources in the database",
            notes = "List all types for data sources in the database for storage managment administration",
            response = DataSources.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all SourceTypes Correctly")
    })
    public Response getSourceTypes(
            @ApiParam(value = "Abbreviation of typo dataSource", required = true)
            @PathParam("type") String type) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iStorageManagmentServices.getSourceTypes(type))
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
     * <p>method that obtain all connection types for data sources</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/testDataSource")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "Test if data source is listening",
            notes = "Test if data source is listening for storage managment administration",
            response = MessageTestConnectionDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "DataSources is listening or not listening")
    })
    public Response isDataSourceListening(@ApiParam(value = "Listening DataSources object", required = true) DataSources dataSource) {
    	Response response;
        try {
        	response = Response.ok().entity(this.iStorageManagmentServices.isDataSourceListening(dataSource)).build();
        }catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }

        return response;
    }


    /**
     * <p>method that obtain only data sources</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getInfoDataSource/{jndiName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "data sources in the database",
            notes = "data sources in the database for storage managment administration",
            response = DataSources.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "DataSources Correctly")
    })
    public Response getJndiNameDataSources(
            @ApiParam(value = "jndiName value for search", required = true)
            @PathParam("jndiName") String jndiName) {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iStorageManagmentServices.getJndiNameDataSources(jndiName))
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

}
