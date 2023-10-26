package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.User;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.UserDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.UserRepositary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepositary userRepositary;

    @Autowired
    ModelMapper modelMapper;


    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepositary.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

        public UserDto getUserById(Long userId){
            User user= this.userRepositary.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id ",userId));
            return this.userToDto(user);
        }

    public UserDto createUser(UserDto userDto){
        User user=this.dtoToUser(userDto);
        User savedUser=userRepositary.save(user);
        return this.userToDto(savedUser);
    }



    public void deleteUser(Long userId){
        User user = this.userRepositary.findById(userId).orElseThrow(()->new ResourceNotFoundException("User" ," id ",userId));
        this.userRepositary.delete(user);

    }


    public UserDto updateUser(Long userId,UserDto userDto){
        User user = this.userRepositary.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", " id ", userId));

        if (userDto.getName() != null)
            user.setName(userDto.getName());

        if(userDto.getEmail() != null)
            user.setEmail(userDto.getEmail());

        if(userDto.getPhoneNo() != null)
            user.setPhoneNo(userDto.getPhoneNo());

        if(userDto.getPassword() != null)
            user.setPassword(userDto.getPassword());

        User updatedUser = this.userRepositary.save(user);
        UserDto userDtoUpdated= this.userToDto(updatedUser);
        return userDtoUpdated;
    }

    private User dtoToUser(UserDto userDto){
        User user= this.modelMapper.map(userDto,User.class);
        return user;
    }
    private UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
