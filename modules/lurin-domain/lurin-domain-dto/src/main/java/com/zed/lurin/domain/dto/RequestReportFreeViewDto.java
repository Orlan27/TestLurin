
package com.zed.lurin.domain.dto;
/**
 *
 * @author Llince
 */
public class RequestReportFreeViewDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String idCarrier;
    private String status;
    private String typeReport; // PDF ,XLS,CSV  

    public String getIdCarrier() {
        return idCarrier;
    }

    public void setIdCarrier(String idCarrier) {
        this.idCarrier = idCarrier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }

}
