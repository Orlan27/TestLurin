package com.zed.campaign.rest.keys;

public enum ResponseCasCustom {

    NUMBERS_ROWS_EXCEED(4001, "Number of records in the file exceeds the maximum allowed");

    private final int code;
    private final String reason;

    ResponseCasCustom(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
