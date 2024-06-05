package com.example.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Model.User;
import com.example.Repo.UserRepository;
import com.example.Service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
     
//    @GetMapping("/forReact")
//    public ArrayList<String> Read2() {
//    	ArrayList<String> bookList= new ArrayList();
//    	bookList.add("book1 ");
//    	bookList.add("book2 ");
//    	bookList.add("book3 ");
//    	
//    	return bookList;
//    }
//    
    

    

    @PostMapping("/register")
    public void register(@RequestBody User user) {      
        userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("/profile/{username}")
    public User getProfile(@PathVariable String username) {
        return userService.getProfile(username);
    }
    
    @PutMapping("/deposit/{username}/{amount}")
    public void deposit(@PathVariable String username, double amount) {
        userService.deposit(username, amount);
    }
    
    @PutMapping("/withdraw/{username}/{amount}")
    public String withdraw(@PathVariable String username, @PathVariable double amount) {
        return userService.withdraw(username, amount);
        // Insufficient balance or user not found
    }
    
    @GetMapping("/checkBalance/{username}")
    public double checkBalance(@PathVariable String username) {
       return userService.checkBalance(username);
   }
    
}

