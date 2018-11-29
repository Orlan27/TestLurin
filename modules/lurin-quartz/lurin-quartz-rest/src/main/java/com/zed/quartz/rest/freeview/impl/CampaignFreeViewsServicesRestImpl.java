package com.zed.quartz.rest.freeview.impl;

import com.zed.lurin.quartz.dto.CampaignScheduler;
import com.zed.lurin.quartz.services.trigger.freeview.ITriggerFreeviewCampaignServices;
import com.zed.quartz.rest.freeview.ICampaignFreeViewsServicesRest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


/**
 * <p>Stateless REST where the methods that manage the scheduled tasks of campaigns are implemented</p>
 * @author Francisco Lujano
 * {@link ICampaignFreeViewsServicesRest}
 */
@Path("/task/campaign")
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CampaignFreeViewsServicesRestImpl implements ICampaignFreeViewsServicesRest {

    @EJB
    ITriggerFreeviewCampaignServices triggerFreeviewCampaingServices;

    /**
     * <p>method that creates a CAS activation or deactivation task</p>
     *
     * @param campaignScheduler
     * @return {@javax.ws.rs.core.Response}
     */
    @Override
    public Response createTriggerFreeviewCampaign(CampaignScheduler campaignScheduler) {
        Response response;

        try {
//            this.triggerFreeviewCampaingServices.createTriggerFreeviewCampaign(campaignScheduler);
            response = Response.ok().build();
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
     * Method that updates an existing trigger and job
     * @param campaignScheduler
     */
    @Override
    public Response updateTriggerFreeviewCampaign(CampaignScheduler campaignScheduler){
        Response response;

        try {
            this.triggerFreeviewCampaingServices.updateTriggerFreeviewCampaign(campaignScheduler);
            response = Response.ok().build();
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
     * Method that deleting an existing trigger and job
     * @param campaignScheduler
     */
    @Override
    public Response deleteTriggerFreeviewCampaign(CampaignScheduler campaignScheduler){
        Response response;

        try {
            this.triggerFreeviewCampaingServices.deleteTriggerFreeviewCampaign(campaignScheduler);
            response = Response.ok().build();
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
