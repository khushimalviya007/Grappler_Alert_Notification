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
@NoArgsConstructor
@AllArgsConstructor
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

//    @Column(nullable = false )
    private String creationDate;

    private String response;
    @ManyToMany(mappedBy = "alerts")
    private List<User> users;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private Project project;

    @OneToOne
    private Template template;

    @ManyToOne
    private Rule rule;


}
