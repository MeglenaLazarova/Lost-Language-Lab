package bg.lostlanguagelab.user.service;

import bg.lostlanguagelab.model.dto.EditProfileDTO;
import bg.lostlanguagelab.model.dto.LoginRequest;
import bg.lostlanguagelab.model.dto.RegisterDTO;
import bg.lostlanguagelab.model.dto.UserDto;
import bg.lostlanguagelab.user.entity.User;
import bg.lostlanguagelab.user.entity.UserRole;
import bg.lostlanguagelab.user.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testDefaultAdminCreatesAdminWhenNoUsers() {
        when(userRepo.count()).thenReturn(0L);
        when(passwordEncoder.encode("admin123")).thenReturn("encoded");

        userService.defaultAdmin();

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(captor.capture());

        User admin = captor.getValue();
        assertEquals("admin", admin.getUsername());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals("encoded", admin.getPassword());
        assertEquals(UserRole.ADMIN, admin.getRole());
    }

    @Test
    void testDefaultAdminDoesNothingWhenUsersExist() {
        when(userRepo.count()).thenReturn(5L);

        userService.defaultAdmin();

        verify(userRepo, never()).save(any());
    }


    @Test
    void testUpdateProfileWithoutPasswordChange() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setUsername("old");
        user.setEmail("old@example.com");
        user.setPassword("pass");

        EditProfileDTO dto = new EditProfileDTO("new", "new@example.com", "");

        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        userService.updateProfile(id, dto);

        assertEquals("new", user.getUsername());
        assertEquals("new@example.com", user.getEmail());
        assertEquals("pass", user.getPassword()); // unchanged
    }

    @Test
    void testUpdateProfileWithPasswordChange() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setPassword("oldPass");

        EditProfileDTO dto = new EditProfileDTO("new", "new@example.com", "newPass");

        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPass")).thenReturn("encoded");

        userService.updateProfile(id, dto);

        assertEquals("encoded", user.getPassword());
    }

    @Test
    void testChangeRole() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setRole(UserRole.USER);

        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        userService.changeRole(id, UserRole.ADMIN);

        assertEquals(UserRole.ADMIN, user.getRole());
    }


    @Test
    void testGetByIdSuccess() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setUsername("test");
        user.setEmail("test@example.com");

        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        UserDto dto = userService.getById(id);

        assertEquals(id, dto.getId());
        assertEquals("test", dto.getUsername());
        assertEquals("test@example.com", dto.getEmail());
    }

    @Test
    void testGetByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getById(id));
    }


    @Test
    void testRegisterSuccess() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUsername("user");
        dto.setEmail("email@example.com");
        dto.setPassword("pass");

        when(userRepo.findByUsername("user")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(userRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        User saved = userService.register(dto);

        assertEquals("user", saved.getUsername());
        assertEquals("email@example.com", saved.getEmail());
        assertEquals("encoded", saved.getPassword());
        assertEquals(UserRole.USER, saved.getRole());
    }

    @Test
    void testRegisterUsernameExists() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUsername("user");
        dto.setEmail("email@example.com");
        dto.setPassword("pass");

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> userService.register(dto));
    }

    @Test
    void testLoginSuccess() {
        LoginRequest req = new LoginRequest("user", "pass");

        User user = new User();
        user.setUsername("user");
        user.setPassword("encoded");

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass", "encoded")).thenReturn(true);

        User logged = userService.login(req);

        assertEquals("user", logged.getUsername());
    }

    @Test
    void testLoginWrongPassword() {
        LoginRequest req = new LoginRequest("user", "wrong");

        User user = new User();
        user.setUsername("user");
        user.setPassword("encoded");

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.login(req));
    }

    @Test
    void testLoginUserNotFound() {
        LoginRequest req = new LoginRequest("missing", "pass");

        when(userRepo.findByUsername("missing")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.login(req));
    }
}
