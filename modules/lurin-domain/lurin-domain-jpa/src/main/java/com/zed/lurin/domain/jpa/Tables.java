package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a table containing the name of those tables of the model 
 * whose content is translated in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "TABLES")
public class Tables {

    /**
     * Id table
     */
    @Id
    @Column(name = "table_id", nullable = false)
    @GenericGenerator(
            name = "tablesSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "TABLES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "tablesSeqId")
    private long  tableId;

    /**
     * Table Name
     */
    @Column(name="name", nullable = false)
    private String name;

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


