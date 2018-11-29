package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a roles in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "ROLES")
public class Roles {

    /**
     * Roles Id
     */
    @Id
    @Column(name = "ROLE_ID", nullable = false)
    @GenericGenerator(
            name = "rolesSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ROLES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "rolesSeqId")
    private long code;


    /**
     * Name Roles
     */
    @Column(name="name", nullable = false)
    private String name;


    /**
     * if it is visible to the public
     */
    @Column(name="VISIBLE", nullable=false, precision=1, scale=0)
    private boolean visible;

    /**
     * Role category
     */
    @ManyToOne()
    @JoinColumn(
            name="categories_roles_id",
            referencedColumnName = "categories_roles_id",
            nullable = false
    )
    private CategoriesRoles categoriesRoles;


    @ManyToOne(optional = false)
    @JoinColumn(
            name="DICTIONARY_ID",
            referencedColumnName = "DICTIONARY_ID",
            nullable=false)
    private Dictionary dictionary;

    /**
     * TABLE ID PENDING FOREIGN KEY
     */

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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public CategoriesRoles getCategoriesRoles() {
        return categoriesRoles;
    }

    public void setCategoriesRoles(CategoriesRoles categoriesRoles) {
        this.categoriesRoles = categoriesRoles;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}
