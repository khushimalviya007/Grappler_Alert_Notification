package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.ProjectRepositary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepositary projectRepositary;
    Logger logger = LoggerFactory.getLogger(ProjectService.class);

    public Optional<Project> getProject(Long projectId) {
        return projectRepositary.findById(projectId);
    }



    public List<Project> getAllProject() {
        return  projectRepositary.findAll();
    }

    public Project createProject(Project project) {
        return projectRepositary.save(project);

    }


    public void deleteProject(Long projectId) {
        projectRepositary.deleteById(projectId);
    }


    public Project partiallyUpdateProject(Project existingProject,Long projectId, Project partialProject) {

        if (partialProject.getName() != null)
            existingProject.setName(partialProject.getName());

        if(partialProject.getUsers()!=null)
            existingProject.setUsers(partialProject.getUsers());

        if(partialProject.getTicketList()!=null)
            existingProject.setTicketList(partialProject.getTicketList());

        return projectRepositary.save(existingProject);
    }







}
