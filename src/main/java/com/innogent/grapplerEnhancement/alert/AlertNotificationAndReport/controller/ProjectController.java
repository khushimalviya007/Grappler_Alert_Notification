package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
//@RequestMapping("/api")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Operation(summary = "Get a Project by ProjectId", description = "Returns a Project as per the ProjectId")
    @GetMapping("/project/{projectId}")
    public ResponseEntity<Object> getProject(@PathVariable("projectId")Long projectId){
        Optional<Project> optionalProject=projectRepository.findById(projectId);
        if(optionalProject.isPresent())
            return ResponseEntity.ok(projectRepository.findById(projectId));
        else
            return ResponseEntity.ok("No Project exist of id : "+projectId);
    }

    @Operation(summary = "Get all Projects", description = "return all projects")
    @GetMapping("/project")
    public ResponseEntity<List<Project>> getAllProject(){
        return ResponseEntity.ok(projectRepository.findAll());
    }

    @Operation(summary = "Create a project", description = "Returns the created project")
    @PostMapping("/project")
    public ResponseEntity<Project> createProject(Project project){
        return ResponseEntity.ok(projectRepository.save(project));
    }


    @Operation(summary = "Delete a project by ProjectID", description = "Returns the deletion status")
    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId")Long projectId){
        return ResponseEntity.ok("Project is deleted of id : "+projectId);
    }


}
