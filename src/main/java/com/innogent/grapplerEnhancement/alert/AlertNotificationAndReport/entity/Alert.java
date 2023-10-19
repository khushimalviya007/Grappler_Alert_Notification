package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false )
    private AlertType type;
    @Column(nullable = false )
    private String description;
    @Column(nullable = false )
    private String channels;
    @Column(nullable = false )
    private LocalDateTime dateAndTime;
    private String response;
    @ManyToMany(mappedBy = "listOfAlerts")
    private List<User> listOfUsers;
    @ManyToOne
    private Ticket ticket;
    @ManyToOne
    private Project project;
    @OneToOne
    private Template template;
    @ManyToOne
    private Rule rule;

    public Alert(Long id, AlertType type, String description, String channels, LocalDateTime dateAndTime, String response, List<User> listOfUsers, Ticket ticket, Project project, Template template, Rule rule) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.channels = channels;
        this.dateAndTime = dateAndTime;
        this.response = response;
        this.listOfUsers = listOfUsers;
        this.ticket = ticket;
        this.project = project;
        this.template = template;
        this.rule = rule;
    }

    public Alert() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(List<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }


}
