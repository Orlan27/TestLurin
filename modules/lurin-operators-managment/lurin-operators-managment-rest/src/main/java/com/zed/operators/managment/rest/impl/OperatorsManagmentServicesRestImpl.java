package com.zed.operators.managment.rest.impl;

import com.zed.lurin.domain.dto.MessageCustomDTO;
import com.zed.lurin.domain.jpa.view.DataSourcesAvailableView;
import com.zed.operators.managment.rest.IOperatorsManagmentServicesRest;
import com.zed.operators.managment.services.IOperatorsManagmentServices;
import com.zed.carriers.services.ICarriersServices;
import com.zed.lurin.domain.jpa.*;

import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;
import com.zed.operators.managment.services.exceptions.ExceptionCarrierCause;
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

import org.apache.log4j.Logger;

/**
 * <p>Stateless REST where the methods that manage the operators managments are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.operators.managment.rest.IOperatorsManagmentServicesRest}
 */
@Path("/operatorsmanagment")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/operatorsmanagment", description = "Operation to manage operatorsmanagment",
        authorizations = {
        @Authorization(value = "bearer_token")
})
public class OperatorsManagmentServicesRestImpl implements IOperatorsManagmentServicesRest {

	 /*
     * Logger util
     */
    private static Logger LOGGER = Logger.getLogger(OperatorsManagmentServicesRestImpl.class.getName());
    /**
     * Context Security
     */
    @Context
    SecurityContext securityContext;
    
	@EJB
	ICarriersServices iCarriersServices;
	
	@EJB
	IOperatorsManagmentServices iOperatorsManagmentServices;
	
	 /**
     * <p>method that creates a carrier</p>
     * @param carrier
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_CREATE})
    @Override
    @ApiOperation(value = "Create Carriers",
            notes = "This method creates an operator. If you create the carrier correctly it returns the generated id",
            response = Carriers.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create Carrier Correctly")
    })
    public Response createCarrier(@ApiParam(value = "Create Carriers object", required = true) Carriers carrier) {
    	Response response;

        try {
        	response = Response.ok()
                    .entity(this.iOperatorsManagmentServices.createCarrier(carrier))
                    .build();
        }catch (IllegalArgumentException ex) {
            response = Response.ok()
                            .status(Response.Status.BAD_REQUEST)
                            .entity(ex.getMessage()).build();

        }catch (ExceptionCarrierCause ex) {

            MessageCustomDTO messageCustomDTO = new MessageCustomDTO();
            messageCustomDTO.setSuccess(false);
            messageCustomDTO.setMessage(ex.getMessage());
            messageCustomDTO.setCodeError(ex.getCodes().toString());
            messageCustomDTO.setKeyMessage(ex.getKeyMessage());

            response = Response.ok()
                    .status(Response.Status.BAD_REQUEST)
                    .entity(messageCustomDTO).build();

        } catch (Exception ex) {
            response = Response.ok()
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(ex.getMessage()).build();
        }

        return response;
    }
 
    
    /**
     * <p>method that updates a carrier</p>
     * @param carrier
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_UPDATE})
    @Override
    @ApiOperation(value = "Update Carriers",
            notes = "This method updates an operator. If you update the carrier correctly it returns the data updated",
            response = Carriers.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "No token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Update Carrier Correctly")
    })
    public Response updateCarrier(Carriers carrier) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iOperatorsManagmentServices.updateCarrier(carrier))
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
     * <p>method that delete a carrier</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @DELETE
    @Path("/delete/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_DELETE})
    @Override
    @ApiOperation(value = "Delete carrier in the database",
            notes = "Delete carrier in the database for operators managment",
            response = Carriers.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Delete Carrier Correctly")
    })
    public Response deleteCarrier(
    		@ApiParam(value = "Code of the carrier to delete by code", required = true)
    		@PathParam("code")long code) {
    	Response response;
        try {
        	if (!this.iCarriersServices.validateDependencies(code)) {
        		this.iCarriersServices.deleteCarrier(code);
        		response = Response.ok().build();            
        	}else {
        		Map<String, String> map = new HashMap<String, String>();
        		map.put("code","0004");
        		map.put("message","Carrier has dependencies");
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
     * <p>method that obtain all carriers</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_GET, Roles.USER_ADMINISTRATION, Roles.USER_ADMINISTRATION_GET})
    @Override
    @ApiOperation(value = "List all carriers in the database",
            notes = "List all carriers in the database for operators managment",
            response = Carriers.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all carriers Correctly")
    })
    public Response getAllCarriers(@HeaderParam(value="lang") String lang, @HeaderParam(value = "Authorization") String token) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iCarriersServices.getAllCarriers(lang, token))
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
     * <p>>method that obtain a carrier</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/get/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_GET, Roles.FREEVIEW, Roles.FREEVIEW_GET, Roles.CAMPAIGN, Roles.CAMPAIGN_GET})
    @Override
    @ApiOperation(value = "Show a carrier in the database",
            notes = "Show a carrier in the database for operators managment",
            response = Carriers.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Show Carriers Correctly")
    })
    public Response getFindByIdCarrier(
    		@ApiParam(value = "Code of the carrier to find by code", required = true)
    		@PathParam("code") long code,
    		@HeaderParam(value="lang") String lang) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iCarriersServices.getFindByCodeCarrier(code, lang))
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
     * <p>method that obtain all data sources available for carriers</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getDataSources")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all data sources available for carriers in the database",
            notes = "List all data sources available for carriers in the database for operators managment",
            response = DataSourcesAvailableView.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all DataSources Correctly")
    })
    public Response getDataSourcesAvailableMerge() {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iOperatorsManagmentServices.getDataSourcesAvailable())
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
     * <p>method that obtain all data sources available for carriers</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getDataSources/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all data for type sources available for carriers in the database",
            notes = "List all data sources available for type for carriers in the database for operators managment",
            response = DataSources.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all DataSources Correctly")
    })
    public Response getDataSourcesAvailable(
            @ApiParam(value = "Abbreviation of typo dataSource", required = true)
            @PathParam("type") String type) {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iOperatorsManagmentServices.getDataSourcesAvailable(type))
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
     * <p>method that obtain all countries in database</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getCountries")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all countries available for carriers in the database",
            notes = "List all countries available for carriers in the database for operators managment",
            response = Countries.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all countries Correctly")
    })
    public Response getCountries() {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iOperatorsManagmentServices.getCountries())
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
     * <p>method that obtain all tables for carriers in database</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getTables")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all tables for carriers in the database",
            notes = "List all tables for carriers in the database for operators managment",
            response = Tables.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all tables Correctly")
    })
    public Response getTables() {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iOperatorsManagmentServices.getTables())
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
     * <p>method that obtain all companies for carriers in database</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getCompanies")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all companies for carriers in the database",
            notes = "List all companies for carriers in the database for operators managment",
            response = Companies.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all companies Correctly")
    })
    public Response getCompanies() {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iOperatorsManagmentServices.getCompanies())
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
     * <p>method that obtain all status for entities in database</p>
     * @return  {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getSecurityCategoryStatus")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.OPERATOR_MANAGEMENT, Roles.OPERATOR_MANAGEMENT_GET, Roles.STORE_MANAGEMENT, Roles.STORE_MANAGEMENT_GET})
    @Override
    @ApiOperation(value = "List all SecurityCategoryStatus in the database",
            notes = "List all SecurityCategoryStatus in the database for operators managment",
            response = SecurityCategoryStatus.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all SecurityCategoryStatus Correctly")
    })
    public Response getSecurityCategoryStatus() {
    	Response response;
        try {
            response = Response.ok()
                    .entity(this.iOperatorsManagmentServices.getSecurityCategoryStatus())
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
