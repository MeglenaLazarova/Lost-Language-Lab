package bg.lostlanguagelab.security;

import bg.lostlanguagelab.user.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {

    @Test
    void testGetAuthorities() {
        UserData user = new UserData(
                UUID.randomUUID(),
                "testuser",
                "password",
                UserRole.ADMIN
        );

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals("ROLE_ADMIN", authorities.iterator().next().getAuthority());
    }

    @Test
    void testGetPassword() {
        UserData user = new UserData(
                UUID.randomUUID(),
                "testuser",
                "secret",
                UserRole.USER
        );

        assertEquals("secret", user.getPassword());
    }

    @Test
    void testGetUsername() {
        UserData user = new UserData(
                UUID.randomUUID(),
                "testuser",
                "pass",
                UserRole.USER
        );

        assertEquals("testuser", user.getUsername());
    }

    @Test
    void testAccountNonExpired() {
        UserData user = new UserData(
                UUID.randomUUID(),
                "testuser",
                "pass",
                UserRole.USER
        );

        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testAccountNonLocked() {
        UserData user = new UserData(
                UUID.randomUUID(),
                "testuser",
                "pass",
                UserRole.USER
        );

        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testCredentialsNonExpired() {
        UserData user = new UserData(
                UUID.randomUUID(),
                "testuser",
                "pass",
                UserRole.USER
        );

        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        UserData user = new UserData(
                UUID.randomUUID(),
                "testuser",
                "pass",
                UserRole.USER
        );

        assertTrue(user.isEnabled());
    }
}

