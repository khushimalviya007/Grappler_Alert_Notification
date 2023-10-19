package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Ticket;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.ProjectRepositary;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectRepositary projectRepositary;

    @Autowired
    ProjectService projectService;

//    @Operation(summary = "Get a Project by ProjectId", description = "Returns a Project as per the ProjectId")
//    @GetMapping("/{projectId}")
//    public ResponseEntity<Object> getProject(@PathVariable("projectId")Long projectId){
//        return projectService.getProject(projectId);
//    }


//    @Operation(summary = "Get all Projects", description = "return all projects")
//    @GetMapping
//    public ResponseEntity<List<Project>> getAllProject(){
//        return projectService.getAllProject();
//    }

    @Operation(summary = "Create a project", description = "Returns the created project")
    @PostMapping
    public ResponseEntity<Project> createProject(Project project){
       return projectService.createProject(project);
    }


    @Operation(summary = "Delete a project by ProjectID", description = "Returns the deletion status")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId")Long projectId){
        return projectService.deleteProject(projectId);
    }

    @Operation(summary = "update a project by projectID", description = "Returns the updated project object status")
    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> deleteProject(@PathVariable("projectId")Long projectId, @RequestBody Project project){
        return projectService.updateProject(projectId,project);
    }


}
