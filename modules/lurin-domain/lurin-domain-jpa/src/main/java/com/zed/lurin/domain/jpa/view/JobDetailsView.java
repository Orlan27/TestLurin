package com.zed.lurin.domain.jpa.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>Entity that represents jobs interrupt</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "v_jobs_interrupt")
public class JobDetailsView {

    /**
     * View Id
     */
    @Id
    @Column(name = "id", nullable = false)
    private long code;

    /**
     * Job Name
     */
    @Column(name="job_name", nullable = false)
    private String jobName;

    /**
     * Job Group
     */
    @Column(name="job_group", nullable = false)
    private String jobGroup;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
}
