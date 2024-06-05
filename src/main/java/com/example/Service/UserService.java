package com.example.Service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Model.User;
import com.example.Repo.UserRepository;

@CrossOrigin(origins = "*")
@RestController

public class UserService {
    @Autowired
	private UserRepository userRepository;
    

    
    public void register(User user) {
        
        User newUser= new User();
    	newUser.setPassword(user.getPassword());
        newUser.setBalance(user.getBalance());
        newUser.setFirstName(user.getFirstName());
        getUserRepository().save(newUser);
    }

    
    public ResponseEntity<String> login(User user) {
        User existingUser = getUserRepository().findByUsername(user.getUsername());
        if (existingUser != null && user.getPassword().matches(existingUser.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

   
    public User getProfile(String username) {
        return getUserRepository().findByUsername(username);
    }
    
    
    public void deposit(String username, double amount) {
        User user = getUserRepository().findByUsername(username);
        if (user != null) {
            double currentBalance = user.getBalance();
            user.setBalance(currentBalance + amount);
            getUserRepository().save(user);
        }
    }
    
    
    public String withdraw(String username, @PathVariable double amount) {
        User user = getUserRepository().findByUsername(username);
        if (user != null) {
            double currentBalance = user.getBalance();
            if (currentBalance >= amount) {
                user.setBalance(currentBalance - amount);
                getUserRepository().save(user);
                return "Withdraw Successful"; // Withdrawal successful
            }
        }
        return "Insufficient Balance"; // Insufficient balance or user not found
    }
    
   
    public double checkBalance(String username) {
        User user = getUserRepository().findByUsername(username);
        if (user != null) {
            return user.getBalance();
        }
        return 0; // User not found
    }


	public UserRepository getUserRepository() {
		return userRepository;
	}


	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}


