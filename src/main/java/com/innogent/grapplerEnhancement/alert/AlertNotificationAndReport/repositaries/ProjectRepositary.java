package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepositary extends JpaRepository<Project,Long> {

    @Query("SELECT NEW com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto(e.id, e.name) " +
            "FROM Project e WHERE e.id = :projectId")
    Optional<ProjectDto> getProjectDto(Long projectId);

    @Query("SELECT NEW com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto(e.id, e.name) " +
            "FROM Project e WHERE e.id = :projectId")
    Optional<ProjectDto> getProjectDtoById(Long projectId);

    @Query("SELECT NEW com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ProjectDto(e.id, e.name) "  +
            "FROM Project e")
    Optional<List<ProjectDto>> getAllProjectDto();
}