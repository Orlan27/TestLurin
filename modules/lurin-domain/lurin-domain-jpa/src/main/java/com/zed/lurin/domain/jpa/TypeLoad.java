package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Entity that represents a type load in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "FV_CAT_TYPE_LOAD")
public class TypeLoad implements Serializable {

    /**
     * type load id
     */
    @Id
    @Column(name = "TYPE_LOAD_ID", nullable = false)
    @GenericGenerator(
            name = "typeLoadId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "FV_CAT_TYPE_LOAD_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "typeLoadId")
    private long  code;

    /**
     * Name
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * type load
     */
    @Column(name="TYPE_LOAD", length = 2, nullable = false)
    private String typeLoad;

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

    public String getTypeLoad() {
        return typeLoad;
    }

    public void setTypeLoad(String typeLoad) {
        this.typeLoad = typeLoad;
    }
}


