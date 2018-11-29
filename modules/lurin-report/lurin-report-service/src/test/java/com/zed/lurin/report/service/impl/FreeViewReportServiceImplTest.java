/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zed.lurin.report.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.*;

import static org.junit.Assert.*;
import com.zed.lurin.report.service.IReportService;

/**
 *
 * @author AppDes
 */
public class FreeViewReportServiceImplTest {

    static List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
    static String pathReport = "/home/AppDes/NetBeansProjects/branches/lurin-master/configuration/docker/server/resources/reports/ReporteFreeWiew.jasper";
    static IReportService ifreeViewReportService;
    static String pathHome = System.getProperty("user.home");
    static HashMap<String, Object> parmeter = new HashMap<String, Object>();

    @BeforeClass
    public static void setUpClass() throws IOException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        ifreeViewReportService = new ReportServiceImpl();
        Date date = new Date();
        map.put("id", 1);
        map.put("chanel", "ch");
        map.put("createdate", date);
        map.put("freeviewname", "Test 1");
        map.put("modifydate", date);
        map.put("operadora", "Salvedor");
        map.put("status", "active");
        map.put("tecnologyname", "CATV");
        map.put("usercreate", "admin");
        map.put("usermodify", "admin");
        data.add(map);
        map = new HashMap<String, Object>();
        map.put("id", 2);
        map.put("chanel", "CATV");
        map.put("createdate", date);
        map.put("freeviewname", "Test 2");
        map.put("modifydate", date);
        map.put("operadora", "Peru");
        map.put("status", "active");
        map.put("tecnologyname", "PPW");
        map.put("usercreate", "admin");
        map.put("usermodify", "admin");
        data.add(map);
        parmeter.put("ColectionParam", data);
        File fileDir = new File("../../../configuration/docker/server/resources/images");
        String pathImages = fileDir.getCanonicalPath() + "/logoMovistar.jpeg";
        parmeter.put("pathImagesLogo", pathImages);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    @Ignore
    public void testGetReportFreeWiewsPDF() throws Exception {
        System.out.println("getReportFreeWiewsPDF");
        FileOutputStream out = new FileOutputStream(pathHome + File.separator + "ReportFreeView.pdf");
        byte[] result = ifreeViewReportService.getReportExportPDF(pathReport, parmeter, data);
        out.write(result);
        out.close();

    }

    /**
     * Test of getReportFreeWiewsXLS method, of class FreeViewReportServiceImpl.
     */
    @Test
    @Ignore
    public void testGetReportFreeWiewsXLS() throws Exception {
        System.out.println("getReportFreeWiewsXLS");
        FileOutputStream out = new FileOutputStream(pathHome+File.separator+"ReportFreeView.xls");
        byte[] result = ifreeViewReportService.getReportExportXLS(pathReport,parmeter, data);
        out.write(result);
        out.close();
    }

    /**
     * Test of getReportFreeWiewsCSV method, of class FreeViewReportServiceImpl.
     */
    @Test
    @Ignore
    public void testGetReportFreeWiewsCSV() throws Exception {
        System.out.println("getReportFreeWiewsCSV");
        FileOutputStream out = new FileOutputStream(pathHome + File.separator + "ReportFreeView.csv");
        byte[] result = ifreeViewReportService.getReportExportCSV(pathReport, parmeter, data);
        out.write(result);
        out.close();
        System.out.println(new String(result));

    }

}
