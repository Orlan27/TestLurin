package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 *
 * @author Llince
 * @author refactor Francisco Lujano
 */
@Entity
@Table(name = "FV_CAMPAIGN_MEMBERS")
public class CampaignMembers {

    /**
     * CampaignMembers Id
     */
    @Id
    @Column(name = "MEMBER_ID", nullable = false)
    @GenericGenerator(
            name = "campaignMembersSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "FV_CAMPAIGN_MEMBERS_SEQ"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "campaignMembersSeqId")
    long memberId;


    @Column(name = "CUSTOMER_ID", nullable = false)
    private long customerId;

    @Column(name = "smartcard", nullable = false)
    private String smartcard;

//    @ManyToOne(optional = false)
//    @JoinColumn(
//            name = "CAMPAIGN_ID",
//            referencedColumnName = "CAMPAIGN_ID",
//            nullable = false
//    )
//    private Campaigns campaigns;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CAMPAIGN_ID", nullable=false)
    private Campaigns campaigns;

    @Column(name = "provisioned", length = 2, nullable = false)
    private String provisioned;

    @Column(name = "error", length = 2, nullable = false)
    private String error;

    @Column(name = "ERROR_DETAIL", nullable = true)
    private String errorDetail;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getSmartcard() {
        return smartcard;
    }

    public void setSmartcard(String smartcard) {
        this.smartcard = smartcard;
    }

    public Campaigns getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Campaigns campaigns) {
        this.campaigns = campaigns;
    }

    public String getProvisioned() {
        return provisioned;
    }

    public void setProvisioned(String provisioned) {
        this.provisioned = provisioned;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
