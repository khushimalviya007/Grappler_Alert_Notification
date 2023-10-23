package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.ProjectRepositary;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    ProjectRepositary projectRepositary;
    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(ProjectService.class);

    public ProjectDto createProject(ProjectDto projectDto) {
        Project project=this.modelMapper.map(projectDto,Project.class);

        projectRepositary.save(project);

        return this.modelMapper.map(project,ProjectDto.class);
    }
    public ProjectDto getProject(Long projectId) {

        Project project = projectRepositary.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
        return this.modelMapper.map(project,ProjectDto.class);

    }



    public List<ProjectDto> getAllProject() {
        List<Project> allProjects = projectRepositary.findAll();
        return allProjects.stream().map((project) -> this.modelMapper.map(project,ProjectDto.class)).collect(Collectors.toList());
    }



    public void deleteProject(Long projectId) {
        projectRepositary.deleteById(projectId);
    }


    public Project partiallyUpdateProject(Project existingProject,Long projectId, Project partialProject) {

        if (partialProject.getName() != null)
            existingProject.setName(partialProject.getName());

        if(partialProject.getUsers()!=null)
            existingProject.setUsers(partialProject.getUsers());

        if(partialProject.getTickets()!=null)
            existingProject.setTickets(partialProject.getTickets());

        Project updatedProject = projectRepositary.save(existingProject);
        return updatedProject;
    }

}