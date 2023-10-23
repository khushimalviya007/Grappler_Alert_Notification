package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false )
    private Stages stage=Stages.TODO;
    @ManyToMany(mappedBy = "ticketsAssigned")
    List<User> assignees;
    @ManyToOne(optional = false)
    private User assigneedBy;

    @Column(nullable = false)
    private String creationDate;
    @Column(nullable = false)
    private String endDate;
    @ManyToOne(optional = false)
    private Project project;

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

    public Stages getStage() {
        return stage;
    }

    public void setStage(Stages stage) {
        this.stage = stage;
    }

    public List<User> getListOfUser() {
        return assignees;
    }

    public void setListOfUser(List<User> listOfUser) {
        this.assignees = listOfUser;
    }

    public User getCreator() {
        return assigneedBy;
    }

    public void setCreator(User creator) {
        this.assigneedBy = creator;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Project getProjectEntity() {
        return project;
    }

    public void setProjectEntity(Project projectEntity) {
        this.project = projectEntity;
    }

    public Ticket(Long id, String name, Stages stage, List<User> listOfUser, User creator, String creationDate, String endDate, Project projectEntity) {
        this.id = id;
        this.name = name;
        this.stage = stage;
        this.assignees = listOfUser;
        this.assigneedBy = creator;
        this.creationDate = creationDate;
        this.endDate = endDate;
        this.project = projectEntity;
    }

    public Ticket() {
    }

    @Override
    public String toString() {
        return "Ticket_Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stage=" + stage +
                ", assignees=" + assignees +
                ", assigneedBy=" + assigneedBy +
                ", creationDate='" + creationDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", project=" + project +
                '}';
    }
}
