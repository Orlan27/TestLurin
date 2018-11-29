package com.zed.lurin.quartz.services.jobs.freeview;

import com.zed.carriers.services.ICarriersServices;
import com.zed.lurin.domain.jpa.Campaigns;
import com.zed.lurin.domain.jpa.view.JobDetailsView;
import com.zed.lurin.mdb.api.queue.IMessageQueue;
import com.zed.lurin.quartz.keys.QuartzKeys;
import com.zed.lurin.quartz.services.events.ITriggerJobsEventServices;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

public class ValidateEventCampaignJob implements Job {

    /*
     *Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(ValidateEventCampaignJob.class.getName());

    /**
     * Message Queue
     */
    private IMessageQueue messageQueue;

    /**
     * Trigger Campaign Services
     */
    private ITriggerJobsEventServices triggerJobsEventServices;

    /**
     *
     */
    private ICarriersServices iCarriersServices;



    /**
     *
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        this.initialized();

        JobDataMap map = context.getMergedJobDataMap();

        LOGGER.debug(String.format("[JOB] Validating Event Job Name %s", context.getJobDetail().getKey().getName()));
        List<JobDetailsView> jobDetailsViews =  this.triggerJobsEventServices.getJobsInterrupt(
                context.getJobDetail().getKey().getName());



        Campaigns campaigns = (Campaigns) map.get(QuartzKeys.CAMPAING.toString());
        List<Long> campaignMember = (List<Long>) map.get(QuartzKeys.CAMPAING_MEMBER.toString());
        String email =(String) map.get(QuartzKeys.EMAIL.toString());
        String jndiName = (String) map.get(QuartzKeys.JNDI_NAME.toString());


        if(!jobDetailsViews.stream().findFirst().isPresent()){
            LOGGER.debug(String.format("[JOB][%s] firing validation and creating triggers ID CAMPAIGN[%s]",
                    ValidateEventCampaignJob.class.getName(), campaigns.getCampaignId()));


        }else{
            LOGGER.debug( String.format("[JOB] Job Name %s cancellated ",context.getJobDetail().getKey().getName()));
        }






    }


    /**
     * Initialize ejbs
     */
    private void initialized(){
        try {
            this.messageQueue = (IMessageQueue)
                    new InitialContext().lookup(IMessageQueue.ejbMappedName);

            this.triggerJobsEventServices = (ITriggerJobsEventServices) new InitialContext()
                    .lookup(ITriggerJobsEventServices.ejbMappedName);

            this.iCarriersServices = (ICarriersServices) new InitialContext()
                    .lookup(ICarriersServices.ejbMappedName);

        }catch (NamingException e){
            LOGGER.error(e.getMessage());
        }
    }

}
