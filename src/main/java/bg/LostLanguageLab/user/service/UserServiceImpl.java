package bg.LostLanguageLab.user.service;

import bg.LostLanguageLab.model.dto.LoginRequest;
import bg.LostLanguageLab.model.dto.UserDto;
import bg.LostLanguageLab.user.entity.User;
import bg.LostLanguageLab.user.entity.UserRole;
import bg.LostLanguageLab.user.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepo.findByUsername(loginRequest.getUsername());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }

        String password = loginRequest.getPassword();
        String hashedPass = optionalUser.get().getPassword();

        if (!passwordEncoder.matches(password, hashedPass)) {
            throw new RuntimeException("Invalid username or password");
        }

        return optionalUser.get();
    }


    public void defaultAdmin() {
        if (userRepo.count() == 0) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .build();

            userRepo.save(admin);
        }
    }

    @Override
    public UserDto getById(UUID userId) {
        return null;
    }
}

