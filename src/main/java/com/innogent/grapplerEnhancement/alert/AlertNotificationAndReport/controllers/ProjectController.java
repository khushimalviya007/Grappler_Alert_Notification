package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.ProjectRepositary;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectRepositary projectRepositary;

    @Autowired
    ProjectService projectService;

    Logger logger = LoggerFactory.getLogger(ProjectController.class);


    @Operation(summary = "Get all Projects", description = "return all projects")
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProject() {
        try {
            logger.info("Attempting to retrieve all projects.");

            List<ProjectDto> projects = projectService.getAllProject();
            if (!projects.isEmpty()) {
                logger.info("Successfully retrieved all projects.");
                return ResponseEntity.ok(projects);
            } else {
                logger.warn("No projects found.");
                String errorMessage = "Project data not found because no data is present.";
                return new ResponseEntity(new ApiResponse(errorMessage,false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting all projects: " + e.getMessage(), e);
            return new ResponseEntity(new ApiResponse(e.getMessage(),false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a Project by ProjectId", description = "Returns a Project as per the ProjectId")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProject(@Valid @PathVariable("projectId") Long projectId) {
        try {
            logger.info("Attempting to retrieve project with ID: " + projectId);
            ProjectDto projectDto = projectService.getProject(projectId);
            logger.info("Successfully retrieved project with ID: " + projectId);
            return ResponseEntity.ok(projectDto);
        }catch (ResourceNotFoundException e){
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving project with ID " + projectId + ": " + e.getMessage(), e);
            return new ResponseEntity(new ApiResponse(e.getMessage(),false) , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a project", description = "Returns the created project")
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto projectDto) {
        try {
            logger.info("Attempting to create a new project.");
            ProjectDto createdProjectDto = projectService.createProject(projectDto);
            logger.info("Successfully created a new project with ID: " + createdProjectDto.getId());
            return ResponseEntity.ok(createdProjectDto);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity( new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while creating a project: " + e.getMessage());
            return new ResponseEntity( new ApiResponse(e.getMessage(),false), HttpStatus.BAD_REQUEST);
        }
    }




    @Operation(summary = "Delete a project by ProjectID", description = "Returns the deletion status")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@Valid @PathVariable("projectId") Long projectId) {
        try {
            logger.info("Attempting to delete project with ID: " + projectId);

                projectService.deleteProject(projectId);
                logger.info("Successfully deleted project with ID: " + projectId);
                return new ResponseEntity(new ApiResponse("Project with ID " + projectId + " has been successfully deleted.",true),HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            logger.warn(ex.getMessage());
            throw ex;
        }catch (Exception e) {
            // Handle other exceptions (e.g., database connection issues, unexpected errors)
             logger.error( e.getMessage());
            return new ResponseEntity(new ApiResponse(e.getMessage(),false),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Operation(summary = "Update Project", description = "Partially update a project")
    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectDto> partiallyUpdateProject(@Valid @PathVariable("projectId") Long projectId, @RequestBody Project partialProject) {
        try {
            logger.info("Attempting to partially update project with ID: " + projectId);

                ProjectDto updatedProject = projectService.partiallyUpdateProject(projectId, partialProject);
                logger.info("Successfully partially updated project with ID: " + projectId);
                return ResponseEntity.ok(updatedProject);

        }
        catch (ResourceNotFoundException e){
            logger.warn(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            logger.error("An error occurred while partially updating project with ID " + projectId + ": " + e.getMessage(), e);
            return new ResponseEntity(new ApiResponse(e.getMessage(),false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}