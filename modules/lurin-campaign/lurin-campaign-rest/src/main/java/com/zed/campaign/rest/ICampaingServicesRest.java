package com.zed.campaign.rest;


import com.zed.campaign.services.entity.CampaignEntity;

import javax.ws.rs.core.Response;

/**
 * @title Abstract Class which defines the methods manage Campaigns are implemented
 * @author Francisco Lujano
 * 
 */
public interface ICampaingServicesRest {

    /**
     * @title Method that creates a campaign
     * @param campaignEntity
     * @return {@javax.ws.rs.core.Response}
     */
    Response createCampaigns(String token, CampaignEntity campaignEntity);


    /**
     * method that returns the list of campaign
     * @return
     */
    Response getCampaigns();


}
