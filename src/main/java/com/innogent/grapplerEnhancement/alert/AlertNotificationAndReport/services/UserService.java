package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.User;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.UserRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepositary userRepositary;
    public ResponseEntity<User> createUser(User user) {
        return ResponseEntity.ok(userRepositary.save(user));

    }

    public ResponseEntity<String> deleteUser(Long userId) {
        return ResponseEntity.ok("User is deleted of id : "+userId);

    }

    public ResponseEntity<User> updateUser(Long userId, User user) {
        return ResponseEntity.ok(userRepositary.findById(userId).get());

    }
}
