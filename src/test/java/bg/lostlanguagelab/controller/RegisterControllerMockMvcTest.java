package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.RegisterDTO;
import bg.lostlanguagelab.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
class RegisterControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registerDTO"));
    }

    @Test
    void testRegisterSuccess() throws Exception {
        mockMvc.perform(post("/register")
                        .param("firstName", "Megi")
                        .param("lastName", "Ivanova")
                        .param("username", "user")
                        .param("email", "test@test.com")
                        .param("password", "123456")
                        .param("confirmPassword", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService).register(any(RegisterDTO.class));
    }

    @Test
    void testRegisterBindingErrors() throws Exception {
        mockMvc.perform(post("/register")
                        .param("password", "123456")
                        .param("confirmPassword", "123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testRegisterUsernameAlreadyExists() throws Exception {
        doThrow(new RuntimeException("Username already exists"))
                .when(userService).register(any(RegisterDTO.class));

        mockMvc.perform(post("/register")
                        .param("firstName", "Megi")
                        .param("lastName", "Ivanova")
                        .param("username", "user")
                        .param("email", "test@test.com")
                        .param("password", "123456")
                        .param("confirmPassword", "123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("registerDTO", "username"));

        verify(userService).register(any(RegisterDTO.class));
    }


    @Test
    void testRegisterOtherException() throws Exception {
        doThrow(new RuntimeException("Other error"))
                .when(userService).register(any(RegisterDTO.class));

        mockMvc.perform(post("/register")
                        .param("firstName", "Megi")
                        .param("lastName", "Ivanova")
                        .param("username", "user")
                        .param("email", "test@test.com")
                        .param("password", "123456")
                        .param("confirmPassword", "123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }



}

