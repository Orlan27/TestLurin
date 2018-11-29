package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author Llince
 */
@Entity
@Table(name = "FV_CAT_SCH_STATUS")
public class CategorySchStatus implements Serializable {

    /**
     * Status code
     */
    @Id
    @Column(name = "sch_status_id", nullable = false)
    @GenericGenerator(
            name = "catSchStsSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "FV_CAT_SCH_STATUS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "catSchStsSeqId")
    private long code;
    /**
     * Status
     */
    @Column(name = "SCH_STATUS", length = 3, nullable = false)
    private String status;
    /**
     * Name the Status
     */
    @Column(name = "name", nullable = false)
    private String name;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategorySchStatus{" + "id=" + code + ", schStatus=" + status + ", name=" + name + '}';
    }

    public enum STATUS{
        NEW{
            @Override
            public String toString() {
                return "N ";
            }
        },
        IN_VALIDATION{
            @Override
            public String toString() {
                return "I ";
            }
        },
        VALIDATE{
            @Override
            public String toString() {
                return "V ";
            }
        }
        ,
        IN_PROGRESS{
            @Override
            public String toString() {
                return "P ";
            }
        },
        COMPLETED{
            @Override
            public String toString() {
                return "T ";
            }
        },
        COMPLETED_WITH_ERROR{
            @Override
            public String toString() {
                return "W ";
            }
        },
        WITH_ERROR{
            @Override
            public String toString() {
                return "E ";
            }
        },
        CANCELED{
            @Override
            public String toString() {
                return "C ";
            }
        }
    }
}
