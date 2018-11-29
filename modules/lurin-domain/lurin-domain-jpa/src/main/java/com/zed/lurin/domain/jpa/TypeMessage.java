package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Entity that represents a type messages in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "FV_CAT_TYPES_MSG")
public class TypeMessage implements Serializable {

    /**
     * type load id
     */
    @Id
    @Column(name = "TYPE_MSG_ID", nullable = false)
    @GenericGenerator(
            name = "catTypesMsgSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "FV_CAT_TYPES_MSG_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "catTypesMsgSeqId")
    private String  code;

    /**
     * Name
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * type load
     */
    @Column(name="TYPE_MSG", length = 2, nullable = false)
    private String typemsg;

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

    public String getTypemsg() {
        return typemsg;
    }

    public void setTypemsg(String typemsg) {
        this.typemsg = typemsg;
    }
}


