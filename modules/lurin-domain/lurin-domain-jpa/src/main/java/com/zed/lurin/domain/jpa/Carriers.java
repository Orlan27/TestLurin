package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Entity that represents a Carriers in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "CARRIERS")
public class Carriers  {

    /**
     * Carriers Id
     */
    @Id
    @Column(name = "CARRIER_ID", nullable = false)
    @GenericGenerator(
            name = "carrierSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CARRIERS_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "carrierSeqId")
    private long code;


    /**
     * #########################
     * Pending Table id Entity
     * #########################
     */

    /**
     * country
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="country_id",
            referencedColumnName = "country_id",
            nullable = false
    )
    private Countries countryId;

    /**
     * Name
     */
    @Column(name="name", nullable = false)
    private String name;
    
    /**
     * Time when the sale range for sending commands to CAS starts
     */
    @Column(name="start_time", nullable = false)
    private String startTime;
    
    /**
     * Time the sale range for sending commands to CAS ends
     */
    @Column(name="end_time", nullable = false)
    private String endTime;
    
    /**
     * Operator's percentage share for sending commands per second to CAS
     */
    @Column(name="percentage_share", nullable = false)
    private long percentageShare;
    
    /**
     * This characteristic sets the maximum number of commands allowed in a day (calculated)
     */
    @Column(name="max_daily_rate", nullable = false)
    private long maxDailyRate;

    /**
     * Tables
     */
    @ManyToOne(optional = true)
    @JoinColumn(
            name="table_id",
            referencedColumnName = "table_id",
            nullable = true
    )
    private Tables tables;
    
    /**
     * Companies
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="company_id",
            referencedColumnName = "company_id",
            nullable = false
    )
    private Companies companies;

    /**
     * Status
     */
    @ManyToOne(optional = false)
    @JoinColumn(
            name="status_id",
            referencedColumnName = "status_id",
            nullable = false
    )
    private SecurityCategoryStatus status;
    
    /**
     * Themes
     */
    @ManyToOne(optional = true)
    @JoinColumn(
            name="theme_id",
            referencedColumnName = "theme_id",
            nullable = true
    )
    private Themes theme;
    
    /**
     * Represents the administrator user that is created together with the operator
     */
    @Transient
    private Users adminUser;
    

    /**
     * UTC
     */
    @Column(name="utc", nullable = false)
    private int utc;

    /**
     * Name prefix
     */
    @Column(name="name_prefix", nullable = false)
    private String namePrefix;

    @ManyToMany(targetEntity=DataSources.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="CARRIERS_DS",
            joinColumns = {@JoinColumn(name="CARRIER_ID") },
            inverseJoinColumns = {@JoinColumn(name="DATA_SOURCE_ID") })
    private Set<DataSources> dataSources = new HashSet<DataSources>(0);

    @ManyToMany(targetEntity = CategoryTechnologies.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="CARRIERS_TECHONLOGIES",
            joinColumns = {@JoinColumn(name="CARRIER_ID") },
            inverseJoinColumns = {@JoinColumn(name="TECHNOLOGY_ID") })
    private Set<CategoryTechnologies> categoryTechnologies = new HashSet<>(0);


    /**
     * id to associate the Free View operators with the operators of the INTRAWAY database
     */
    @Column(name="EXTERNAL_IDOPERADORA", nullable = false)
    private long externalOperatorId;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Countries getCountryId() {
        return countryId;
    }

    public void setCountryId(Countries countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Companies getCompanies() {
        return companies;
    }

    public void setCompanies(Companies companies) {
        this.companies = companies;
    }

    public SecurityCategoryStatus getStatus() {
        return status;
    }

    public void setStatus(SecurityCategoryStatus status) {
        this.status = status;
    }
    
    public Users getAdminUser() {
    	return adminUser;
    }
    
    public void setAdminUser(Users adminUser) {
    	this.adminUser = adminUser;
    }
    
	public long getPercentageShare() {
		return percentageShare;
	}

	public void setPercentageShare(long percentageShare) {
		this.percentageShare = percentageShare;
	}

	public long getMaxDailyRate() {
		return maxDailyRate;
	}

	public void setMaxDailyRate(long maxDailyRate) {
		this.maxDailyRate = maxDailyRate;
	}

	public Tables getTables() {
		return tables;
	}

	public void setTables(Tables tables) {
		this.tables = tables;
	}
    
	public Themes getThemes() {
		return theme;
	}
    
	public void setThemes(Themes theme) {
		this.theme = theme;
	}

    public Themes getTheme() {
        return theme;
    }

    public void setTheme(Themes theme) {
        this.theme = theme;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public int getUtc() {
        return utc;
    }

    public void setUtc(int utc) {
        this.utc = utc;
    }

    public Set<DataSources> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Set<DataSources> dataSources) {
        this.dataSources = dataSources;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Set<CategoryTechnologies> getCategoryTechnologies() {
        return categoryTechnologies;
    }

    public void setCategoryTechnologies(Set<CategoryTechnologies> categoryTechnologies) {
        this.categoryTechnologies = categoryTechnologies;
    }

    public long getExternalOperatorId() {
        return externalOperatorId;
    }

    public void setExternalOperatorId(long externalOperatorId) {
        this.externalOperatorId = externalOperatorId;
    }
}
