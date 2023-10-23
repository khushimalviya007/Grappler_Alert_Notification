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

    public UserDto createUser(UserDto userDto){
        User user=this.dtoToUser(userDto);
        User savedUser= this.userRepositary.save(user);
        return this.userToDto(savedUser);
    }
    public UserDto updateUser(UserDto userDto,Long userId){
        User user = this.userRepositary.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", " id ", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNo(userDto.getPhoneNo());
        user.setPassword(user.getPassword());

        User updatedUser = this.userRepositary.save(user);
        UserDto userDtoupdated= this.userToDto(updatedUser);
        return userDtoupdated;
    }
    public UserDto getUserById(Long userId){
        User user= this.userRepositary.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id ",userId));
        return this.userToDto(user);
    }
    public List<UserDto> getAllUsers(){
        List<User> users=this.userRepositary.findAll();
        List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }
    public void deleteUser(Long userId){
        User user = this.userRepositary.findById(userId).orElseThrow(()->new ResourceNotFoundException("User" ," id ",userId));
        this.userRepositary.delete(user);

    }

    private User dtoToUser(UserDto userDto){
        User user= this.modelMapper.map(userDto,User.class);
//                new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setPhoneNo(userDto.getPhoneNo());
        return user;
    }
    private UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
//                new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setPhoneNo(user.getPhoneNo());
        return userDto;
    }
}
