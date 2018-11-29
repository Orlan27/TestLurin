package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a table containing the themes to aplicate 
 * at the look anf feel of the systeme in the user view</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "THEMES_DETAILS")
public class ThemesDetails {

    /**
     * Id theme detail
     */
    @Id
    @Column(name = "theme_detail_id", nullable = false)
    @GenericGenerator(
            name = "themeDetSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "THEMES_DETAILS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "themeDetSeqId")
    private Long id;
    
    @Column(name="theme_id", nullable = false)
    private Long theme;

    /**
     * Detail Name
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * Theme detail title
     */
    @Column(name="title", nullable = false)
    private String title;
    
    /**
     * Detail value
     */
    @Column(name="detail_value", nullable = false)
    private String value;
    
    /**
     * Detail type
     */
    @Column(name="detail_type", nullable = false)
    private String type;
    
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public Long getTheme() {
    	return theme;
    }
    
    public void setTheme(Long theme) {
    	this.theme = theme;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getTitle() {
    	return title;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getValue() {
    	return value;
    }
    
    public void setValue(String value) {
    	this.value = value;
    }
    
    public String getType() {
    	return type;
    }
    
    public void setType(String type) {
    	this.type = type;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThemesDetails )) return false;
        return id != null && id.equals(((ThemesDetails) o).id);
    }
    @Override
    public int hashCode() {
        return 31;
    }
}


