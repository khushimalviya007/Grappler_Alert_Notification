package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToMany(mappedBy = "ticketsAssigned")
    List<User> assignees;

    @ManyToOne(optional = false)
    private User assigneedBy;

    @Column(nullable = false)
    private String creationDate;

    private String endDate;

    @ManyToOne(optional = false)
    private Project project;


}
