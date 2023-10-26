package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Stages stage = Stages.TODO;
    @ManyToMany(mappedBy = "listOfTickets")
    @JsonIgnore
    List<User> assigness;
    @ManyToOne
    private User assignedBy;
    @Column(nullable = false)
    private String creationDate;
    @Column(nullable = false)
    private String endDate;
    @ManyToOne
    @JoinColumn(name = "project_id")
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
        return assigness;
    }

    public void setListOfUser(List<User> listOfUser) {
        this.assigness = listOfUser;
    }

    public User getCreator() {
        return assignedBy;
    }

    public void setCreator(User creator) {
        this.assignedBy = creator;
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

    public void setProjectEntity(Project project) {
        this.project = project;
    }

    public Ticket(Long id, String name, Stages stage, List<User> listOfUser, User creator, String creationDate, String endDate, Project project) {
        this.id = id;
        this.name = name;
        this.stage = stage;
        this.assigness = listOfUser;
        this.assignedBy = creator;
        this.creationDate = creationDate;
        this.endDate = endDate;
        this.project = project;
    }

    public Ticket() {
    }
//    @Override
//    public String toString() {
//        return "Ticket_Entity{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", stage=" + stage +
//                ", asignees=" + assigness +
//                ", assigneedBy=" + assignedBy +
//                ", creationDate='" + creationDate + '\'' +
//                ", endDate='" + endDate + '\'' +
//                ", project=" + project +
//                '}';
//    }
}
