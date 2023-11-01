package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Alert;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Notification;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Project;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

//    @Pattern(regexp = "\\d+",message = "must be no only")
    private Long id;
    @NotBlank(message = "Name is mandatory field")
//    @Size(min=4,message = "Username must be min 4 characters !!")
    private String name;

    @NotBlank(message = "Password is mandatory field")
//    @Size(min=3,max = 10,message = "password must be min 3 chars and less than 10 chars")
    private String password;
    @Email
    @NotBlank(message = "email is mandatory field")
    private String email;

    @NotBlank(message = "phone no is mandatory field")
    private String phoneNo;

}
