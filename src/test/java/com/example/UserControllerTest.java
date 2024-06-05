package com.example;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Controller.UserController;
import com.example.Model.User;
import com.example.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

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
    }

    @Test
    public void testRegister() throws Exception {
        doNothing().when(userService).register(any(User.class));

        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk());

        verify(userService, times(1)).register(any(User.class));
    }

    @Test
    public void testGetProfile() throws Exception {
        when(userService.getProfile("mansi")).thenReturn(user);

        mockMvc.perform(get("/api/profile/mansi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("mansi"));

        verify(userService, times(1)).getProfile("mansi");
    }

    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
