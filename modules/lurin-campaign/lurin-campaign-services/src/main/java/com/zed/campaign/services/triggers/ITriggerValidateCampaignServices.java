package com.zed.campaign.services.triggers;

import com.zed.lurin.domain.jpa.Campaigns;
import com.zed.lurin.domain.jpa.view.JobDetailsView;
import com.zed.lurin.quartz.dto.CampaignScheduler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public interface ITriggerValidateCampaignServices {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.campaign.services/TriggerValidateCampaignServicesImpl!com.zed.campaign.services.triggers.ITriggerValidateCampaignServices";

    /**
     *
     * @param campaigns
     * @param campaignMember
     * @param email
     * @param jndiName
     * @param nameTrigger
     * @param nameJob
     * @param startAt
     */
    void createValidateTriggerFreeviewCampaign(Campaigns campaigns, List<Long> campaignMember, String email,
                                               String jndiName, String nameTrigger, String nameJob, Date startAt);

    /**
     *
     * method that creates a CAS activation or deactivation task
     *
     * @param campaignScheduler
     */
    void createTriggerFreeviewCampaign(CampaignScheduler campaignScheduler);



    /**
     * method to return inter-row jobs by name
     * @param jobName
     * @return
     */
    List<JobDetailsView> getJobsInterrupt(String jobName);

    /**
     * method return max date set
     * @return
     */
    BigDecimal maxDateTriggerJobs();
}
