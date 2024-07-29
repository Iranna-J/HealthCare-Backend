package com.HealthCareBack.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.HealthCareBack.dto.User;
import com.HealthCareBack.securityConfig.ResponseStructure;

public interface UserService {

	User createUser(User  user);

	Optional<User> getUser(long id);

	Optional<User> deleteUser(long id);


	ResponseEntity<ResponseStructure<User>> updateUser(User user, long id);



}
