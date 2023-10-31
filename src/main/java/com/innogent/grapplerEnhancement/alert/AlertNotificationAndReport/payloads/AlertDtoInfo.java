package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.AlertType;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlertDtoInfo {

        private Long id;

        @JsonProperty("type") // Map the property to "type" in JSON
        private AlertType type;

        @JsonProperty("description") // Map the property to "description" in JSON
        private String description;

        @JsonProperty("channels") // Map the property to "channels" in JSON
        private String channels;

        @JsonProperty("creationDate") // Map the property to "dateAndTime" in JSON
        private LocalDateTime creationDate;

        @JsonProperty("response") // Map the property to "response" in JSON
        private String response;

//        @JsonProperty("userIdList")
//        private List<Long> userIdList;

        @JsonProperty("ticketId") // Map the property to "ticketId" in JSON
        private Long ticketId;

        @JsonProperty("projectId") // Map the property to "projectId" in JSON
        private Long projectId;

        @JsonProperty("templateId") // Map the property to "templateId" in JSON
        private Long templateId;

        @JsonProperty("ruleId") // Map the property to "ruleId" in JSON
        private Long ruleId;
}
