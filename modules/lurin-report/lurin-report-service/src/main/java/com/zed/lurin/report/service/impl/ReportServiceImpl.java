package com.zed.lurin.report.service.impl;

import com.zed.lurin.domain.dto.RequestReportCampaignDto;
import com.zed.lurin.domain.jpa.CampaignReport;
import com.zed.lurin.domain.services.IEntityManagerCreateImpl;
import com.zed.lurin.domain.jpa.FreeWiewsReport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.apache.log4j.Logger;
import com.zed.lurin.report.service.IReportService;

/**
 *
 * @author Llince
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ReportServiceImpl implements IReportService {

    private static Logger LOGGER = Logger.getLogger(ReportServiceImpl.class.getName());

    /**
     * Entity Manager reference.
     */
    private EntityManager em;

    @EJB
    IEntityManagerCreateImpl createEntityManagerFactory;

    public List<HashMap<String, Object>> getDataReportFreeWiews(String status, String carrierId, String jndiNameDs) {
        TypedQuery<FreeWiewsReport> query = null;
        List<HashMap<String, Object>> mapReport = new ArrayList<HashMap<String, Object>>();
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();

        try {
            this.em.getTransaction().begin();
            String select = "SELECT v FROM FreeWiewsReport v  ";
            String where = " WHERE v.id=v.id ";
            String and = "";
            if (status != null && !status.equals("")) {
                and += " AND v.status='" + status + "'";
            }
            if (carrierId != null && !carrierId.equals("")) {
                and += " AND v.carrierId='" + carrierId + "'";
            }
            String strQuery = select + where + and;
            query = em.createQuery(strQuery, FreeWiewsReport.class);
            for (FreeWiewsReport report : query.getResultList()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", report.getId());
                map.put("chanel", report.getChanel());
                map.put("createdate", report.getCreateDate());
                map.put("freeviewname", report.getFreeViewName());
                map.put("modifydate", report.getModifyDate());
                map.put("operadora", report.getOperadora());
                map.put("status", report.getStatus());
                map.put("tecnologyname", report.getTecnologyName());
                map.put("usercreate", report.getUserCreate());
                map.put("usermodify", report.getUserModify());
                mapReport.add(map);
            }
        } catch (Exception e) {
            LOGGER.warn(String.format("Exception FreeWiewsReport: [%]", e.getMessage()));
        }finally {
            try {
                this.em.close();
            }catch (Exception e){
                LOGGER.warn(String.format("The EntityManager is closed [%s]", e.getMessage()));
            }
        }

        return mapReport;
    }

    @Override
    public List<FreeWiewsReport> getDataFreeWiews(String status, String carrierId, String jndiNameDs) {
        TypedQuery<FreeWiewsReport> query = null;
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try {
            this.em.getTransaction().begin();
            String select = "SELECT v FROM FreeWiewsReport v  ";
            String where = " WHERE v.id=v.id ";
            String and = "";
            if (status != null && !status.equals("")) {
                and += " AND v.status='" + status + "'";
            }
            if (carrierId != null && !carrierId.equals("")) {
                and += " AND v.carrierId='" + carrierId + "'";
            }
            String strQuery = select + where + and;
            query = em.createQuery(strQuery, FreeWiewsReport.class);

        } catch (Exception e) {
            LOGGER.warn(String.format("Exception FreeWiewsData: [%]", e.getMessage()));
        }
        return query.getResultList();
    }

    public byte[] getReportExportPDF(String pathReport, HashMap<String, Object> parmeter, List<HashMap<String, Object>> data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File(pathReport));
            JRDataSource datasource = new JRBeanCollectionDataSource(data);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parmeter, datasource);
            out.write(JasperExportManager.exportReportToPdf(jasperPrint));
        } catch (JRException | IOException ex) {
            LOGGER.error(ex.getMessage());
        }
        return out.toByteArray();
    }

    public byte[] getReportExportXLS(String pathReport, HashMap<String, Object> parmeter, List<HashMap<String, Object>> data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File(pathReport));
            JRDataSource datasource = new JRBeanCollectionDataSource(data);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parmeter, datasource);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
            exporter.exportReport();
        } catch (JRException ex) {
            LOGGER.error(ex.getMessage());
        }

        return out.toByteArray();
    }

    public byte[] getReportExportCSV(String pathReport, HashMap<String, Object> parmeter, List<HashMap<String, Object>> data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File(pathReport));
            JRDataSource datasource = new JRBeanCollectionDataSource(data);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parmeter, datasource);
            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(out));
            exporter.exportReport();
        } catch (JRException ex) {
            LOGGER.error(ex.getMessage());
        }
        return out.toByteArray();
    }

    @Override
    public List<HashMap<String, Object>> getDataReportCampaign(RequestReportCampaignDto param, String jndiNameDs) {
        TypedQuery<CampaignReport> query = null;
        List<HashMap<String, Object>> mapReport = new ArrayList<HashMap<String, Object>>();

        String select = "SELECT v FROM CampaignReport v  ";
        String where = " WHERE v.idStb=v.idStb ";
        String and = "";

        if (param.getIdStb() != null && !param.getIdStb().equals("")) {
            and += " AND v.idStb=" + param.getIdStb();
        }
        if (param.getIdCliente() != null && !param.getIdCliente().equals("")) {
            and += " AND v.idClient=" + param.getIdCliente();
        }
        if (param.getCampaignId() != null && !param.getCampaignId().equals("")) {
            and += " AND v.campaignId=" + param.getCampaignId();
        }
        if (param.getFreeViewId() != null && !param.getFreeViewId().equals("")) {
            and += " AND v.freeViewId=" + param.getFreeViewId();
        }
        if (param.getIdCarrier() != null && !param.getIdCarrier().equals("")) {
            and += " AND v.carrierId=" + param.getIdCarrier();
        }
        if (param.getStatusId() != null && !param.getStatusId().equals("")) {
            and += " AND v.statusId=" + param.getStatusId();
        }
        if (param.getSchStatusId() != null && !param.getSchStatusId().equals("")) {
            and += " AND v.schStatusId=" + param.getSchStatusId();
        }
        String strQuery = select + where + and;
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try {
            this.em.getTransaction().begin();

            query = em.createQuery(strQuery, CampaignReport.class);
            for (CampaignReport report : query.getResultList()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("idStb", report.getIdStb());
                map.put("idCli", report.getIdClient());
                map.put("cmName", report.getCampaignName());
                map.put("freeName", report.getFreeViewName());
                map.put("iniDate", report.getCampaignIni());
                map.put("endDate", report.getCanpmaignEnd());
                map.put("isProv", report.getIsProvisioning());
                map.put("oper", report.getOperadora());
                map.put("status", report.getStatus());
                map.put("schStatus", report.getSchStatus());
                map.put("comercial", report.getComercialZone());
                mapReport.add(map);
            }
        } catch (Exception e) {
            LOGGER.warn(String.format("Exeption getDataReportCampaign [%s]", e.getMessage()));
        }
        return mapReport;

    }

    @Override
    public List<CampaignReport> getDataCampaign(RequestReportCampaignDto param, String jndiNameDs) {
        TypedQuery<CampaignReport> query = null;
        String select = "SELECT v FROM CampaignReport v  ";
        String where = " WHERE v.idStb=v.idStb ";
        String and = "";

        if (param.getIdStb() != null && !param.getIdStb().equals("")) {
            and += " AND v.idStb=" + param.getIdStb();
        }
        if (param.getIdCliente() != null && !param.getIdCliente().equals("")) {
            and += " AND v.idClient=" + param.getIdCliente();
        }
        if (param.getCampaignId() != null && !param.getCampaignId().equals("")) {
            and += " AND v.campaignId=" + param.getCampaignId();
        }
        if (param.getFreeViewId() != null && !param.getFreeViewId().equals("")) {
            and += " AND v.freeViewId=" + param.getFreeViewId();
        }
        if (param.getIdCarrier() != null && !param.getIdCarrier().equals("")) {
            and += " AND v.carrierId=" + param.getIdCarrier();
        }
        if (param.getStatusId() != null && !param.getStatusId().equals("")) {
            and += " AND v.statusId=" + param.getStatusId();
        }
        if (param.getSchStatusId() != null && !param.getSchStatusId().equals("")) {
            and += " AND v.schStatusId=" + param.getSchStatusId();
        }
        String strQuery = select + where + and;
        this.em = this.createEntityManagerFactory.createEntityManagerFactory(jndiNameDs).createEntityManager();
        try {
            this.em.getTransaction().begin();
            query = em.createQuery(strQuery, CampaignReport.class);

        } catch (Exception e) {
            LOGGER.warn(String.format("Exeption getDataCampaign [%s]", e.getMessage()));
        }
        return query.getResultList();

    }
}
