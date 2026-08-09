package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.LoginRequest;
import bg.lostlanguagelab.user.entity.User;
import bg.lostlanguagelab.user.entity.UserRole;
import bg.lostlanguagelab.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetLoginPage_NoError() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginRequest"))
                .andExpect(model().attributeDoesNotExist("errorMessage"));
    }

    @Test
    void testGetLoginPage_WithError() throws Exception {
        mockMvc.perform(get("/login").param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginRequest"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    void testLogin_BindingErrors() throws Exception {

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("username", "")
                        .param("password", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginRequest"));
    }

    @Test
    void testLogin_Success() throws Exception {

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("Megi");
        user.setPassword("123456");
        user.setRole(UserRole.USER);

        when(userServiceImpl.login(any(LoginRequest.class))).thenReturn(user);

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("username", "Megi")
                        .param("password", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        verify(userServiceImpl).login(any(LoginRequest.class));
    }



    @Test
    void testLogin_InvalidCredentials() throws Exception {

        when(userServiceImpl.login(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Invalid username or password"));

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("username", "wrong")
                        .param("password", "wrong"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginRequest"));
    }
}

