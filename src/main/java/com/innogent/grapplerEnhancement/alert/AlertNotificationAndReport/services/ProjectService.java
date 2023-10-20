package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.ProjectRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepositary projectRepositary;

    public ResponseEntity<Project> createProject(Project project) {
        return new  ResponseEntity<Project>(projectRepositary.save(project), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteProject(Long projectId) {
        return ResponseEntity.ok("Project is deleted of id : "+projectId);
    }

    public ResponseEntity<Project> updateProject(Long projectId, Project project) {
        Optional<Project> optionalProject=projectRepositary.findById(projectId);
//        if(optionalProject.isPresent())
           return ResponseEntity.ok(projectRepositary.findById(projectId).get());
//        else
//            return ResponseEntity.ok("No Project exist of id : "+projectId);
//        projectRepositary.save(project);
//        return
    }

    public List<Project> getAllProject(){
        return projectRepositary.findAll();
    }

    public Project getProjectById(Long projectId) {
        Optional<Project> optionalProject=projectRepositary.findById(projectId);
        if(optionalProject.isPresent())
            return projectRepositary.findById(projectId).get();
        else
            return null;
    }


}
