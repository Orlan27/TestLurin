package com.zed.lurin.domain.dto;

public class MessageCustomDTO {
    /**
     * is error method
     */
    private boolean success;
    /**
     * Code Error Custom
     */
    private String codeError;
    /**
     * Message Error Custom
     */
    private String keyMessage;

    private String message;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCodeError() {
        return codeError;
    }

    public void setCodeError(String codeError) {
        this.codeError = codeError;
    }

    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum MESSAGES_KEYS{
       USER_IS_LPAD,
       USER_OLD_PASSWORD_INCORRECT
    }

    public enum MESSAGES_CODE{
        USER_IS_LPAD{
            @Override
            public String toString() {
                return "EC_001";
            }
        },
        USER_OLD_PASSWORD_INCORRECT{
            @Override
            public String toString() {
                return "EC_002";
            }
        }


    }
}
