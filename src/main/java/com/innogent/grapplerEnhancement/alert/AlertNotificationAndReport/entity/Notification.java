package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false )
    private String title;
    @Column(nullable = false )
    private String description;
    @Column(nullable = false )
    private String channels;
    @Column(nullable = false )
    private LocalDateTime dateAndTime;
    @Column(nullable = false ,columnDefinition = "boolean default false")
    private boolean isRead;
    @ManyToMany(mappedBy = "listOfNotifications")
    private List<User> listOfUser;
    @OneToOne
    private Template template;
    @ManyToOne
    private Ticket ticket;
    @ManyToOne
    private Project project;

    @ManyToOne
    private Rule rule;

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public Notification(Long id, String title, String description, String channels, LocalDateTime dateAndTime, boolean isRead, List<User> listOfUser, Template template, Ticket ticket, Project project, Rule rule) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.channels = channels;
        this.dateAndTime = dateAndTime;
        this.isRead = isRead;
        this.listOfUser = listOfUser;
        this.template = template;
        this.ticket = ticket;
        this.project = project;
        this.rule = rule;
    }

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public List<User> getListOfUser() {
        return listOfUser;
    }

    public void setListOfUser(List<User> listOfUser) {
        this.listOfUser = listOfUser;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
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

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", channels='" + channels + '\'' +
                ", dateAndTime=" + dateAndTime +
                ", isRead=" + isRead +
                ", listOfUser=" + listOfUser +
                ", template=" + template +
                ", ticket=" + ticket +
                ", project=" + project +
                ", rule=" + rule +
                '}';
    }
}