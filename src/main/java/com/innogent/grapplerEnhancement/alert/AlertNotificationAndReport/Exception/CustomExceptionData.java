package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception;

public class CustomExceptionData<T> {
    private  boolean status;
    private String message;
    private T data;

    public CustomExceptionData(String message , boolean status, T data) {
        this.message=message;
        this.status= status;
        this.data=data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
