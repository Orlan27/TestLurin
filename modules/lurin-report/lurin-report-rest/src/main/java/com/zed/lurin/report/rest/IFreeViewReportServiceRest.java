/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zed.lurin.report.rest;

import com.zed.lurin.domain.dto.RequestReportCampaignDto;
import com.zed.lurin.domain.dto.RequestReportFreeViewDto;
import javax.ws.rs.core.Response;

/**
 *
 * @author Llince
 */
public interface IFreeViewReportServiceRest {
    
 Response getReportfreeView(RequestReportFreeViewDto report);
 Response getReportCampaign(RequestReportCampaignDto report);
 Response getDatafreeView(RequestReportFreeViewDto report);
 Response getDataCampaign(RequestReportCampaignDto report);
 /**
  * <p>method that List all CategorySchStatus in the database for campaing reports</p>
  *
  * @return {@javax.ws.rs.core.Response}
  */
 Response getAllCategorySchStatus();
}
