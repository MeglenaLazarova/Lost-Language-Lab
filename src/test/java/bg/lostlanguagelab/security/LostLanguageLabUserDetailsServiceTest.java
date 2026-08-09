package bg.lostlanguagelab.security;

import bg.lostlanguagelab.user.entity.User;
import bg.lostlanguagelab.user.entity.UserRole;
import bg.lostlanguagelab.user.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LostLanguageLabUserDetailsServiceTest {

    @Test
    void testLoadUserByUsernameReturnsUserData() {
        // Arrange
        UserRepo userRepo = mock(UserRepo.class);

        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setUsername("testuser");
        user.setPassword("secret");
        user.setRole(UserRole.ADMIN);

        when(userRepo.findByUsername("testuser"))
                .thenReturn(Optional.of(user));

        LostLanguageLabUserDetailsService service =
                new LostLanguageLabUserDetailsService(userRepo);

        // Act
        UserData result = (UserData) service.loadUserByUsername("testuser");

        // Assert
        assertEquals(id, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("secret", result.getPassword());
        assertEquals(UserRole.ADMIN, result.getRole());
        assertEquals("ROLE_ADMIN",
                result.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void testLoadUserByUsernameThrowsExceptionWhenNotFound() {
        // Arrange
        UserRepo userRepo = mock(UserRepo.class);

        when(userRepo.findByUsername("missing"))
                .thenReturn(Optional.empty());

        LostLanguageLabUserDetailsService service =
                new LostLanguageLabUserDetailsService(userRepo);

        // Act + Assert
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("missing"));
    }
}

