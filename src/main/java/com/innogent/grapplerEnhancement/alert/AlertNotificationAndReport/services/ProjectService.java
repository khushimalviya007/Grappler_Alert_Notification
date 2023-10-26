package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception.ProjectNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.Exception.UserNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.ProjectRepositary;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    ProjectRepositary projectRepositary;
    Logger logger = LoggerFactory.getLogger(ProjectService.class);

    public ProjectDto getProject(Long projectId) {

        try {
            logger.info("Fetch project By ID Service Called, project Id {}", projectId);
            Optional<ProjectDto> projectDto = projectRepositary.getProjectDto(projectId);
            if (projectDto.isPresent()) {
                return projectDto.get();
            } else {
                logger.error("Fetch project By Id throws ProjectNotFoundException");

                throw new ProjectNotFoundException(
                        "Project Not Found With ID : " + projectId);
            }
        } catch (Exception e) {
            logger.error("Exception in Fetch project By Id Exception {}", e.getMessage());
            throw e;
        }
    }

    public List<ProjectDto> getAllProject() {
        try {
            logger.info("Fetch All projects Service Called");
            Optional<List<ProjectDto>> projectDtoList = projectRepositary.getAllProjectDto();
            if (projectDtoList.isPresent()) {
                logger.info("Fetch All projects Service Returning ProjectDTO");
                return projectDtoList.get();
            } else {
                logger.error("Fetch All projects Service Call ProjectNotFoundException");
                throw new ProjectNotFoundException("projects Not found.");
            }
        } catch (Exception e) {
            logger.info("Exception In Fetch All projects Exception {}", e.getMessage());
            throw e;
        }
    }

    public Optional<ProjectDto> createProject(Project project) {
           Project projectLocal =  projectRepositary.save(project);
        return projectRepositary.getProjectDtoById(projectLocal.getId());
    }

    public void deleteProject(Long projectId) {
        projectRepositary.deleteById(projectId);
    }

    public Project partiallyUpdateProject(Project existingProject, Long projectId, Project partialProject) {

        if (partialProject.getName() != null)
            existingProject.setName(partialProject.getName());

        if (partialProject.getUsers() != null)
            existingProject.setUsers(partialProject.getUsers());

        if (partialProject.getTicketList() != null)
            existingProject.setTicketList(partialProject.getTicketList());

        Project updatedProject = projectRepositary.save(existingProject);
        return updatedProject;
    }
}
