package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private Sources sources;

    @Column(nullable = false)
    private String scope;
    @Column(nullable = false)
    private String identity;

    @Column(nullable = false ,name="trigger_field")
    private String trigger;
    @Column(nullable = false ,name = "trigger_condition")
    private String condition;

    @Column(nullable = false)
    private String action;
    @Column(nullable = false)
    private String desription;
    @Column(nullable = false)
    private String severity;
    @Column(nullable = false)
    private String recepientDescription;
    @Column(nullable = false)
    private String channel;
    @Column(nullable = false)
    private LocalDateTime creationDate;
    @Column(nullable = false)
    private boolean isEnabled;
    @Column(nullable = false)
    private boolean isDeleted;

    public Rule(int id,Sources sources, String scope, String identity, String trigger, String condition, Boolean flag, String action, String desription, String severity, String recepientDescription, String channel, LocalDateTime creationDate, boolean isEnabled, boolean isDeleted) {
        this.id = id;
        this.sources =sources ;
        this.scope = scope;
        this.identity = identity;
        this.trigger = trigger;
        this.condition = condition;
        this.action = action;
        this.desription = desription;
        this.severity = severity;
        this.recepientDescription = recepientDescription;
        this.channel = channel;
        this.creationDate = creationDate;
        this.isEnabled = isEnabled;
        this.isDeleted = isDeleted;
    }

    public Rule() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getRecepientDescription() {
        return recepientDescription;
    }

    public void setRecepientDescription(String recepientDescription) {
        this.recepientDescription = recepientDescription;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Sources getSources() {
        return sources;
    }

    public void setSources(Sources sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", sources=" + sources +
                ", scope='" + scope + '\'' +
                ", identity='" + identity + '\'' +
                ", trigger='" + trigger + '\'' +
                ", condition='" + condition + '\'' +
                ", action='" + action + '\'' +
                ", desription='" + desription + '\'' +
                ", severity='" + severity + '\'' +
                ", recepientDescription='" + recepientDescription + '\'' +
                ", channel='" + channel + '\'' +
                ", creationDate=" + creationDate +
                ", isEnabled=" + isEnabled +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
