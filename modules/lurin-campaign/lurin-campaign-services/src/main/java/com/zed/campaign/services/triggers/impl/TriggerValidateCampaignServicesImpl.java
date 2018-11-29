package com.zed.campaign.services.triggers.impl;

import com.zed.campaign.services.job.CreateFreeviewCampaignJob;
import com.zed.campaign.services.job.ValidateEventCampaignJob;
import com.zed.campaign.services.triggers.ITriggerValidateCampaignServices;
import com.zed.lurin.domain.jpa.Campaigns;
import com.zed.lurin.domain.jpa.view.JobDetailsView;
import com.zed.lurin.domain.services.ISecurityStatusServices;
import com.zed.lurin.mdb.api.queue.IMessageQueue;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.quartz.dto.CampaignScheduler;
import com.zed.lurin.quartz.keys.QuartzKeys;
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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TriggerValidateCampaignServicesImpl implements ITriggerValidateCampaignServices {

    /*
    /*
     *Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(TriggerValidateCampaignServicesImpl.class.getName());

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
     *
     * @return
     */
    @Override
    public BigDecimal maxDateTriggerJobs(){

        Query query =
                this.em.createNativeQuery("SELECT MAX(START_TIME) FROM QRTZ_TRIGGERS");

        BigDecimal dateMax = (BigDecimal) query.getSingleResult();

        return dateMax;
    }


    /**
     *
     * method that creates a CAS activation or deactivation task
     *
     * @param campaignScheduler
     */
    @Override
    public void createTriggerFreeviewCampaign(CampaignScheduler campaignScheduler){
        try{

            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler= sf.getScheduler();

            LOGGER.debug(String.format("Creating trigger for Campaign CAS command %s", campaignScheduler.getType().toString().toLowerCase()));

            Trigger triggerNewCampaign = newTriggerBuild(campaignScheduler);

            //Data mapDataJob
            JobDataMap mapDataJob = getJobDataMap(campaignScheduler);

            // Define job instance
            JobDetail jobCreateFreeviewCampaignJob = newJobBuild(campaignScheduler, mapDataJob);

            // Schedule the job with the trigger
            scheduler.scheduleJob(jobCreateFreeviewCampaignJob, triggerNewCampaign);
        }catch (Exception e){
            LOGGER.error(String.format(String.format("Error creating trigger for Campaign CAS command %s [%s]",
                    campaignScheduler.getType().toString().toLowerCase(),
                    e.getMessage())));
        }

    }

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
    @Override
    public void createValidateTriggerFreeviewCampaign(Campaigns campaigns, List<Long> campaignMember ,String email,
                                                      String jndiName ,String nameTrigger,String nameJob, Date startAt){
        try{

            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler= sf.getScheduler();

            LOGGER.debug(String.format("Creating trigger for Campaign CAS command %s", campaigns.getCampaignId()));

            Trigger triggerNewCampaign = newTrigger()
                    .withIdentity(nameTrigger, QuartzKeys.SCHEDULER_GROUP_CAS.toString())
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                    .startAt(startAt)
                    .build();

            //Data mapDataJob
            JobDataMap mapDataJob = new JobDataMap();
            mapDataJob.put(QuartzKeys.CAMPAING.toString(), campaigns);
            mapDataJob.put(QuartzKeys.CAMPAING_MEMBER.toString(), campaignMember);
            mapDataJob.put(QuartzKeys.EMAIL.toString(), email);
            mapDataJob.put(QuartzKeys.JNDI_NAME.toString(),jndiName);

            // Define job instance
            JobDetail jobCreateFreeviewCampaignJob = newJob(ValidateEventCampaignJob.class)
                    .withIdentity(nameJob, QuartzKeys.SCHEDULER_GROUP_CAS.toString())
                    .setJobData(mapDataJob)
                    .build();

            // Schedule the job with the trigger
            scheduler.scheduleJob(jobCreateFreeviewCampaignJob, triggerNewCampaign);
        }catch (Exception e){
            LOGGER.error(String.format(String.format("Error creating trigger for Campaign CAS command %s [%s]",
                    campaigns.getCampaignId(),
                    e.getMessage())));
        }

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
        mapDataJob.put(QuartzKeys.INIT_BATCH.toString(), campaignScheduler.getInitBatch());
        mapDataJob.put(QuartzKeys.END_BATCH.toString(), campaignScheduler.getEndBatch());

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

    @Override
    public List<JobDetailsView> getJobsInterrupt(String jobName){
        TypedQuery<JobDetailsView> query =
                this.em.createQuery("SELECT jdv " +
                        "FROM JobDetailsView jdv " +
                        "WHERE jdv.jobName = :jobName ", JobDetailsView.class);

        query.setParameter("jobName",jobName);

        return query.getResultList();

    }

}
