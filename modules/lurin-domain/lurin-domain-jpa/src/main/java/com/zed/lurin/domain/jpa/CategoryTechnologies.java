package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Entity that represents a category technologies in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "TECHNOLOGIES")
public class CategoryTechnologies implements Serializable {

    /**
     * technology id
     */
    @Id
    @Column(name = "technology_id", nullable = false)
    @GenericGenerator(
            name = "catTechSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "TECHNOLOGIES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "catTechSeqId")
    private long  code;

    /**
     * technology abbr
     */
    @Column(name="technology",length = 2, nullable = false)
    private String technology;

    /**
     * technology name
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * technology name
     */
    @Column(name="name_external", nullable = false)
    private String nameExternal;


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameExternal() {
        return nameExternal;
    }

    public void setNameExternal(String nameExternal) {
        this.nameExternal = nameExternal;
    }
}


