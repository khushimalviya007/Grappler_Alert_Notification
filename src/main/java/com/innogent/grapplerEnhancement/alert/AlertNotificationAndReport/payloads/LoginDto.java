package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LoginDto {

    @Email
    @NotBlank(message = "email is mandatory field")
    private String email;

    @NotBlank(message = "Password is mandatory field")
//    @Size(min=3,max = 10,message = "password must be min 3 chars and less than 10 chars")
    private String password;

}
