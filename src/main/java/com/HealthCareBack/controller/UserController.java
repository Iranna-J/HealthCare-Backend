package com.HealthCareBack.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.HealthCareBack.util.JWTUtil;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JWTUtil jwtUtil; // Inject JWTUtil

    @PostMapping
    public ResponseEntity<ResponseStructure<String>> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        String token = jwtUtil.generateToken(createdUser); // Generate JWT token

        // Wrap token in ResponseStructure
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setMessage("User created successfully");
        response.setStatus(HttpStatus.CREATED.value());
        response.setData(token);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<Optional<User>>> getUser(@RequestParam long id) {
        Optional<User> user = userService.getUser(id);

        ResponseStructure<Optional<User>> response = new ResponseStructure<>();
        response.setMessage(user.isPresent() ? "User found" : "User not found");
        response.setStatus(HttpStatus.OK.value());
        response.setData(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseStructure<Optional<User>>> deleteUser(@RequestParam long id) {
        Optional<User> deletedUser = userService.deleteUser(id);

        ResponseStructure<Optional<User>> response = new ResponseStructure<>();
        response.setMessage(deletedUser.isPresent() ? "User deleted successfully" : "User not found");
        response.setStatus(HttpStatus.OK.value());
        response.setData(deletedUser);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody User user, @RequestParam long id) {
        ResponseEntity<ResponseStructure<User>> updatedUser = userService.updateUser(user, id);
        return updatedUser;
    }
}
