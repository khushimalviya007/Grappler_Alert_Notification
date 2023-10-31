package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.User;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.LoginDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.UserDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.UserRepositary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserRepositary userRepositary;

    @Autowired
    ModelMapper modelMapper;

    public UserDto startLogin(LoginDto loginDto) {
        User user=userRepositary.findByEmailAndPassword(loginDto.getEmail(),loginDto.getPassword());
        return modelMapper.map(user, UserDto.class);

    }
}
