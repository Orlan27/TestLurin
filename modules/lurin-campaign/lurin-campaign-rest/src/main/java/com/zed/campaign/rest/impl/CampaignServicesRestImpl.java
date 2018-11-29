package com.zed.campaign.rest.impl;


import com.zed.campaign.rest.ICampaingServicesRest;
import com.zed.campaign.rest.keys.ResponseCasCustom;
import com.zed.campaign.services.ICampaignsServices;
import com.zed.campaign.services.entity.CampaignEntity;
import com.zed.lurin.domain.jpa.*;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.security.filters.services.DynamicJPA;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.filters.services.bean.JpaDynamicBean;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.keys.Roles;
import com.zed.lurin.security.users.services.IUsersServices;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

/**
 * <p>Stateless REST where the methods that manage the campaign are implemented</p>
 * @author Francisco Lujano
 * {@link }
 */
@Path("/campaign")
@Stateless
@Api(
        basePath = "/api/rest/",
        value = "/campaign", description = "Operation to manage campaign",
        authorizations = {
                @Authorization(value = "bearer_token")
        })
public class CampaignServicesRestImpl implements ICampaingServicesRest {

    private static Logger LOGGER = Logger.getLogger(CampaignServicesRestImpl.class.getName());

    @EJB
    ICoreParameterServices iCoreParameterServices;

    @EJB
    ICampaignsServices campaignsServices;

    @Inject
    JpaDynamicBean jpaDynamicBean;

    @EJB
    IUsersServices iUsersServices;

    @EJB
    ICampaignsServices iCampaignsServices;

    /**
     * <p>method that creates a campaigns</p>
     *
     * @param campaignEntity
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.CAMPAIGN, Roles.CAMPAIGN_CREATE})
    @DynamicJPA
    @ApiOperation(value = "Create Campaigns",
            notes = "This method creates a campaign and if you create the campaign correctly it returns the generated id",
            response = CampaignEntity.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "Create Campaign Correctly")
    })
    public Response createCampaigns(
            @HeaderParam(value = "Authorization") String token,
            @ApiParam(value = "Multipart Create Campaigns object", required = true)
            @MultipartForm CampaignEntity campaignEntity) {
        Response response;


        String urlUploadFile = this.iCoreParameterServices.getCoreParameterString(Keys.UPLOAD_URL_FILE.toString());

        final File rootFile = new File(urlUploadFile);
        if (!rootFile.exists()) {

            boolean res = rootFile.mkdirs();
            if (!res) {
                LOGGER.error( String.format("Couldn't create the file upload directory: %s",rootFile.getAbsolutePath()));
            }
        }


        token = token.substring(Keys.AUTHENTICATION_SCHEME.toString().length()).trim();

        List<UsersControlAccess> usersControlAccesses = this.iUsersServices.getUserControlAccessByToken(token);
        String email = null;
        if(usersControlAccesses.stream().findFirst().isPresent()) {
            final Users user = usersControlAccesses.stream().findFirst().get().getUser();
            email =  user.getEmail();
        }

        Date date = new Date();

        File fileFinal = new File(rootFile, String.valueOf(date.getTime())+".csv");

        boolean isFileCopied=false;
        try (FileOutputStream fos = new FileOutputStream(fileFinal)){
            byte[] filebytes = campaignEntity.getFile();
            fos.write(filebytes);
            isFileCopied=true;
        }catch (Exception e){
            LOGGER.error( String.format("File copy error %s",e.getMessage()));
        }


        try {
            if(!this.campaignsServices.isValidateNumberRows(fileFinal.getAbsolutePath())){
                response = Response.ok()
                        .entity(ResponseCasCustom.NUMBERS_ROWS_EXCEED)
                        .status(Response.Status.BAD_REQUEST).build();
                return response;
            }
            if(isFileCopied) {

                Campaigns campaigns = getCampaignsToEntityForm(campaignEntity, fileFinal);
                this.campaignsServices.createCampaigns(campaigns, jpaDynamicBean.getJndiName(), false);
                response = Response.ok()
                        .entity(campaigns.getCampaignId())
                        .build();

                final String emailFinal = email;
                Runnable bulkAndValidateAction = new Runnable(){

                    public void run(){

                        iCampaignsServices.processValidationsAndTriggerEvents(campaigns, jpaDynamicBean.getJndiName(),
                                emailFinal, campaignEntity.getValidateBefore(), campaignEntity.getPriority());

                    }
                };

                Thread thread = new Thread(bulkAndValidateAction);
                thread.start();

            }else{
                response = Response.ok()
                        .status(Response.Status.NO_CONTENT).build();
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
     * method that returns the list of campaign
     *
     * @return
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.CAMPAIGN, Roles.CAMPAIGN_GET})
    @DynamicJPA
    @Override
    @ApiOperation(value = "List all Campaign in the database",
            notes = "List all Campaign in the database for Campaign",
            response = Campaigns.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "no token authorization or invalid permits"),
            @ApiResponse(code = 200, message = "List all Campaign Correctly")
    })
    public Response getCampaigns() {
        Response response;
        try {
            response = Response.ok()
                    .entity(this.campaignsServices.getCampaigns(jpaDynamicBean.getJndiName()))
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
     * not very elegant but functional method to create object campaign
     * @param campaignEntity
     * @param fileFinal
     * @return
     */
    private Campaigns getCampaignsToEntityForm(CampaignEntity campaignEntity, File fileFinal) throws ParseException {
        Campaigns campaigns = new Campaigns();
        campaigns.setName(campaignEntity.getName());

        CommercialZone commercialZone = new CommercialZone();
        commercialZone.setZone(campaignEntity.getZoneId());
        campaigns.setCommercialZone(commercialZone);

        TypeLoad typeLoad= new TypeLoad();
        typeLoad.setCode(campaignEntity.getTypeLoadId());
        campaigns.setTypeLoad(typeLoad);

        FreeView freeView =  new FreeView();
        freeView.setFreeViewId(campaignEntity.getFreeviewId());
        campaigns.setFreeViewId(freeView);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        campaigns.setDateIniSchedule(formatter.parse(campaignEntity.getDateIniSchedule()));
        campaigns.setDateFinSchedule(formatter.parse(campaignEntity.getDateFinSchedule()));

        campaigns.setFileName(fileFinal.getAbsolutePath());

//        Messages messagesInit =  new Messages();
//        messagesInit.setCode(campaignEntity.getInitialMessage());
//        campaigns.setMessagesInitial(messagesInit);
//
//        Messages messagesFin =  new Messages();
//        messagesFin.setCode(campaignEntity.getFinalMessage());
//        campaigns.setMessagesFinal(messagesFin);

        campaigns.setPriority(campaignEntity.getPriority());
        campaigns.setValidateBefore(campaignEntity.getValidateBefore());
        campaigns.setUserCreate(campaignEntity.getUserCreate());

        campaigns.setCarrierId(campaignEntity.getCarrierId());
        return campaigns;
    }


}
