package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.EditProfileDTO;
import bg.lostlanguagelab.model.dto.UserDto;
import bg.lostlanguagelab.security.UserData;
import bg.lostlanguagelab.user.entity.UserRole;
import bg.lostlanguagelab.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
class ProfileControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserData mockUserData() {
        return new UserData(
                UUID.randomUUID(),
                "user",
                "password",
                UserRole.USER
        );
    }

    @Test
    void testGetProfilePage() throws Exception {
        UserData userData = new UserData(
                UUID.randomUUID(),
                "user",
                "password",
                UserRole.USER
        );

        UserDto userDto = new UserDto();
        userDto.setId(userData.getId());
        userDto.setUsername("Megi");
        userDto.setEmail("megi@test.com");

        when(userService.getById(userData.getId())).thenReturn(userDto);

        mockMvc.perform(get("/profile")
                        .with(authentication(
                                new UsernamePasswordAuthenticationToken(
                                        userData,
                                        userData.getPassword(),
                                        userData.getAuthorities()
                                )))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("editProfileDTO"));

        verify(userService).getById(userData.getId());
    }


    @Test
    void testUpdateProfileSuccess() throws Exception {
        UserData userData = mockUserData();

        mockMvc.perform(post("/profile")
                        .with(authentication(
                                new UsernamePasswordAuthenticationToken(
                                        userData,
                                        userData.getPassword(),
                                        userData.getAuthorities()
                                )
                        ))
                        .param("username", "Megi")
                        .param("email", "megi@test.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        verify(userService).updateProfile(eq(userData.getId()), any(EditProfileDTO.class));
    }

    @Test
    void testUpdateProfileBindingErrors() throws Exception {
        UserData userData = mockUserData();

        mockMvc.perform(post("/profile")
                        .with(authentication(
                                new UsernamePasswordAuthenticationToken(
                                        userData,
                                        userData.getPassword(),
                                        userData.getAuthorities()
                                )
                        ))
                        .with(csrf())
                        .param("username", "Megi"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }


}
