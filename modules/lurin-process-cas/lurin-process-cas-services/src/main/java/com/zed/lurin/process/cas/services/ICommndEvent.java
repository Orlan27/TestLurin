package com.zed.lurin.process.cas.services;

import com.zed.lurin.process.cas.dto.CampaingCAS;

/**
 *
 * @author Llince
 */
public interface ICommndEvent {

    @Deprecated
    String addProduct(CampaingCAS campaigns);

    @Deprecated
    String cancellProduct(CampaingCAS campaigns);

    @Deprecated
    String updateProduct(CampaingCAS campaigns);

    @Deprecated
    String suspendProduct(CampaingCAS campaigns);

    @Deprecated
    String allCancellProduct(CampaingCAS campaigns);

    /**
     *
     * @param command
     * @param ipServerCas
     * @param portServerCas
     * @return
     */
    String sendCommand(String command, String ipServerCas, String portServerCas, String portServerCas2, String portServerCas3) throws Exception ;

}
