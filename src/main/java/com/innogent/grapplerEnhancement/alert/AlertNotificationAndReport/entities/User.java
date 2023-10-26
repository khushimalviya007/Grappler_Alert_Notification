package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.Name;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Name("users")
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

    @ManyToMany(mappedBy = "listOfUser")
    private List<Notification> notifications;

    @ManyToMany
    private List<Alert> alerts;

    @ManyToMany(mappedBy = "assignees")
    private List<Ticket> ticketsAssigned;

    @ManyToMany(mappedBy = "users")
    private List<Project> projectsAssigned;

}
