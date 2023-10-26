package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class TriggerOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String entity;
    @Column(nullable = false)
    private String field;
    @Column(nullable = false)
    private String condition;

}
