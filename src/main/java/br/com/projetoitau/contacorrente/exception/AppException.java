package br.com.projetoitau.contacorrente.exception;

import java.util.Date;

public class AppException {

    private String message;
    private Integer errorCode;
    private long timestamp;

    public AppException(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = new Date().getTime();
    }

    public AppException(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getCode();
        this.timestamp = new Date().getTime();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
