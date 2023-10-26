package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception.CustomException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception.CustomExceptionData;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception.ProjectNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception.UserNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Ticket;
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
@CrossOrigin(origins = "*")
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectRepositary projectRepositary;
    @Autowired
    ProjectService projectService;
    Logger logger = LoggerFactory.getLogger(ProjectController.class);


    @Operation(summary = "Get a Project by ProjectId", description = "Returns a Project as per the ProjectId")
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProject(@PathVariable("projectId") Long projectId) {
        try {
            logger.info(" Inside Get Project By Id - {}", projectId);
            ProjectDto projectDto = projectService.getProject(projectId);

            logger.info("Get projects By Id Returning project in ResponseEntity, project Id {} ", projectDto.getId());
            return new ResponseEntity<>(projectDto, HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            logger.error("ProjectNotFoundException in Get Projects BY Id API, Exception {}", e.getMessage());
            return new ResponseEntity<>(new CustomException<>(e.getMessage(),false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Exception In Get Projects By Id API Exception {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all Projects", description = "return all projects")
    @GetMapping
    public ResponseEntity<?> getAllProject() {
        try {
            logger.info("Attempting to retrieve all projects.");
            List<ProjectDto> projects = projectService.getAllProject();

            logger.info("Successfully retrieved all projects.");
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (ProjectNotFoundException e) {
            logger.error("ProjectNotFoundException In Get All Projects API Exception {}", e.getMessage());
            return new ResponseEntity<>(new CustomException<>(e.getMessage(), false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error occurred while getting all projects: " + e.getMessage(), e);
            return new ResponseEntity("An error occurred while getting projects.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a project", description = "Returns the created project")
    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project) {
        try {
            logger.info("Received a request to create a project");
//            if (project.getName() == null || project.getName().trim().isEmpty()) {
//                logger.warn("Project name cannot be null or empty.");
//                return new ResponseEntity<>(new CustomException<>("Project name cannot be null", false), HttpStatus.OK);
//            }
            Optional<ProjectDto> createdProject = projectService.createProject(project);

            if(createdProject.isPresent()){
                logger.info("Successfully created a new project with ID: " + createdProject.get().getId());
                return new ResponseEntity<>(createdProject.get(), HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(new CustomException<>("Project Not Created", false), HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {

            logger.error("An error occurred while creating a project: " + e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Delete a project by ProjectID", description = "Returns the deletion status")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") Long projectId) {
        try {
            logger.info("Attempting to delete project with ID: " + projectId);

            Optional<Project> optionalProject = projectRepositary.findById(projectId);
            if (optionalProject.isPresent()) {
                projectService.deleteProject(projectId);
                logger.info("Successfully deleted project with ID: " + projectId);
                return new ResponseEntity<>(new CustomExceptionData<>("User Deleted Successfully.",false, projectId), HttpStatus.OK);
            }
        } catch (ProjectNotFoundException e) {
            logger.error("ProjectNotFoundException in Delete project BY Id API, Exception {}",e.getMessage());
            return new ResponseEntity<>(new CustomExceptionData<>(e.getMessage(), false,projectId), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            String errorMessage = "An error occurred while trying to delete the project with ID " + projectId + ": " + e.getMessage();
            logger.error(errorMessage, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
        String errorMessage = "Project with ID " + projectId + " not found.";
        logger.warn(errorMessage);
        return new ResponseEntity<>(new CustomExceptionData<>("Project not found", false,projectId), HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Update Project", description = "Partially update a project")
    @PatchMapping("/projects/{projectId}")
    public ResponseEntity<?> partiallyUpdateProject(@PathVariable("projectId") Long projectId, @RequestBody Project partialProject) {
        try {
            logger.info("Attempting to partially update project with ID: " + projectId);
            Optional<Project> optionalProject = projectRepositary.findById(projectId);
            if (optionalProject.isPresent()) {
                Project updatedProject = projectService.partiallyUpdateProject(optionalProject.get(), projectId, partialProject);
                logger.info("Successfully partially updated project with ID: " + projectId);
                return new ResponseEntity<>(new CustomExceptionData<>("User Updated Successfully.",false, updatedProject), HttpStatus.OK);
            } else {
                String errorMessage = "Project with ID " + projectId + " not found.";
                logger.warn(errorMessage);
                return new ResponseEntity<>(new CustomException<>(errorMessage,false), HttpStatus.OK);
            }
        }catch (ProjectNotFoundException e) {
            logger.error("ProjectNotFoundException in Update API, Exception {}",e.getMessage());
            return new ResponseEntity<>(new CustomExceptionData<>(e.getMessage(), false,projectId), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            logger.error("An error occurred while partially updating project with ID " + projectId + ": " + e.getMessage(), e);
            return new ResponseEntity<>(new CustomExceptionData<>("An error occured", false,projectId), HttpStatus.NOT_FOUND);
        }
    }
}
