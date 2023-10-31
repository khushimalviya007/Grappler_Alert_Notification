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
@Table(name = "Alert")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private AlertType type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String channels;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime creationDate; // Update the field type to LocalDateTime

    private String response;

    @ManyToMany
    private List<User> userList;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private Project project;

    @OneToOne
    private Template template;

    @ManyToOne
    private Rule rule;
}
