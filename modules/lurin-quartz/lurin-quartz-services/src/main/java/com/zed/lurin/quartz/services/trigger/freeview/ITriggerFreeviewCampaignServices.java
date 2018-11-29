package com.zed.lurin.quartz.services.trigger.freeview;

import com.zed.lurin.quartz.dto.CampaignScheduler;



@Deprecated
public interface ITriggerFreeviewCampaignServices {
    /**
     * Method that updates an existing trigger and job
     * @param campaignScheduler
     */
    void updateTriggerFreeviewCampaign(CampaignScheduler campaignScheduler);

    /**
     * Method that deleting an existing trigger and job
     * @param campaignScheduler
     */
    void deleteTriggerFreeviewCampaign(CampaignScheduler campaignScheduler);


}
