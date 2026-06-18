package bg.LostLanguageLab.user.service;

import bg.LostLanguageLab.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User register(String username, String email, String password);

    Optional<User> findByUsername(String username);

    User getById(UUID id);

    boolean userExists(String username);

    void defaultAdmin();

}
