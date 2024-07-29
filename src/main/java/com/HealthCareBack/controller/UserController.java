package com.HealthCareBack.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HealthCareBack.dto.User;
import com.HealthCareBack.securityConfig.ResponseStructure;
import com.HealthCareBack.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
    	return userService.createUser(user);
    }
    @GetMapping
    public Optional<User> getUser(@RequestParam long id) {
    	return userService.getUser(id);
    }
    @DeleteMapping
    public Optional<User> deleteUser(@RequestParam long id) {
    	return userService.deleteUser(id);
    }
    
    @PutMapping
    public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody User user,@RequestParam long id) {
		return userService.updateUser(user, id);
	

}
}
