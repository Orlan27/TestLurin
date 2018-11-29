
package com.zed.lurin.process.cas.command;

/**
 *
 * @author Llince
 */
@Deprecated
public class CommandCas {
private static final String transactionId="transaction_id:";
private static final String sourceId="source_id:";
private static final String destId="dest_id:";
private static final String callConnection="command_id:1002";
private static final String circuitId="circuit_id:";
private static final String iMSProductId="IMS_product_id:";
private static final String mopPpid="MOP_PPID:"; 
private static final String sMSProductId="SMS_product_id:"; 
private static final String sTUNumber="STU_number:"; 
private static final String addProduct="command_id:0002";
private static final String suspensionProduct="command_id:0004";
private static final String reactivationProduct="command_id:0005";
private static final String cancellationProduct="command_id:0006";
private static final String alLcancellationProduct="command_id:0007";
private static final String beginDate="begin_date:"; 
private static final String endDate="end_date:";
private static final String formatDate="YYYYMMDD";

    public static String getTransactionId() {
        return transactionId;
    }

    public static String getSourceId() {
        return sourceId;
    }

    public static String getDestId() {
        return destId;
    }

    public static String getCallConnection() {
        return callConnection;
    }

    public static String getCircuitId() {
        return circuitId;
    }

    public static String getiMSProductId() {
        return iMSProductId;
    }

    public static String getMopPpid() {
        return mopPpid;
    }

    public static String getsMSProductId() {
        return sMSProductId;
    }

    public static String getsTUNumber() {
        return sTUNumber;
    }

    public static String getAddProduct() {
        return addProduct;
    }

    public static String getSuspensionProduct() {
        return suspensionProduct;
    }

    public static String getReactivationProduct() {
        return reactivationProduct;
    }

    public static String getCancellationProduct() {
        return cancellationProduct;
    }

    public static String getAlLcancellationProduct() {
        return alLcancellationProduct;
    }

    public static String getBeginDate() {
        return beginDate;
    }

    public static String getEndDate() {
        return endDate;
    }

    public static String getFormatDate() {
        return formatDate;
    }

}
