package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception;

public class ResourceNotFoundException extends RuntimeException {
private String message;
        public ResourceNotFoundException(String message) {
            this.message=message;
        }
    public String getMessage() {
            return message;
    }
}