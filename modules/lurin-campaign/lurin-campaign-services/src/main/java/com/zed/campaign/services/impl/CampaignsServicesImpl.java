package com.zed.campaign.services.impl;

import java.io.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.zed.campaign.services.ICampaignsServices;
import com.zed.campaign.services.triggers.ITriggerValidateCampaignServices;
import com.zed.carriers.services.ICarriersServices;
import com.zed.lurin.commons.utils.ReadCSV;
import com.zed.lurin.domain.jpa.CampaignMembersTemporal;
import com.zed.lurin.domain.jpa.Campaigns;
import com.zed.lurin.domain.jpa.CarrierDataSources;
import com.zed.lurin.domain.jpa.Carriers;
import com.zed.lurin.domain.services.IEntityManagerCreateImpl;
import com.zed.lurin.domain.services.IStatusSchemaServices;
import com.zed.lurin.mail.services.IMailServices;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.process.cas.services.IProvisioningServicesImpl;
import com.zed.lurin.quartz.dto.CampaignScheduler;
import com.zed.lurin.security.keys.Keys;
import com.zed.operators.managment.services.IOperatorsManagmentServices;
import com.zed.operators.managment.services.exceptions.ExceptionCarrierCause;
import org.apache.log4j.Logger;
import org.hibernate.dialect.OracleTypesHelper;
import org.hibernate.internal.util.ReflectHelper;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.*;


/**
 *
 * @author Alberto Zapata
 * 
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CampaignsServicesImpl implements ICampaignsServices {

    private static Logger LOGGER = Logger.getLogger(CampaignsServicesImpl.class.getName());

    /**
     * Entity Manager reference.
     */
    private EntityManager em;

    /**
     * Services the status methods
     */
    @EJB
    IStatusSchemaServices iStatusSchemaServices;

    /**
     * Service that creates the EntityManager
     */
    @EJB
    IEntityManagerCreateImpl createEntityManagerFactory;

    @EJB
    ICoreParameterServices iCoreParameterServices;

    @EJB
    IMailServices mailServices;

    @EJB
    ICarriersServices iCarriersServices;

    @EJB
    IProvisioningServicesImpl iProvisioningServices;

    @EJB
    ITriggerValidateCampaignServices iTriggerValidateCampaignServices;

    @EJB
    IOperatorsManagmentServices iOperatorsManagmentServices;

    /**
     * Create new campaings
     * @param campaigns
     * @param jndiNameDs
     * @param isCloseEntityManager
     * @return Object Campaign
     */
    @Override
    public Campaigns createCampaigns(Campaigns campaigns, String jndiNameDs, boolean isCloseEntityManager) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try {
            this.em.getTransaction().begin();
            campaigns.setCatSchStatus(this.iStatusSchemaServices.getCategoryNew(jndiNameDs));

            this.em.persist(campaigns);
            this.em.getTransaction().commit();

        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return campaigns;
    }

    @Override
    public List<CampaignMembersTemporal> loadCampaignTemporal(Campaigns campaigns, String jndiNameDs, boolean isCloseEntityManager){
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        List<CampaignMembersTemporal> campaignMembersTemporally = new ArrayList<>();
        try {
            this.em.getTransaction().begin();
            campaignMembersTemporally = getCampaignMembersTemporals(campaigns);
            campaignMembersTemporally.stream().forEach(cmp->this.em.persist(cmp));
            this.em.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return campaignMembersTemporally;
    }

    /**
     * Read CSV and create list de CampaignTemporal
     *
     * @param campaigns
     * @return
     * @throws IOException
     */
    private List<CampaignMembersTemporal> getCampaignMembersTemporals(Campaigns campaigns) throws IOException {
        List<CampaignMembersTemporal> campaignMembersTemporally = new ArrayList<>();

        ReadCSV getCsv = new ReadCSV(new FileReader(campaigns.getFileName()));
        if(getCsv.readHeaders()) {
            String header = getCsv.getHeaders()[0];
            while (getCsv.readRecord()) {
                CampaignMembersTemporal campaignMembersTemporal = new CampaignMembersTemporal();
                campaignMembersTemporal.setMember(getCsv.get(header));
                campaignMembersTemporal.setCampaigns(campaigns);
                campaignMembersTemporally.add(campaignMembersTemporal);

            }
        }
        getCsv.close();

        return campaignMembersTemporally;
    }

    /**
     *
     * Execute procedure for validate campaign
     *
     * @param campaigns
     * @param jndiNameDs
     * @param isCloseEntityManager
     */
    @Override
    public List<Long> executeValidatingCampaign(Campaigns campaigns, String jndiNameDs, boolean isCloseEntityManager){
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        List<Long> listResult = null;
        try {
            this.em.getTransaction().begin();

            StoredProcedureQuery query = this.em.createStoredProcedureQuery("PKG_VALIDATE_CAMP.SP_VALIDATE_CAMPAIGN");
            query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT);
            query.setParameter(1, campaigns.getCampaignId());
            query.setParameter(2, campaigns.getCarrierId());

            String membersGenerated = (String) query.getOutputParameterValue(3);

            listResult = Arrays.asList(membersGenerated.trim().split(" "))
                    .stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());


        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return listResult ;
    }

    /**
     * @param campaign
     * @return Object {@link Campaigns }
     * @title Method that updates a campaign
     */
    @Override
    public Campaigns updateCampaign(Campaigns campaign, String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try {
            this.em.getTransaction().begin();

            this.em.merge(campaign);
            this.em.getTransaction().commit();
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
        return campaign;
    }

    /**
     * method that returns the list of campaign
     *
     * @param jndiNameDs
     * @return
     */
    @Override
    public List<Campaigns> getCampaigns(String jndiNameDs) {
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try {
            this.em.getTransaction().begin();

            TypedQuery<Campaigns> query = this.em.createQuery("SELECT c FROM Campaigns c", Campaigns.class);
            List<Campaigns> campaigns = query.getResultList();
            return campaigns;

        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }
    }


    /**
     * Read CSV and validate number rows
     *
     * @param fileName
     * @return false if not valid row numbers
     * @throws IOException
     */
    @Override
    public boolean isValidateNumberRows(String fileName) throws IOException {


        int maxNumberRows = this.iCoreParameterServices.getCoreParameterInteger(Keys.CAS_NUMBER_MAX_ROWS_FILES.toString());
        int maxNumberRowsCount = 0;
        ReadCSV getCsv = new ReadCSV(new FileReader(fileName));
        if(getCsv.readHeaders()) {
            while (getCsv.readRecord()) {
                maxNumberRowsCount++;
            }
        }
        getCsv.close();

        return maxNumberRowsCount>maxNumberRows?false:true;
    }


    /**
     * Method that sends an email notifying that the validation process was completed
     *
     * @param email
     * @param campaignID
     */
    @Override
    public void sendValidateMail(String email, long campaignID) {
        try {
            String body = this.iCoreParameterServices.getCoreParameterString(
                    Keys.VALIDATE_CAMPAIGN_FINALIZED.toString());

            body = String.format(body, campaignID);
            mailServices.send(email, "Campaign validation completed", body);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     *  Method that create and validate campaign automated
     * @param campaigns
     * @param jndiName
     * @param email
     * @param validateBefore
     */
    @Override
    public void processValidationsAndTriggerEvents(Campaigns campaigns, String jndiName, String email, long validateBefore, int priority){

        Carriers carriers = iCarriersServices.findById(campaigns.getCarrierId());

        LocalTime initTime = LocalTime.parse(carriers.getStartTime());
        long maxDailyRate = carriers.getMaxDailyRate();
        int utcCarriers = carriers.getUtc();

        LOGGER.info(String.format("Process of inserting bulk of temporary data of the campaign ID[%s]",campaigns.getCampaignId()));
        this.loadCampaignTemporal(campaigns, jndiName, false);

        LOGGER.info(String.format("Initiating process of validation of data of the campaign ID[%s]",campaigns.getCampaignId()));
        List<Long> campaignMember= this.executeValidatingCampaign(campaigns, jndiName, false);

        if(validateBefore==0){
            long totalProvisioning= iProvisioningServices.totalProvisioningMembersByCarrier(campaigns.getCarrierId());

            if(email!=null){
                LOGGER.info(String.format("Notification of which validation process has been completed by campaign ID[%s]",campaigns.getCampaignId()));
                this.sendValidateMail(email, campaigns.getCampaignId());
            }

            DecimalFormat decimalFormat = new DecimalFormat("#");
            String roundTotal = decimalFormat.format(((float)totalProvisioning/(float)maxDailyRate));
            int totalCreateTriggers = Integer.parseInt(roundTotal) ;

            if(totalCreateTriggers==0){
                totalCreateTriggers=1;
            }


            for (int i=0; i<totalCreateTriggers; i++){

                int initBatch = 1;
                int maxDailyRateDynamic = (int) maxDailyRate;

                if(i==(totalCreateTriggers-1)){
                    initBatch = 0;
                    maxDailyRateDynamic=0;
                }

                LOGGER.info(String.format("Creating task of activation of the cas the campaign ID[%s]",campaigns.getCampaignId()));
                iTriggerValidateCampaignServices.createTriggerFreeviewCampaign(
                        getCampaignSchedulerTransforms(campaigns, CampaignScheduler.TYPE_CAS_COMMAND.ACTIVATION,
                                campaignMember, campaigns.getCarrierId(),i, initTime,
                                utcCarriers, initBatch, maxDailyRateDynamic));

            }
        }else{
            Calendar startAt = Calendar.getInstance();

            if(priority==0){
                BigDecimal bigValue = iTriggerValidateCampaignServices.maxDateTriggerJobs();
                if(bigValue==null){
                    startAt.setTime(campaigns.getDateIniSchedule());
                }else{
                    Date date = new Date();
                    date.setTime(bigValue.longValue());
                    startAt.setTime(date);
                }
            }else{
                startAt.setTime(campaigns.getDateIniSchedule());
            }


            startAt.add(Calendar.DATE, (int)(validateBefore*-1));
            startAt.set(Calendar.HOUR_OF_DAY, initTime.getHour());
            startAt.set(Calendar.MINUTE, initTime.getMinute());
            startAt.add(Calendar.HOUR, utcCarriers);


            String nameJob = String.format("%s_Job_Validate_%s_Carrier_%s", startAt.getTime() ,campaigns.getCampaignId(), campaigns.getCarrierId());
            String nameTrigger = String.format("%s_Trigger_%s_Carrier_%s", startAt.getTime(), campaigns.getCampaignId(), campaigns.getCarrierId());

            LOGGER.info(String.format("Creating task of validation before of the cas the campaign ID[%s]",campaigns.getCampaignId()));
            iTriggerValidateCampaignServices.createValidateTriggerFreeviewCampaign(campaigns, campaignMember, email,
                    jndiName ,nameTrigger, nameJob,startAt.getTime());



        }
    }

    /**
     * Method to transform and map an object
     * @param campaigns
     * @param type
     * @param campaignMembers
     * @param carrierId
     * @return
     */
    private CampaignScheduler getCampaignSchedulerTransforms(Campaigns campaigns, CampaignScheduler.TYPE_CAS_COMMAND type,
                                                             List<Long> campaignMembers,
                                                             long carrierId, int dateIncrement, LocalTime initTime,
                                                             int utcCarriers, int initBatch, int endBatch){

        String typeSend = type.equals(CampaignScheduler.TYPE_CAS_COMMAND.ACTIVATION)?
                CampaignScheduler.TYPE_CAS_COMMAND.ACTIVATION.toString():
                CampaignScheduler.TYPE_CAS_COMMAND.DEACTIVATION.toString();


        Calendar startAt = Calendar.getInstance();
        startAt.setTime(type.equals(CampaignScheduler.TYPE_CAS_COMMAND.ACTIVATION)?campaigns.getDateIniSchedule():campaigns.getDateFinSchedule());

        startAt.add(Calendar.DATE, dateIncrement);
        startAt.set(Calendar.HOUR_OF_DAY, initTime.getHour());
        startAt.set(Calendar.MINUTE, initTime.getMinute());
        startAt.add(Calendar.HOUR, utcCarriers);

        CampaignScheduler campaignScheduler =  new CampaignScheduler();
        campaignScheduler.setNameJob(String.format("%s_Job_%s_Carrier_%s_Tag_%s", typeSend ,campaigns.getCampaignId(), carrierId, dateIncrement));
        campaignScheduler.setStartAT(startAt.getTime());
        campaignScheduler.setNameTrigger(String.format("%s_Trigger_%s_Carrier_%s_Tag_%s", typeSend, campaigns.getCampaignId(), carrierId, dateIncrement));
        campaignScheduler.setCampaingId(campaigns.getCampaignId());
        campaignScheduler.setType(type);
        campaignScheduler.setCampaingMember(campaignMembers);
        campaignScheduler.setCarrierId(carrierId);
        campaignScheduler.setInitBatch(initBatch);
        campaignScheduler.setEndBatch(endBatch);

        CarrierDataSources carrierDataSources = iOperatorsManagmentServices.getCarrierDataSourcesForType(carrierId, "T");

        campaignScheduler.setIpServerCas(carrierDataSources.getDataSources().getIp());
        if(carrierDataSources.getDataSources().getPort()!=null){
            campaignScheduler.setPortServerCas(carrierDataSources.getDataSources().getPort().toString());
        }
        if(carrierDataSources.getDataSources().getPort2()!=null){
            campaignScheduler.setPortServerCas2(carrierDataSources.getDataSources().getPort2().toString());
        }
        if(carrierDataSources.getDataSources().getPort3()!=null){
            campaignScheduler.setPortServerCas3(carrierDataSources.getDataSources().getPort3().toString());
        }

        return campaignScheduler;
    }

}
