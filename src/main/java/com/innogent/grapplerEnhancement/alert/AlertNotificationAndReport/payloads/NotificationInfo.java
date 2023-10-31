package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.AlertType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationInfo {

    private Long id;

    @JsonProperty("title") // Map the property to "type" in JSON
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("channels")
    private String channels;

    @JsonProperty("creationDate")
    private LocalDateTime creationDate;

    @JsonProperty("isRead")
    private boolean isRead;


    @JsonProperty("ticketId")
    private Long ticketId;

    @JsonProperty("projectId")
    private Long projectId;

    @JsonProperty("templateId")
    private Long templateId;

    @JsonProperty("ruleId")
    private Long ruleId;
}
