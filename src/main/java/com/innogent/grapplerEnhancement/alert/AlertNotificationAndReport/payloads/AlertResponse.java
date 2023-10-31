package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.AlertType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlertResponse {
//    @NotEmpty(message = "id field is mandatory")
//    private Long id;
    @NotEmpty(message = "response field is mandatory")
    private String response;

}



