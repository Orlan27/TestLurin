package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Entity that represents a messages in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "FV_MESSAGES")
public class Messages implements Serializable {

    /**
     * type load id
     */
    @Id
    @Column(name = "MESSAGE_ID", nullable = false)
    @GenericGenerator(
            name = "messagesSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "FV_MESSAGES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "messagesSeqId")
    private long  code;

    /**
     * Messages
     */
    @Column(name="message", nullable = false)
    private String message;


    @ManyToOne(optional = false)
    @JoinColumn(
            name="TYPE_MSG_ID",
            referencedColumnName = "TYPE_MSG_ID",
            nullable = false
    )
    private TypeMessage typeMessage;

    /**
     * Status
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="status_id",
            referencedColumnName = "status_id",
            nullable = false
    )
    private CategoryStatus status;


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }
}


