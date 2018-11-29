package com.zed.lurin.process.cas.services.impl;

import com.zed.lurin.domain.jpa.Provisioning;
import com.zed.lurin.mdb.api.dto.CampaignGenericMapping;
import com.zed.lurin.process.cas.dto.CampaingCAS;
import com.zed.lurin.process.cas.services.ICampaignsCasServices;
import com.zed.lurin.process.cas.services.ICommndEvent;
import javax.ejb.Stateful;
import javax.inject.Inject;

/**
 *
 * @author Llince
 */
@Stateful
public class CampaignsCasServicesImpl implements ICampaignsCasServices {
    @Inject
    private ICommndEvent commndEventImpl ;
    @Deprecated
    @Override
    public String sendCampaignsCas(CampaingCAS campaigns) {
        
        String response = "";
        switch (campaigns.getEvent()) {
            case CREATE:
                response = commndEventImpl.addProduct(campaigns);
                break;
            case CANCELED:
                response = commndEventImpl.cancellProduct(campaigns);
                break;
            case UPDATE:
                response = commndEventImpl.updateProduct(campaigns);
                break;
            case SUSPEND:
                response = commndEventImpl.suspendProduct(campaigns);
                break;
        }
        return response;
    }

    @Override
    public String sendCampaignsCas(Provisioning provisioning, String ipServerCas, String portServerCas, String portServerCas2, String portServerCas3) throws Exception {

        commndEventImpl.sendCommand(provisioning.getCommand(), ipServerCas, portServerCas, portServerCas2, portServerCas3);
        return null;
    }

}
