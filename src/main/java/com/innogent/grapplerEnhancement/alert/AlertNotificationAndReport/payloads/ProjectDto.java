package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

public class ProjectDto {
    private Long id;
    private String name;
    public ProjectDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public ProjectDto() {
    }
    @Override
    public String toString() {
        return "ProjectDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
