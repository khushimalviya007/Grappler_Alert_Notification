package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.ProjectRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepositary projectRepositary;

    public ResponseEntity<Project> createProject(Project project) {
        return ResponseEntity.ok(projectRepositary.save(project));
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

//    public ResponseEntity<List<Project>> getAllProject(){
//        return ResponseEntity.ok(projectRepositary.findAll());
//    }

//    public ResponseEntity<Object> getProject(Long projectId) {
//        Optional<Project> optionalProject=projectRepositary.findById(projectId);
//        if(optionalProject.isPresent())
//            return ResponseEntity.ok(projectRepositary.findById(projectId));
//        else
//            return ResponseEntity.ok("No Project exist of id : "+projectId);
//    }


}
