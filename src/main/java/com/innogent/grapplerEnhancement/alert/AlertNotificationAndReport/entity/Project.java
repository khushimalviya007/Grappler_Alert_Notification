package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "projectEntity")
    private List<Ticket> ticketList;

    @ManyToMany
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }


    public Project() {
    }

    public Project(Long id, String name, List<Ticket> ticketList, List<User> users) {
        this.id = id;
        this.name = name;
        this.ticketList = ticketList;
        this.users = users;
    }

    @Override
    public String toString() {
        return "Project_Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ticketList=" + ticketList +
                '}';
    }
}


