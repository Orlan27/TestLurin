package com.zed.lurin.domain.jpa;// default package
// Generated 04/26/2018 12:12:12 AM by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Columns generated by hbm2java
 */
@Entity
@Table(name="COLUMNS")
public class Columns {

    @Id
    @Column(name="COLUMN_ID", unique=true, nullable=false, precision=10, scale=0)
    private long columnId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TABLE_ID", nullable=false)
    private Tables tables;

    @Column(name="NAME", nullable=false, length=100)
    private String name;

    public long getColumnId() {
        return columnId;
    }

    public void setColumnId(long columnId) {
        this.columnId = columnId;
    }

    public Tables getTables() {
        return tables;
    }

    public void setTables(Tables tables) {
        this.tables = tables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


