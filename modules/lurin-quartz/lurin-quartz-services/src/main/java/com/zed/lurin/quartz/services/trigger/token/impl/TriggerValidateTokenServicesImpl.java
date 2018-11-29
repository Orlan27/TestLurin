package com.zed.lurin.quartz.services.trigger.token.impl;

import com.zed.lurin.domain.jpa.SecurityCategoryStatus;
import com.zed.lurin.domain.services.ISecurityStatusServices;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.quartz.keys.QuartzKeys;
import com.zed.lurin.quartz.services.jobs.token.UserControlValidateLiveTokenJob;
import com.zed.lurin.quartz.services.trigger.token.ITriggerValidateTokenServices;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.users.services.IUsersServices;
import org.quartz.*;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TriggerValidateTokenServicesImpl implements ITriggerValidateTokenServices {

    /*
    /*
     *Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(TriggerValidateTokenServicesImpl.class.getName());

    /**
     * Core Parameter Services
     */
    @EJB
    private ICoreParameterServices parameterServices;

    /**
     * User Services
     */
    @EJB
    private IUsersServices usersServices;

    /**
     * Security Status Services
     */
    @EJB
    private ISecurityStatusServices securityStatusServices;

    /**
     *
     * method that creates the scheduled task that validates the life of the tokens
     *
     * @param scheduler
     */
    @Override
    public void createTriggerValidateLiveToken(Scheduler scheduler){
        try{

            LOGGER.debug("creating task to validate the life of the tokens");

            final int intervalQuartz = this.parameterServices.getCoreParameterInteger(
                    Keys.QUARTZ_INTERVAL_TIME.toString());

            final String cronjobDefine = String.format("0 0/%s * * * ?",intervalQuartz);

            // Trigger the job to run on the next round minute
            Trigger trigger = newTrigger()
                    .withIdentity(QuartzKeys.TRIGGER_VALIDATE_TOKEN.toString(), QuartzKeys.SCHEDULER_GROUP_LURIN.toString())
                    .startNow()
                    .withSchedule(cronSchedule(cronjobDefine))
                    .build();


            SecurityCategoryStatus securityCategoryStatus = this.securityStatusServices.getCategoryActive();
            //Data map
            JobDataMap map = new JobDataMap();
            map.put(Keys.CODE_ACTIVE_STATUS.toString(), securityCategoryStatus.getCode());

            // Define job instance
            JobDetail job1 = newJob(UserControlValidateLiveTokenJob.class)
                    .withIdentity(QuartzKeys.JOB_VALIDATE_TOKEN.toString(), QuartzKeys.SCHEDULER_GROUP_LURIN.toString())
                    .setJobData(map)
                    .build();

            // Schedule the job with the trigger
            scheduler.scheduleJob(job1, trigger);
        }catch (SchedulerException e){
            LOGGER.warn(e.getMessage());
        }catch (Exception e){
            LOGGER.error(String.format("Error creating the token validation program task [%s]",e.getMessage()));
        }

    }

}
