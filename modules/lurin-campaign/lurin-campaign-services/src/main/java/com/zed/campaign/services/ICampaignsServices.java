package com.zed.campaign.services;

import com.zed.lurin.domain.jpa.CampaignMembersTemporal;
import com.zed.lurin.domain.jpa.Campaigns;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

public interface ICampaignsServices {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.campaign.services/CampaignsServicesImpl!com.zed.campaign.services.ICampaignsServices";

    /**
     * Create new campaings
     * @param campaigns
     * @param jndiNameDs
     * @param isCloseEntityManager
     * @return Object Campaign
     */
    Campaigns createCampaigns(Campaigns campaigns, String jndiNameDs, boolean isCloseEntityManager);

    /**
     * @param campaign
     * @return Object {@link Campaigns }
     * @title Method that updates a campaign
     */
    Campaigns updateCampaign(Campaigns campaign, String jndiNameDs);


    /**
     * method that returns the list of campaign
     * @param jndiNameDs
     * @return
     */
    List<Campaigns> getCampaigns(String jndiNameDs);

    /**
     *
     * Execute procedure for validate campaign
     *
     * @param campaigns
     * @param jndiNameDs
     * @param isCloseEntityManager
     */
    List<Long> executeValidatingCampaign(Campaigns campaigns, String jndiNameDs, boolean isCloseEntityManager);

    /**
     *
     * @param campaigns
     * @param jndiNameDs
     * @param isCloseEntityManager
     * @return
     */
    List<CampaignMembersTemporal> loadCampaignTemporal(Campaigns campaigns, String jndiNameDs, boolean isCloseEntityManager);

    /**
     * Read CSV and validate number rows
     *
     * @param fileName
     * @return false if not valid row numbers
     * @throws IOException
     */
    boolean isValidateNumberRows(String fileName) throws IOException;


    /**
     * Method that sends an email notifying that the validation process was completed
     *
     * @param email
     * @param campaignID
     */
    void sendValidateMail(String email, long campaignID);

    /**
     *  Method that create and validate campaign automated
     * @param campaigns
     * @param jndiName
     * @param email
     * @param validateBefore
     * @param  priority
     */
    void processValidationsAndTriggerEvents(Campaigns campaigns, String jndiName, String email, long validateBefore, int priority);

}
