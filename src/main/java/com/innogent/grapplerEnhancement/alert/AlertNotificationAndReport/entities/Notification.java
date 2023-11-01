package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

//    @Column(nullable = false )
    private LocalDateTime creationDate;

    @Column(nullable = false ,columnDefinition = "boolean default false")
    private boolean isRead;

    @ManyToMany
    private List<User> userList;

    @OneToOne
    private Template template;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private Project project;


    @ManyToOne
    private Rule rule;
}
