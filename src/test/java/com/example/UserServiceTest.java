package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.Model.User;
import com.example.Repo.UserRepository;
import com.example.Service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private User user2;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("mansi");
        user.setFirstName("Mansi");
        user.setLastName("Sonavane");
        user.setCity("Pune");
        user.setPassword("1234");
        user.setBalance(1000);

        user2 = new User();
        user2.setId(2);
        user2.setUsername("john");
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setCity("Mumbai");
        user2.setPassword("abcd");
        user2.setBalance(500);
    }

    @Test
    public void testRegister() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.register(user);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLoginSuccess() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        ResponseEntity<String> response = userService.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody());
    }

    @Test
    public void testLoginFailure() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        ResponseEntity<String> response = userService.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    public void testGetProfile() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        User foundUser = userService.getProfile("mansi");

        assertNotNull(foundUser);
        assertEquals("mansi", foundUser.getUsername());
    }

    @Test
    public void testDeposit() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        userService.deposit("mansi", 500);

        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(1500, user.getBalance());
    }

    @Test
    public void testWithdrawSuccess() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        String response = userService.withdraw("mansi", 500);

        assertEquals("Withdraw Successful", response);
        assertEquals(500, user.getBalance());
    }

    @Test
    public void testWithdrawFailure() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        String response = userService.withdraw("mansi", 1500);

        assertEquals("Insufficient Balance", response);
        assertEquals(1000, user.getBalance());
    }

    @Test
    public void testCheckBalance() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        double balance = userService.checkBalance("mansi");

        assertEquals(1000, balance);
    }
}
