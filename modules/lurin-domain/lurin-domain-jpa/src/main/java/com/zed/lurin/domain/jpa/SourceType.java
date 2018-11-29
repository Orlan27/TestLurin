package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents the types of Data Sources that the system handles</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "CAT_SOURCE_TYPE")
public class SourceType {

    /**
     * Type id
     */
    @Id
    @Column(name = "SOURCE_TYPE_ID", nullable = false)
    @GenericGenerator(
            name = "cstSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CAT_SOURCE_TYPE_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "cstSeqId")
    private Long sourceTypeId;

    /**
     * Name of Data Sources type
     */
    @Column(name="NAME", nullable = false)
    private String name;

    /**
     * Data source type prefix
     */
    @Column(name="SOURCE_TYPE", length = 1, nullable = false)
    private String sourceType;

    public Long getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(Long sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public enum TYPE{
        JDBC{
            @Override
            public String toString() {
                return "J";
            }
        },
        TCP{
            @Override
            public String toString() {
                return "T";
            }
        },
        JNDI{
            @Override
            public String toString() {
                return "N";
            }
        }
    }
}


