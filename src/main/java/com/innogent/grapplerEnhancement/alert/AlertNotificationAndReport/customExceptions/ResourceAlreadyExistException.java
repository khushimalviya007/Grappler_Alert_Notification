package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ResourceAlreadyExistException extends RuntimeException {
    String resourceName;
    String fieldName;
    long fieldValue;
    public ResourceAlreadyExistException(String resourceName,String fieldName,long fieldValue){
        super(String.format("%s already exist with %s : %s",resourceName,fieldName,fieldValue));
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }


}

