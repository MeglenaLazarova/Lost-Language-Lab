package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.UserDto;
import bg.lostlanguagelab.security.UserData;
import bg.lostlanguagelab.user.entity.UserRole;
import bg.lostlanguagelab.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IndexController.class)
class IndexControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void testIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testHomePage() throws Exception {

        UUID id = UUID.randomUUID();
        UserData principal = new UserData(
                id,
                "Megi",
                "123456",
                UserRole.USER
        );

        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setUsername("Megi");

        when(userServiceImpl.getById(id)).thenReturn(userDto);

        mockMvc.perform(get("/home")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new UsernamePasswordAuthenticationToken(
                                        principal,
                                        principal.getPassword(),
                                        principal.getAuthorities()
                                )
                        )))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", userDto));
    }

   @Test
    void testHomePage_Unauthenticated() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection()) // Security redirects to login
                .andExpect(redirectedUrlPattern("**/login"));
    }
}

