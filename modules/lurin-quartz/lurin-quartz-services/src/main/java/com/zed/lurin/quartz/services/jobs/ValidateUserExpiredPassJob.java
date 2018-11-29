
package com.zed.lurin.quartz.services.jobs;

import com.zed.lurin.security.users.services.IUsersServices;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Llince
 */
public class ValidateUserExpiredPassJob implements Job {

    /*
     *Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(ValidateUserExpiredPassJob.class.getName());
    /**
     * User Services
     */
    private IUsersServices usersServices;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.debug("RUN QUARTZ TO  VALID EXPIRED PASSWORD");
        this.initialized();
        this.usersServices.validateExpirationUsersPassword();
    }

    /**
     * Initialize ejbs
     */
    private void initialized(){
        try {
            this.usersServices = (IUsersServices)
                    new InitialContext().lookup(IUsersServices.ejbMappedName);

        }catch (NamingException e){
            LOGGER.error(e.getMessage());
        }
    }

}
