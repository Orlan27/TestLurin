package com.zed.lurin.quartz.services.jobs.token;

import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.users.services.IUsersServices;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.naming.InitialContext;
import java.sql.Timestamp;
import java.util.Date;

public class UserControlValidateLiveTokenJob implements Job {

    /*
     *Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(UserControlValidateLiveTokenJob.class.getName());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Timestamp now = new Timestamp((new Date()).getTime());
        LOGGER.debug( String.format("Run quartz to validate active tokens already expired %s",now));
        JobDataMap map = context.getMergedJobDataMap();
        try {
            IUsersServices usersServices = (IUsersServices)
                    new InitialContext().lookup(IUsersServices.ejbMappedName);

            long codeActiveStatus =  (Long) map.get(Keys.CODE_ACTIVE_STATUS.toString());
            usersServices.setUserControlAccessInvalidate(now, codeActiveStatus);
        }catch (Exception e){
            LOGGER.error(e);
        }

    }

}