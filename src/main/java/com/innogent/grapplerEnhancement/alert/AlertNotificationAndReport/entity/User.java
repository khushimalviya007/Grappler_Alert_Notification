package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity;

import jakarta.persistence.*;
import jdk.jfr.Name;

import java.util.List;

@Entity
@Name("user_entity")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false )
    private String name;
    @Column(nullable = false )
    private String password;
    @Column(nullable = false )
    private String email;
    @Column(nullable = false)
    private String phoneNo;
    @ManyToMany
    private List<Notification> listOfNotifications;
    @ManyToMany
    private List<Alert> listOfAlerts;
    @ManyToMany
    private List<Ticket> listOfTickets;

    @ManyToMany(mappedBy = "users")
    private List<Project> listOfProjects;

    public User(Long id, String name, String password, String email, String phoneNo, List<Notification> listOfNotifications, List<Alert> listOfAlerts, List<Ticket> listOfTickets) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNo = phoneNo;
        this.listOfNotifications = listOfNotifications;
        this.listOfAlerts = listOfAlerts;
        this.listOfTickets = listOfTickets;
    }

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public List<Notification> getListOfNotifications() {
        return listOfNotifications;
    }

    public void setListOfNotifications(List<Notification> listOfNotifications) {
        this.listOfNotifications = listOfNotifications;
    }

    public List<Alert> getListOfAlerts() {
        return listOfAlerts;
    }

    public void setListOfAlerts(List<Alert> listOfAlerts) {
        this.listOfAlerts = listOfAlerts;
    }

    public List<Ticket> getListOfTickets() {
        return listOfTickets;
    }

    public void setListOfTickets(List<Ticket> listOfTickets) {
        this.listOfTickets = listOfTickets;
    }

    @Override
    public String toString() {
        return "User_Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", listOfNotifications=" + listOfNotifications +
                ", listOfAlerts=" + listOfAlerts +
                ", listOfTickets=" + listOfTickets +
                '}';
    }
}
