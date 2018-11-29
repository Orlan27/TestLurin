package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.zed.lurin.domain.jpa.view.ThemesDetailsView;

/**
 * <p>Entity that represents a table containing the themes to aplicate 
 * at the look anf feel of the systeme in the user view</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "THEMES")
public class Themes {

    /**
     * Id table
     */
    @Id
    @Column(name = "theme_id", nullable = false)
    @GenericGenerator(
            name = "themeSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "THEMES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "themeSeqId")
    private Long  themeId;

    /**
     * Theme Name
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * Theme description
     */
    @Column(name="description", nullable = false)
    private String description;
    
    public Long getThemeId() {
    	return themeId;
    }
    
    public void setThemeId(Long themeId) {
    	this.themeId = themeId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    @Transient
    private List<ThemesDetailsView> details = new ArrayList<ThemesDetailsView>();
    
    public List<ThemesDetailsView> getDetails(){
    	return details;
    }
    
    public void setDetails(List<ThemesDetailsView> details) {
    	this.details = details;
    }
}


