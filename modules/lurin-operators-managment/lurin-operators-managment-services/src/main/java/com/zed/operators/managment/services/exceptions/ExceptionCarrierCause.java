package com.zed.operators.managment.services.exceptions;

public class ExceptionCarrierCause extends Exception {


    private CodeExceptionCarrier codes;

    private String message;

    private String keyMessage;

    private String[] params;


    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     */
    public ExceptionCarrierCause(CodeExceptionCarrier codes) {
        super();
        this.codes=codes;
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     */
    public ExceptionCarrierCause(CodeExceptionCarrier codes, String... params) {
        super();
        this.codes=codes;
        this.params = params;
    }


    @Override
    public String getMessage() {
        
        switch (codes){
            case USER_EXIST:
                if(params.length>0){
                    this.setMessage( String.format("UserName %s that exist", params[0]));
                }else{
                    this.setMessage("UserName that exist");
                }
                this.setKeyMessage(CodeExceptionCarrier.USER_EXIST.toString());
                break;
            case PERCENTAGE_EXCEEDED:
                this.setMessage("the percentage shared between operators has been exceeded");
                this.setKeyMessage(CodeExceptionCarrier.PERCENTAGE_EXCEEDED.toString());
                break;
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }

    public CodeExceptionCarrier getCodes() {
        return codes;
    }

    public void setCodes(CodeExceptionCarrier codes) {
        this.codes = codes;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
