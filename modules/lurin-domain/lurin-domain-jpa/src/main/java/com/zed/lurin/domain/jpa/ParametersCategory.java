package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a category parameters in database</p>
 * @author 
 */
@Entity
@Table(name = "CAT_PARAM_CATEGORIES")
public class ParametersCategory {

    /**
     * Status id
     */
    @Id
    @Column(name = "category_id", nullable = false)
    @GenericGenerator(
            name = "catpcSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CAT_PARAM_CATEGORIES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "catpcSeqId")
    private long  code;

    /**
     * Name the Status
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


