package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception;

public class ProjectNotFoundException extends RuntimeException{
    private String message;
    public ProjectNotFoundException(String message) {
        this.message=message;
    }
    public String getMessage() {
        return message;
    }
}
