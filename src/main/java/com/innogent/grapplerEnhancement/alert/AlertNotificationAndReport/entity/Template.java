package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity;

import jakarta.persistence.*;

@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String description;
}
