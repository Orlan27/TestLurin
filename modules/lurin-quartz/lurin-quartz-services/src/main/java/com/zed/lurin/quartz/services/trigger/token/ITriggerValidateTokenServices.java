package com.zed.lurin.quartz.services.trigger.token;

import org.quartz.Scheduler;

public interface ITriggerValidateTokenServices {
    /**
     *
     * method that creates the scheduled task that validates the life of the tokens
     *
     * @param scheduler
     */
    void createTriggerValidateLiveToken(Scheduler scheduler);
}
