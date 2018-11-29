package com.zed.lurin.domain.dto;

import java.util.List;

/**
 * <p>Data Transfer Object for manage the test Connection</p>
 * @author Francisco Lujano
 */
public class MessageTestConnectionDTO {
    /**
     * Return if the connection is valid
     */
    private boolean valid;
    /**
     * Return the message key as string
     */
    private String message;

    /**
     * Parameters to concatenate to the string key
     */
    private List<String> params;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public enum MESSAGES_KEYS{
        JDBC_DRIVER_IS_NOT_FOUND,
        SUCCESSFUL_CONNECTION,
        CONNECTION_FAILED,
        UNKNOWN_HOST,
        CONNECTION_FAILED_ONLY_ONE,
        CONNECTION_FAILED_MORE_THAN_ONE,
        INVALID_USERNAME_PASSWORD,
        INVALID_SID
    }
}
