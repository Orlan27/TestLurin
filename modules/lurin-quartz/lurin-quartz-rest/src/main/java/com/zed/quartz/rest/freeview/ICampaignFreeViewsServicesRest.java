package com.zed.quartz.rest.freeview;

import com.zed.lurin.quartz.dto.CampaignScheduler;
import com.zed.lurin.security.filters.services.Secured;
import com.zed.lurin.security.keys.Roles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface ICampaignFreeViewsServicesRest {
    /**
     * <p>method that creates a CAS activation or deactivation task</p>
     *
     * @param campaignScheduler
     * @return {@javax.ws.rs.core.Response}
     */
    @POST
    @Path("/create")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.CAMPAIGN, Roles.CAMPAIGN_CREATE})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response createTriggerFreeviewCampaign(CampaignScheduler campaignScheduler);

    /**
     * Method that updates an existing trigger and job
     * @param campaignScheduler
     */
    @POST
    @Path("/update")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.CAMPAIGN, Roles.CAMPAIGN_CREATE})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateTriggerFreeviewCampaign(CampaignScheduler campaignScheduler);

    /**
     * Method that deleting an existing trigger and job
     * @param campaignScheduler
     */
    @DELETE
    @Path("/delete")
    @Secured({Roles.ADMIN, Roles.ADMIN_LOCAL,Roles.CAMPAIGN, Roles.CAMPAIGN_CREATE})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response deleteTriggerFreeviewCampaign(CampaignScheduler campaignScheduler);
}
