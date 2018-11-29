package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>Entity that represents a provisionings in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "PS_PROVISIONINGS")
public class Provisioning {

    /**
     * provisioning id
     */
    @Id
    @Column(name = "PROVISIONING_ID", nullable = false)
    @GenericGenerator(
            name = "provisioningsSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "PS_PROVISIONINGS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "provisioningsSeqId")
    private long  code;

    /**
     * command send to cas
     */
    @Column(name="PROVISIONING_COMMAND", nullable = false)
    private String command;


    /**
     * date init scheduler the provisionings
     */
    @Column(name="DATE_INI_SCHEDULE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInit;

    /**
     * date init scheduler the provisionings
     */
    @Column(name="DATE_FIN_SCHEDULE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;

    /**
     * carriers
     */
    @Column(name="CARRIERS_ID", nullable = false)
    private long carriersId;

    /**
     * members campaigns
     */
    @Column(name="member_id", nullable = false)
    private long memberId;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Date getDateInit() {
        return dateInit;
    }

    public void setDateInit(Date dateInit) {
        this.dateInit = dateInit;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public long getCarriersId() {
        return carriersId;
    }

    public void setCarriersId(long carriersId) {
        this.carriersId = carriersId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
}


