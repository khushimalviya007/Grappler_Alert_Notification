package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception;

import org.springframework.http.HttpStatus;

public class CustomException<T> {
    private  boolean status;
    private String message;

    public CustomException(String message , boolean status) {
        this.message=message;
        this.status= status;
    }

    public boolean getStatus(){
        return status;
    }
    public  String getMessage()
    {
        return message;
    }
}
