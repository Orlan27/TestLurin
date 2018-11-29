/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zed.lurin.quartz.services.trigger.impl;

import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.quartz.keys.QuartzKeys;
import com.zed.lurin.quartz.services.jobs.ValidateUserExpiredPassJob;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.users.services.IUsersServices;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.apache.log4j.Logger;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import com.zed.lurin.quartz.services.trigger.ITriggerValidateExpiredPasswordServices;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 *
 * @author Llince
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TriggerValidateExpiredPasswordServicesImpl implements ITriggerValidateExpiredPasswordServices{
      /*
    /*
     *Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(TriggerValidateExpiredPasswordServicesImpl.class.getName());
    
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
    
    @Override
    public void createTriggerValidateExpiredPass(Scheduler scheduler) {
       try{

            LOGGER.debug("creating task to validate the expired password");

            final int intervalQuartz = this.parameterServices.getCoreParameterInteger(
                    Keys.QUARTZ_INTERVAL_TIME_PASS.toString());
             LOGGER.debug("intervalQuartz: "+intervalQuartz);

            // Trigger the job to run on the next round hour
            Trigger trigger = newTrigger()
                    .withIdentity(QuartzKeys.TRIGGER_VALIDATE_EXPIRED_PASS.toString(), QuartzKeys.SCHEDULER_GROUP_LURIN.toString())
                     .startNow()
                     .withSchedule(simpleSchedule()
                     .withIntervalInHours(intervalQuartz)
                     .repeatForever()) 
                     .build();
             // Define job instance
            JobDetail job1 = newJob(ValidateUserExpiredPassJob.class)
                    .withIdentity(QuartzKeys.JOB_VALIDATE_EXPIRED_PASS.toString(), QuartzKeys.SCHEDULER_GROUP_LURIN.toString())
                    .build();
         // Schedule the job with the trigger
            scheduler.scheduleJob(job1, trigger);
          }catch (SchedulerException e){
            LOGGER.warn(e.getMessage());
        }catch (Exception ex){
            LOGGER.error(String.format("Error creating the expired password validation program task [%s]",ex.getMessage()));
        }   
            
    }
    
}
