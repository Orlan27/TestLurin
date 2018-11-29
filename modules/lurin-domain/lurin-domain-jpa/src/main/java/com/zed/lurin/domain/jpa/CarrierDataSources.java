package com.zed.lurin.domain.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Entity that represents a user carriers roles in database</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "CARRIERS_DS")
@IdClass(CarrierDataSourcesId.class)
public class CarrierDataSources  {
	
	

    /**
     * Carriers
     */
	@Id
    @ManyToOne(optional = false)
    @JoinColumn(
            name="CARRIER_ID",
            referencedColumnName = "CARRIER_ID",
            nullable = false
    )
    private Carriers carriers;
	
	@Id
    @ManyToOne(optional = false)
    @JoinColumn(
            name="DATA_SOURCE_ID",
            referencedColumnName = "DATA_SOURCE_ID",
            nullable = false
    )
    private DataSources dataSources;

    public DataSources getDataSources() {
        return dataSources;
    }

    public void setDataSources(DataSources dataSources) {
        this.dataSources = dataSources;
    }

    public Carriers getCarriers() {
        return carriers;
    }

    public void setCarriers(Carriers carriers) {
        this.carriers = carriers;
    }
    
    

}
