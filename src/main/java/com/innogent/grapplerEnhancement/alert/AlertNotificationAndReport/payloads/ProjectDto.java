package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProjectDto {
    private Long id;
    @NotBlank(message = "Name of project  cannot be Blank")
    private String name;

}
