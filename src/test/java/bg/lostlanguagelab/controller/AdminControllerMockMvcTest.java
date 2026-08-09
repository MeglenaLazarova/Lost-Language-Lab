package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.ChangeRoleDTO;
import bg.lostlanguagelab.user.entity.User;
import bg.lostlanguagelab.user.entity.UserRole;
import bg.lostlanguagelab.user.repository.UserRepo;
import bg.lostlanguagelab.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private UserService userService;

    @Test
    void testListUsers() throws Exception {

        when(userRepo.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/users")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void testChangeRole() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/admin/users/" + id + "/role")
                        .with(user("admin").roles("ADMIN"))
                        .param("role", UserRole.ADMIN.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService, times(1)).changeRole(id, UserRole.ADMIN);
    }

    @Test
    void testAccessDeniedForNonAdmin() throws Exception {

        mockMvc.perform(get("/admin/users")
                        .with(user("user").roles("USER")))
                .andExpect(status().isForbidden());
    }
}

