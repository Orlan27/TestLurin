package com.zed.lurin.domain.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Entity that represents a countries in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "COUNTRIES")
public class Countries {

    /**
     * Id Country
     */
    @Id
    @Column(name = "country_id", nullable = false)
    @GenericGenerator(
            name = "countriesSeqId",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "COUNTRIES_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "countriesSeqId")
    private String  code;

    /**
     * Country Name
     */
    @Column(name="country", nullable = false)
    private String country;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}


