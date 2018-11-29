
package com.zed.lurin.process.cas.services;

import com.zed.lurin.domain.jpa.Provisioning;
import com.zed.lurin.process.cas.dto.CampaingCAS;

/**
 *
 * @author Llince
 */
public interface ICampaignsCasServices {

 @Deprecated
 String sendCampaignsCas(CampaingCAS campaigns);

 String sendCampaignsCas(Provisioning provisioning, String ipServerCas, String portServerCas, String portServerCas2, String portServerCas3) throws Exception;
}
