package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.ProjectRepositary;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

    Logger logger = LoggerFactory.getLogger(ProjectController.class);


    @Operation(summary = "Get a Project by ProjectId", description = "Returns a Project as per the ProjectId")
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable("projectId") Long projectId) {
        try {
            logger.info("Attempting to retrieve project with ID: " + projectId);

            Optional<Project> optionalProject = projectService.getProject(projectId);
            if (optionalProject.isPresent()) {
                Project project = optionalProject.get();
                logger.info("Successfully retrieved project with ID: " + projectId);
                return ResponseEntity.ok(project);
            } else {
                logger.warn("No project found with ID: " + projectId);
                String errorMessage = "Project with ID " + projectId + " not found.";
                return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while retrieving project with ID " + projectId + ": " + e.getMessage(), e);
            return new ResponseEntity("An error occurred while getting the project. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Get all Projects", description = "return all projects")
    @GetMapping
    public ResponseEntity<List<Project>> getAllProject() {
        try {
            logger.info("Attempting to retrieve all projects.");

            List<Project> projects = projectService.getAllProject();
            if (!projects.isEmpty()) {
                logger.info("Successfully retrieved all projects.");
                return ResponseEntity.ok(projects);
            } else {
                logger.warn("No projects found.");
                String errorMessage = "Project data not found because no data is present.";
                return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting all projects: " + e.getMessage(), e);
            return new ResponseEntity("An error occurred while getting projects.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a project", description = "Returns the created project")
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            logger.info("Attempting to create a new project.");

            if (project.getName() == null || project.getName().trim().isEmpty() ) {
                logger.warn("Project name cannot be null or empty.");
                return new ResponseEntity("Project name cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            Project createdProject = projectService.createProject(project);
            logger.info("Successfully created a new project with ID: " + createdProject.getId());
            return ResponseEntity.ok(createdProject);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity("A project with the same name already exists. " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while creating a project: " + e.getMessage());
            return new ResponseEntity("An error occurred while creating the project. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Operation(summary = "Delete a project by ProjectID", description = "Returns the deletion status")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId) {
        try {
            logger.info("Attempting to delete project with ID: " + projectId);

            Optional<Project> optionalProject = projectRepositary.findById(projectId);
            if (optionalProject.isPresent()) {
                projectService.deleteProject(projectId);
                logger.info("Successfully deleted project with ID: " + projectId);
                return ResponseEntity.ok("Project with ID " + projectId + " has been successfully deleted.");
            }
        } catch (Exception e) {
            // Handle other exceptions (e.g., database connection issues, unexpected errors)
            String errorMessage = "An error occurred while trying to delete the project with ID " + projectId + ": " + e.getMessage();
            logger.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

        String errorMessage = "Project with ID " + projectId + " not found.";
        logger.warn(errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }





    @Operation(summary = "Update Project", description = "Partially update a project")
    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> partiallyUpdateProject(@PathVariable("projectId") Long projectId, @RequestBody Project partialProject) {
        try {
            logger.info("Attempting to partially update project with ID: " + projectId);

            Optional<Project> optionalProject = projectRepositary.findById(projectId);
            if (optionalProject.isPresent()) {
                Project updatedProject = projectService.partiallyUpdateProject(optionalProject.get(), projectId, partialProject);
                logger.info("Successfully partially updated project with ID: " + projectId);
                return ResponseEntity.ok(updatedProject);
            }else{
                String errorMessage = "Project with ID " + projectId + " not found.";
                logger.warn(errorMessage);
                return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("An error occurred while partially updating project with ID " + projectId + ": " + e.getMessage(), e);
            return new ResponseEntity("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
