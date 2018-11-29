package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a datasource connection type in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "CAT_CONN_TYPE")
public class ConnectionType {

    /**
     * Type id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(
            name = "connTypeSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CAT_CONN_TYPE_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "connTypeSeqId")
    private String  code;

    /**
     * Name the connection type
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * Status
     */
    @Column(name="status", length = 2, nullable = false)
    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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


