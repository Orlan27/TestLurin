package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a core parameter in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "CORE_PARAMETERS")
public class CoreParameter {

    /**
     * core parameters id
     */
    @Id
    @Column(name = "CORE_PARAMETERS_ID", nullable = false)
    @GenericGenerator(
            name = "coreParamSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CORE_PARAMETERS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "coreParamSeqId")
    private String  code;

    /**
     * Key parameters
     */
    @Column(name="key", nullable = false)
    private String key;

    /**
     * value parameters
     */
    @Column(name="value", nullable = false)
    private String value;

    /**
     * description parameters
     */
    @Column(name="description", nullable = false)
    private String description;

    /**
     * category parameters
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="category_id",
            referencedColumnName = "category_id",
            nullable = false
    )
    private ParametersCategory category;
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParametersCategory getCategory() {
        return category;
    }

    public void setCategory(ParametersCategory category) {
        this.category = category;
    }

    /*
    public enum CATEGORY{
        APP_GLOBAL{
            @Override
            public String toString() {
                return "APP_GLOBAL";
            }
        },
        QUARTZ{
            @Override
            public String toString() {
                return "QUARTZ";
            }
        }
    }*/
}


