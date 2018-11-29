package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a Companies in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "COMPANIES")
public class Companies {

    /**
     * Companies Id
     */
    @Id
    @Column(name = "COMPANY_ID", nullable = false)
    @GenericGenerator(
            name = "companiesSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "COMPANIES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "companiesSeqId")
    private long code;

    /**
     * #########################
     * Pending Table id Entity
     * #########################
     */

    /**
     * name
     */
    @Column(name="name", nullable = false)
    private String name;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
