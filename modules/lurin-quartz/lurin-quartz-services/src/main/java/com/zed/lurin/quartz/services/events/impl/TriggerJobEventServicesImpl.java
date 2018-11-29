package com.zed.lurin.quartz.services.events.impl;

import com.zed.lurin.domain.jpa.view.JobDetailsView;
import com.zed.lurin.quartz.services.events.ITriggerJobsEventServices;
import org.apache.log4j.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Stateless
public class TriggerJobEventServicesImpl implements ITriggerJobsEventServices {

    /*
    /*
     *Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(TriggerJobEventServicesImpl.class.getName());

    /**
     * Entity Manager reference.
     */
    @PersistenceContext(unitName="lurin-admin-em")
    private EntityManager em;


    /**
     * method that changes to interrunmpido a job
     * @param jobName
     */
    @Override
    public void interruptedJob(String jobName){
        Query query = this.em.createNativeQuery("CALL INTERRUPT_JOB_FOR_NAME(:jobName)");
        query.setParameter("jobName", jobName);
        query.executeUpdate();
        return;
    }

    /**
     * method to return inter-row jobs by name
     * @param jobName
     * @return
     */
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
