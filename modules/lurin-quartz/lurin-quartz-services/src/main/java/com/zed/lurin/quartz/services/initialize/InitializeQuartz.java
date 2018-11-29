package com.zed.lurin.quartz.services.initialize;

import com.zed.lurin.quartz.services.trigger.token.ITriggerValidateTokenServices;
import com.zed.lurin.security.keys.Keys;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.List;
import com.zed.lurin.quartz.services.trigger.ITriggerValidateExpiredPasswordServices;


@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class InitializeQuartz {

    /*
    /*
     *Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(InitializeQuartz.class.getName());

    /**
     * Service validate token scheduler
     */
    @EJB
    ITriggerValidateTokenServices triggerValidateTokenServices;
      /**
     * Service validate expired password scheduler
     */
    @EJB
    ITriggerValidateExpiredPasswordServices triggerValidateExpiredPasswordServices;


    @PostConstruct
    public void init(){

        LOGGER.debug("Initializing all persisted quartz in database");
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler= sf.getScheduler();
            scheduler.start();
            this.triggerValidateTokenServices.createTriggerValidateLiveToken(scheduler);
            this.triggerValidateExpiredPasswordServices.createTriggerValidateExpiredPass(scheduler);

        }catch (Exception e){
           LOGGER.error(String.format("Error initializing quartz [%s]", e.getMessage()));
        }
    }

}
