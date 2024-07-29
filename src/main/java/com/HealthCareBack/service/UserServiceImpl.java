package com.HealthCareBack.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.HealthCareBack.dto.User;
import com.HealthCareBack.exception.UserNotFoundByIdException;
import com.HealthCareBack.repo.UserRepositiry;
import com.HealthCareBack.securityConfig.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepositiry userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUser(long id) {
        return userRepo.findById(id);
    }

   

	@Override
	public Optional<User> deleteUser(long id) {
		Optional<User> u1=userRepo.findById(id);
		if(u1.isEmpty()) {
			throw new UserNotFoundByIdException("User Not Found!!!");
		}else {
			User u=u1.get();
			userRepo.deleteById(id);
			return u1;
		}
		
	}

	@Override
	public ResponseEntity<ResponseStructure<User>> updateUser(User user,long id) {
		Optional<User> u1=userRepo.findById(id);
		if(u1.isEmpty()) {
			throw new UserNotFoundByIdException("User Not Found");
		}else {
			user.setId(id);
			User user1=userRepo.save(user);
			ResponseStructure<User> responseStructure=new ResponseStructure<>();
			responseStructure.setData(user1);
			responseStructure.setMessage("User Updated Successfully!!");
			responseStructure.setStatus(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<User>>(responseStructure,HttpStatus.OK);
		}
	}
}
