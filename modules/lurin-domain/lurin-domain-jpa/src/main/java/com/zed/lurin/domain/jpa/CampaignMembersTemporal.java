package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 *
 * @author Francisco Lujano
 */
@Entity
@Table(name = "FV_CMP_TMP")
public class CampaignMembersTemporal {

    /**
     * Id
     */
    @Id
    @Column(name = "CMP_TMP_ID", nullable = false)
    @GenericGenerator(
            name = "fvCmpTmpSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "FV_CMP_TMP_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "fvCmpTmpSeqId")
    private long code;

    @Column(name = "member", nullable = false)
    private String member;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "CAMPAIGN_ID",
            referencedColumnName = "CAMPAIGN_ID",
            nullable = false
    )
    private Campaigns campaigns;


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public Campaigns getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Campaigns campaigns) {
        this.campaigns = campaigns;
    }
}
