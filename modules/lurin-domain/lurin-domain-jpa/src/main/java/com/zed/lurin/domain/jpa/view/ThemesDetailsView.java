package com.zed.lurin.domain.jpa.view;

import javax.persistence.*;

/**
 * <p>Entity that represents a menus for user name</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "v_themesdetails_dictionary")
public class ThemesDetailsView {

    /**
     * View themes details id
     */
    @Id
    @Column(name = "id", nullable = false)
    private long code;
    
    @Column(name = "theme_detail_id", nullable = false)
    private Long id;

    /**
     * theme id
     */
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
    
    
    /**
     * default I18n for the title
     */
    @Column(name="default_I18n", nullable = false)
    private String defaultI18n;

    /**
     * Lang ES for the title
     */
    @Column(name="es", nullable = false)
    private String langES;

    /**
     * Lang EN for the title
     */
    @Column(name="en", nullable = false)
    private String langEN;

    /**
     * Lang PR the for the title
     */
    @Column(name="pr", nullable = false)
    private String langPR;

    @Transient
    private String langAssigned;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
    
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

    public String getDefaultI18n() {
        return defaultI18n;
    }

    public void setDefaultI18n(String defaultI18n) {
        this.defaultI18n = defaultI18n;
    }

    public String getLangES() {
        return langES;
    }

    public void setLangES(String langES) {
        this.langES = langES;
    }

    public String getLangEN() {
        return langEN;
    }

    public void setLangEN(String langEN) {
        this.langEN = langEN;
    }

    public String getLangPR() {
        return langPR;
    }

    public void setLangPR(String langPR) {
        this.langPR = langPR;
    }

    public String getLangAssigned() {
        return langAssigned;
    }

    public void setLangAssigned(String langAssigned) {
        this.langAssigned = langAssigned;
    }
}
