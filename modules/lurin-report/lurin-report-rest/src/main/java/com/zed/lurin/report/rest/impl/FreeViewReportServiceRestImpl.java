package com.zed.lurin.report.rest.impl;

import com.zed.lurin.domain.dto.RequestReportCampaignDto;
import com.zed.lurin.domain.dto.RequestReportFreeViewDto;
import com.zed.lurin.domain.dto.ResponseReportDto;
import com.zed.lurin.domain.jpa.CampaignReport;
import com.zed.lurin.domain.jpa.FreeWiewsReport;
import com.zed.lurin.domain.jpa.CategorySchStatus;
import com.zed.lurin.report.rest.IFreeViewReportServiceRest;
import com.zed.lurin.security.keys.Roles;
import com.zed.lurin.security.filters.services.Secured;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.zed.lurin.security.filters.services.bean.JpaDynamicBean;
import com.zed.lurin.security.filters.services.DynamicJPA;
import javax.inject.Inject;
import com.zed.lurin.report.service.IReportService;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.domain.services.IStatusSchemaServices;

/**
 *
 * @author Llince
 */
@Path("/report")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/report", description = "Operation to manage report")
public class FreeViewReportServiceRestImpl implements IFreeViewReportServiceRest {

    @EJB
    IReportService iReportService;

    @Inject
    JpaDynamicBean jpaDynamicBean;

    @EJB
    ICoreParameterServices iCoreParameterServices;
    
    @EJB
    IStatusSchemaServices iStatusSchemaServices;

    /**
     * <p>
     * method that consult report free Views</p>
     *
     * @param report
     * @return {
     * @javax.ws.rs.core.Response}
     */
    @POST
    @Path("/freeView")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.FREEVIEW, Roles.FREEVIEW_GET})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @DynamicJPA
    @ApiOperation(value = "Consult Report free Views",
            notes = "This method Consult Report free Views and if you Consult Report free Views it returns the PDF,XLS or CSV",
            response = ResponseReportDto.class,
            authorizations = {
                @Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "no token authorization or invalid permits")
        ,
            @ApiResponse(code = 200, message = "Consult Report free Views Correctly")
    })
    @Override
    public Response getReportfreeView(@ApiParam(value = "RequestReportFreeViewDto object", required = true) RequestReportFreeViewDto report) {
        Response response;

        try {
            byte[] reporte = null;
            ResponseReportDto responseReportDto = new ResponseReportDto();
            String typeRepor = report.getTypeReport();

            String pathReport = this.iCoreParameterServices.getCoreParameter("PATH.JASPER.REPORT.FREE.VIEW").getValue();
            String pathImages = this.iCoreParameterServices.getCoreParameter("PATH.LOGO.REPORT.FREE.VIEW").getValue();
            String idCarrier = report.getIdCarrier();
            List<HashMap<String, Object>> data = this.iReportService.getDataReportFreeWiews(report.getStatus(), idCarrier, jpaDynamicBean.getJndiName());
            HashMap<String, Object> parmeter = new HashMap<String, Object>();
            parmeter.put("ColectionParam", data);
            parmeter.put("pathImagesLogo", pathImages);
            if (data.stream().findFirst().isPresent()) {
                responseReportDto = responseDto(typeRepor, pathReport, parmeter, data);
                response = Response.ok()
                        .entity(responseReportDto)
                        .build();
            } else {
                response = Response.ok().status(Response.Status.NO_CONTENT).build();
            }
        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }
        return response;
    }

    @POST
    @Path("/dataFreeView")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.FREEVIEW, Roles.FREEVIEW_GET})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @DynamicJPA
    @ApiOperation(value = "Consult Data free Views",
            notes = "This method Consult Report free Views and if you Consult Dtaa free Views ",
            response = FreeWiewsReport.class,
            authorizations = {
                @Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "no token authorization or invalid permits")
        ,
            @ApiResponse(code = 200, message = "Consult Data free Views Correctly")
    })
    @Override
    public Response getDatafreeView(@ApiParam(value = "RequestReportFreeViewDto object", required = true) RequestReportFreeViewDto report) {
        Response response;

        try {

            List<FreeWiewsReport> data = this.iReportService.getDataFreeWiews(report.getStatus(), report.getIdCarrier(), jpaDynamicBean.getJndiName());

            if (data.stream().findFirst().isPresent()) {
                response = Response.ok()
                        .entity(data)
                        .build();
            } else {
                response = Response.ok().status(Response.Status.NO_CONTENT).build();
            }
        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }
        return response;
    }

    /**
     * <p>
     * method that consult report free Views</p>
     *
     * @param report
     * @return {
     * @javax.ws.rs.core.Response}
     */
    @POST
    @Path("/campaign")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.FREEVIEW, Roles.FREEVIEW_GET})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @DynamicJPA
    @ApiOperation(value = "Consult Reportcampaign",
            notes = "This method Consult Report campaign and if you Consult Report campaign it returns the PDF,XLS or CSV",
            response = ResponseReportDto.class,
            authorizations = {
                @Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "no token authorization or invalid permits")
        ,
            @ApiResponse(code = 200, message = "Consult Report campaign Correctly")
    })
    @Override
    public Response getReportCampaign(@ApiParam(value = "RequestReportCampaignDto object", required = true) RequestReportCampaignDto report) {
        Response response;

        try {
            byte[] reporte = null;
            ResponseReportDto responseReportDto = new ResponseReportDto();
            String typeRepor = report.getTypeReport();
            String pathReport = this.iCoreParameterServices.getCoreParameter("PATH.JASPER.REPORT.CAMPAIGN").getValue();
            String pathImages = this.iCoreParameterServices.getCoreParameter("PATH.LOGO.REPORT.FREE.VIEW").getValue();
            List<HashMap<String, Object>> data = this.iReportService.getDataReportCampaign(report, jpaDynamicBean.getJndiName());
            HashMap<String, Object> parmeter = new HashMap<String, Object>();
            parmeter.put("ColectionParam", data);
            parmeter.put("pathImagesLogo", pathImages);
            if (data.stream().findFirst().isPresent()) {
                responseReportDto = responseDto(typeRepor, pathReport, parmeter, data);
                response = Response.ok()
                        .entity(responseReportDto)
                        .build();
            } else {
                response = Response.ok().status(Response.Status.NO_CONTENT).build();
            }
        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }
        return response;
    }

    /**
     * <p>
     * method that consult report free Views</p>
     *
     * @param report
     * @return {
     * @javax.ws.rs.core.Response}
     */
    @POST
    @Path("/dataCampaign")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.FREEVIEW, Roles.FREEVIEW_GET})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @DynamicJPA
    @ApiOperation(value = "Consult CampaignReport",
            notes = "This method Consult Report campaign and if you Consult data campaign",
            response = CampaignReport.class,
            authorizations = {
                @Authorization(value = "bearer_token")}
    )
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "no token authorization or invalid permits")
        ,
            @ApiResponse(code = 200, message = "Consult data campaign Correctly")
    })
    @Override
    public Response getDataCampaign(@ApiParam(value = "RequestReportCampaignDto object", required = true) RequestReportCampaignDto report) {
        Response response;

        try {
            List<CampaignReport> data = this.iReportService.getDataCampaign(report, jpaDynamicBean.getJndiName());

            if (data.stream().findFirst().isPresent()) {

                response = Response.ok()
                        .entity(data)
                        .build();
            } else {
                response = Response.ok().status(Response.Status.NO_CONTENT).build();
            }
        } catch (Exception ex) {
            response = Response.ok()
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }
        return response;
    }

    private ResponseReportDto responseDto(String typeRepor, String pathReport, HashMap<String, Object> parmeter, List<HashMap<String, Object>> data) {
        byte[] reporte = null;
        ResponseReportDto responseReportDto = new ResponseReportDto();
        switch (typeRepor) {
            case "PDF":
                reporte = this.iReportService.getReportExportPDF(pathReport, parmeter, data);
                responseReportDto.setContentType("application/pdf");
                break;
            case "XLS":
                reporte = this.iReportService.getReportExportXLS(pathReport, parmeter, data);
                responseReportDto.setContentType("application/vnd.ms-excel");
                break;
            case "CSV":
                reporte = this.iReportService.getReportExportCSV(pathReport, parmeter, data);
                responseReportDto.setContentType("application/x-csv");
                break;
            default:
                reporte = this.iReportService.getReportExportPDF(pathReport, parmeter, data);
                responseReportDto.setContentType("application/pdf");
                break;
        }
        responseReportDto.setReport(reporte);
        return responseReportDto;
    }
    
    /**
     * <p>method that List all CategorySchStatus in the database for campaing reports</p>
     *
     * @return {@javax.ws.rs.core.Response}
     */
    @GET
    @Path("/getCategorySchStatus/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL, Roles.CAMPAIGN, Roles.CAMPAIGN_GET})
    @DynamicJPA
    @Override
    @ApiOperation(value = "List all CategorySchStatus in the database",
            notes = "List all CategorySchStatus in the database for campaing reports",
            response = CategorySchStatus.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all CategorySchStatus Correctly")
    })
    public Response getAllCategorySchStatus() {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.iStatusSchemaServices.getAllCategorySchStatus(jpaDynamicBean.getJndiName()))
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
