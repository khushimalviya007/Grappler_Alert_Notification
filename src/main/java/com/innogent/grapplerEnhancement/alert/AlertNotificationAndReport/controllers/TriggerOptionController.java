package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.TriggerOptions;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.TriggerOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/triggerOptions")
public class TriggerOptionController {
    @Autowired
    private TriggerOptionsService triggerOptionsService;
    @GetMapping
    public ResponseEntity<List<TriggerOptions>> getAll(){
        List<TriggerOptions> allTriggers = this.triggerOptionsService.getAllTriggers();
        if(allTriggers.isEmpty() || allTriggers==null)
            return new ResponseEntity(new ApiResponse("no data available",false),HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<TriggerOptions>>(allTriggers, HttpStatus.OK);
    }
}
