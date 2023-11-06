package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false )
    private Stages stage=Stages.TODO;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime creationDate;

    private String endDate;


    @ManyToMany
    List<User> assignees;

    @ManyToOne(optional = false)
    private User assignedBy;

    @ManyToOne(optional = false)
    private Project project;
}
