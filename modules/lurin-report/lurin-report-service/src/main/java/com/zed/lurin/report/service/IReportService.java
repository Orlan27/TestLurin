package com.zed.lurin.report.service;

import com.zed.lurin.domain.dto.RequestReportCampaignDto;
import com.zed.lurin.domain.jpa.CampaignReport;
import com.zed.lurin.domain.jpa.FreeWiewsReport;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Llince
 */
public interface IReportService {

    byte[] getReportExportPDF(String pathReport, HashMap<String, Object> parmeter, List<HashMap<String, Object>> data);

    byte[] getReportExportXLS(String pathReport, HashMap<String, Object> parmeter, List<HashMap<String, Object>> data);

    byte[] getReportExportCSV(String pathReport, HashMap<String, Object> parmeter, List<HashMap<String, Object>> data);

    List<HashMap<String, Object>> getDataReportFreeWiews(String status, String carrierdId, String jndiNameDs);

    List<HashMap<String, Object>> getDataReportCampaign(RequestReportCampaignDto param, String jndiNameDs);

    List<FreeWiewsReport> getDataFreeWiews(String status, String carrierId, String jndiNameDs);

    List<CampaignReport> getDataCampaign(RequestReportCampaignDto param, String jndiNameDs);

}
