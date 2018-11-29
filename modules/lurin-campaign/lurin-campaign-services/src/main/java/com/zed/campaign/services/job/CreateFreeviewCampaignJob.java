package com.zed.campaign.services.job;

import com.zed.campaign.services.triggers.ITriggerValidateCampaignServices;
import com.zed.lurin.domain.jpa.view.JobDetailsView;
import com.zed.lurin.mdb.api.dto.CampaignGenericMapping;
import com.zed.lurin.mdb.api.keys.QueueNameKeys;
import com.zed.lurin.mdb.api.queue.IMessageQueue;
import com.zed.lurin.quartz.keys.QuartzKeys;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

public class CreateFreeviewCampaignJob implements Job {

    /*
     *Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(CreateFreeviewCampaignJob.class.getName());

    /**
     * Message Queue
     */
    private IMessageQueue messageQueue;

    /**
     * Trigger Campaign Services
     */
    private ITriggerValidateCampaignServices triggerValidateCampaignServices;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        this.initialized();

        JobDataMap map = context.getMergedJobDataMap();

        LOGGER.debug(String.format("[JOB] Validating Job Name %s", context.getJobDetail().getKey().getName()));
        List<JobDetailsView> jobDetailsViews =  this.triggerValidateCampaignServices.getJobsInterrupt(
                context.getJobDetail().getKey().getName());

        if(!jobDetailsViews.stream().findFirst().isPresent()){


            String typeCampaign = (String) map.get(QuartzKeys.CAMPAING_TYPE.toString());
            long campaignsId = (long) map.get(QuartzKeys.CAMPAING.toString());
            List<Long> campaignMember = (List<Long>) map.get(QuartzKeys.CAMPAING_MEMBER.toString());
            long carrierId = (long) map.get(QuartzKeys.CARRIER_ID.toString());
            String ipServerCas = (String) map.get(QuartzKeys.IP_SERVER_CAS.toString());
            String portServerCas = (String) map.get(QuartzKeys.PORT_SERVER_CAS.toString());
            String portServerCas2 = (String) map.get(QuartzKeys.PORT_SERVER_CAS2.toString());
            String portServerCas3 = (String) map.get(QuartzKeys.PORT_SERVER_CAS3.toString());
            int initBatch =(int) map.get(QuartzKeys.INIT_BATCH.toString());
            int endBatch = (int) map.get(QuartzKeys.END_BATCH.toString());

            LOGGER.debug(String.format("[JOB][%s] send CAS command %s message to the queue",
                    CreateFreeviewCampaignJob.class.getName(), typeCampaign));

            LOGGER.debug(String.format("[JOB][%s] Data Send ID CAMPAIGN[%s]",
                    CreateFreeviewCampaignJob.class.getName(), campaignsId));

            CampaignGenericMapping campaignGenericMapping = new CampaignGenericMapping();
            campaignGenericMapping.setCampaignMember(campaignMember);
            campaignGenericMapping.setCarrierId(carrierId);
            campaignGenericMapping.setIpServerCas(ipServerCas);
            campaignGenericMapping.setPortServerCas(portServerCas);
            campaignGenericMapping.setPortServerCas2(portServerCas2);
            campaignGenericMapping.setPortServerCas3(portServerCas3);
            campaignGenericMapping.setInitBatch(initBatch);
            campaignGenericMapping.setEndBatch(endBatch);

            /**
             * Parser here
             */

            this.messageQueue.send(QueueNameKeys.SEND_COMMAND_QUEUE_NAME, campaignGenericMapping);
        }else{
            LOGGER.debug( String.format("[JOB] Job Name %s cancellated ",context.getJobDetail().getKey().getName()));
        }

    }


    /**
     * Initialize ejbs
     */
    private void initialized(){
        try {
            this.messageQueue = (IMessageQueue)
                    new InitialContext().lookup(IMessageQueue.ejbMappedName);

            this.triggerValidateCampaignServices = (ITriggerValidateCampaignServices) new InitialContext()
                    .lookup(triggerValidateCampaignServices.ejbMappedName);

        }catch (NamingException e){
            LOGGER.error(e.getMessage());
        }
    }

}
