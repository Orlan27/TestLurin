/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zed.lurin.domain.dto;

/**
 *
 * @author Llince
 */
public class ResponseReportDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String contentType;
    private byte[] report;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getReport() {
        return report;
    }

    public void setReport(byte[] report) {
        this.report = report;
    }

}
