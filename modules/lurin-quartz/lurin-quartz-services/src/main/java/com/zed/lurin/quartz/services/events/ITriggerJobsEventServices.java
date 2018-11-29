package com.zed.lurin.quartz.services.events;

import com.zed.lurin.domain.jpa.view.JobDetailsView;
import com.zed.lurin.quartz.dto.CampaignScheduler;

import java.util.List;

public interface ITriggerJobsEventServices {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.quartz.services/TriggerJobEventServicesImpl!com.zed.lurin.quartz.services.events.ITriggerJobsEventServices";

    /**
     * method that changes to interrunmpido a job
     * @param jobName
     */
    void interruptedJob(String jobName);

    /**
     * method to return inter-row jobs by name
     * @param jobName
     * @return
     */
    List<JobDetailsView> getJobsInterrupt(String jobName);
}
