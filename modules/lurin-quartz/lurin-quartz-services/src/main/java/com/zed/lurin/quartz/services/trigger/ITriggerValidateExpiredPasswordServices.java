
package com.zed.lurin.quartz.services.trigger;

import org.quartz.Scheduler;

/**
 *
 * @author Llince
 */
public interface ITriggerValidateExpiredPasswordServices {
    /**
     *
     * method that creates the scheduled task that validates the Expired password
     *
     * @param scheduler
     */
    void createTriggerValidateExpiredPass(Scheduler scheduler);  
}
