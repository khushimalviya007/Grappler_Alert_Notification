package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Operation(summary = "Get a Project by ProjectId", description = "Returns a Project as per the ProjectId")
    @GetMapping("/project/{projectId}")
    public String getProject(@PathVariable("projectId")Long projectId){
        return "this is your project of id : "+projectId;
    }



}
