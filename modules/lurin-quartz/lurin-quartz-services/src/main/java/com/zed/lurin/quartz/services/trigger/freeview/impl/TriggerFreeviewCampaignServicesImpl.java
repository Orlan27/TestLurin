package com.zed.lurin.quartz.services.trigger.freeview.impl;

import com.zed.lurin.domain.services.ISecurityStatusServices;
import com.zed.lurin.mdb.api.queue.IMessageQueue;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.quartz.dto.CampaignScheduler;
import com.zed.lurin.quartz.keys.QuartzKeys;
import com.zed.lurin.quartz.services.events.ITriggerJobsEventServices;
import com.zed.lurin.quartz.services.jobs.freeview.CreateFreeviewCampaignJob;
import com.zed.lurin.quartz.services.trigger.freeview.ITriggerFreeviewCampaignServices;
import com.zed.lurin.security.users.services.IUsersServices;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Deprecated
public class TriggerFreeviewCampaignServicesImpl implements ITriggerFreeviewCampaignServices {

    /*
    /*
     *Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(TriggerFreeviewCampaignServicesImpl.class.getName());

    /**
     * Entity Manager reference.
     */
    @PersistenceContext(unitName="lurin-admin-em")
    private EntityManager em;

    /**
     * Core Parameter Services
     */
    @EJB
    ICoreParameterServices parameterServices;

    /**
     * User Services
     */
    @EJB
    IUsersServices usersServices;

    /**
     * Message Queue
     */
    @EJB
    IMessageQueue messageQueue;

    /**
     * Security Status Services
     */
    @EJB
    ISecurityStatusServices securityStatusServices;

    /**
     * Service event trigger and jobs
     */
    @EJB
    ITriggerJobsEventServices triggerJobsEventServices;


    /**
     * Method that updates an existing trigger and job
     * @param campaignScheduler
     */
    @Override
    public void updateTriggerFreeviewCampaign(CampaignScheduler campaignScheduler){
        try{
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler= sf.getScheduler();

            this.updateTrigger(campaignScheduler, scheduler);
            LOGGER.debug(String.format("Updating the %s trigger ", campaignScheduler.getNameTrigger()));

            this.updateJob(campaignScheduler, scheduler);
            LOGGER.debug(String.format("Updating the %s Job ", campaignScheduler.getNameJob()));

        }catch (Exception e){
            LOGGER.error(String.format("error when updating the data of trigger %s and of the job %s [%s]",
                    campaignScheduler.getNameTrigger(), campaignScheduler.getNameJob(), e.getMessage()));
            e.printStackTrace();
        }

    }


    /**
     * Method that deleting an existing trigger and job
     * @param campaignScheduler
     */
    @Override
    public void deleteTriggerFreeviewCampaign(CampaignScheduler campaignScheduler){
        try{
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler= sf.getScheduler();

            scheduler.unscheduleJob(TriggerKey.triggerKey(campaignScheduler.getNameTrigger()));
            LOGGER.debug(String.format("Delete %s trigger ", campaignScheduler.getNameTrigger()));


            this.triggerJobsEventServices.interruptedJob(campaignScheduler.getNameJob());
            scheduler.deleteJob(JobKey.jobKey(campaignScheduler.getNameJob()));
            LOGGER.debug(String.format("Delete %s Job ", campaignScheduler.getNameJob()));

        }catch (Exception e){
            LOGGER.error(String.format("error when deleting the data of trigger %s and of the job %s [%s]",
                    campaignScheduler.getNameTrigger(), campaignScheduler.getNameJob(), e.getMessage()));
            e.printStackTrace();
        }
    }



    /**
     * method that updates a job
     *
     * @param campaignScheduler
     * @param scheduler
     * @throws SchedulerException
     */
    private void updateJob(CampaignScheduler campaignScheduler, Scheduler scheduler) throws SchedulerException {
        //Data mapDataJob update
        JobDataMap mapDataJob = getJobDataMap(campaignScheduler);

        // Define job instance
        JobDetail jobUpdateFreeviewCampaignJob = newJobBuild(campaignScheduler, mapDataJob);

        // store, and set overwrite flag to 'true'
        scheduler.addJob(jobUpdateFreeviewCampaignJob, true);
    }

    /**
     * method that updates a trigger
     * @param campaignScheduler
     * @param scheduler
     * @throws SchedulerException
     */
    private void updateTrigger(CampaignScheduler campaignScheduler, Scheduler scheduler) throws SchedulerException {
        Trigger triggerUpdateCampaign = newTriggerBuild(campaignScheduler);

        // tell the scheduler to remove the old trigger with the given key, and put the new one in its place
        scheduler.rescheduleJob(TriggerKey.triggerKey(campaignScheduler.getNameTrigger()), triggerUpdateCampaign);
    }

    /**
     * common method to define a data map
     * @param campaignScheduler
     * @return
     */
    private JobDataMap getJobDataMap(CampaignScheduler campaignScheduler) {
        JobDataMap mapDataJob = new JobDataMap();
        mapDataJob.put(QuartzKeys.CAMPAING_TYPE.toString(), campaignScheduler.getType().toString());
        mapDataJob.put(QuartzKeys.CAMPAING.toString(), campaignScheduler.getCampaingId());
        mapDataJob.put(QuartzKeys.CAMPAING_MEMBER.toString(), campaignScheduler.getCampaingMember());
        mapDataJob.put(QuartzKeys.CARRIER_ID.toString(), campaignScheduler.getCarrierId());
        mapDataJob.put(QuartzKeys.IP_SERVER_CAS.toString(), campaignScheduler.getIpServerCas());
        mapDataJob.put(QuartzKeys.PORT_SERVER_CAS.toString(), campaignScheduler.getPortServerCas());
        mapDataJob.put(QuartzKeys.PORT_SERVER_CAS2.toString(), campaignScheduler.getPortServerCas2());
        mapDataJob.put(QuartzKeys.PORT_SERVER_CAS3.toString(), campaignScheduler.getPortServerCas3());

        return mapDataJob;
    }

    /**
     * method that creates a trigger
     * @param campaignScheduler
     * @return
     */
    private Trigger newTriggerBuild(CampaignScheduler campaignScheduler) {
        return newTrigger()
                .withIdentity(campaignScheduler.getNameTrigger(), QuartzKeys.SCHEDULER_GROUP_CAS.toString())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .startAt(campaignScheduler.getStartAT())
                .build();
    }

    /**
     * method that creates a job
     * @param campaignScheduler
     * @param mapDataJob
     * @return
     */
    private JobDetail newJobBuild(CampaignScheduler campaignScheduler, JobDataMap mapDataJob) {
        return newJob(CreateFreeviewCampaignJob.class)
                .withIdentity(campaignScheduler.getNameJob(), QuartzKeys.SCHEDULER_GROUP_CAS.toString())
                .setJobData(mapDataJob)
                .build();
    }

}
