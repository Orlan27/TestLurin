package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Entity that represents a category status in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "FV_CAT_STATUS")
public class CategoryStatus implements Serializable {

    /**
     * Status id
     */
    @Id
    @Column(name = "status_id", nullable = false)
    @GenericGenerator(
            name = "catStsSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "FV_CAT_STATUS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "catStsSeqId")
    private long  code;

    /**
     * Name the Status
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * Status ABBR
     */
    @Column(name="status", length = 2, nullable = false)
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public enum STATUS{
        ACTIVE{
            @Override
            public String toString() {
                return "A";
            }
        },
        INACTIVE{
            @Override
            public String toString() {
                return "I";
            }
        }
    }
}


