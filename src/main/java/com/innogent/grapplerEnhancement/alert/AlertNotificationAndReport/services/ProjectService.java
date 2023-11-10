package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Rule;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Trigger;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.TicketDto;
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
    @Autowired
    RuleService ruleService;
    @Autowired
    AlertSevice alertSevice;
    @Autowired
    AlertNotiService alertNotiService;

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
        Project project = projectRepositary.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
        projectRepositary.delete(project);
    }


    public ProjectDto partiallyUpdateProject(Long projectId, Project partialProject) {

        Project existingProject = projectRepositary.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));


        if (partialProject.getName() != null)
            existingProject.setName(partialProject.getName());

//        if(partialProject.getUsers()!=null)
//            existingProject.setUsers(partialProject.getUsers());
//
//        if(partialProject.getTickets()!=null)
//            existingProject.setTickets(partialProject.getTickets());

        Project updatedProject = projectRepositary.save(existingProject);
        List<Rule> ruleList = ruleService.getRule(Trigger.EVENT, "Global", "Project", "Name", "Update");
        for (Rule rule : ruleList) {
            alertNotiService.createAlertNoti(null, modelMapper.map(updatedProject, ProjectDto.class), rule);
        }
        return this.modelMapper.map(updatedProject,ProjectDto.class);
    }
}