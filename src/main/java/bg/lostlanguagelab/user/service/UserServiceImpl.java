package lostlanguagelab.user.service;

import lostlanguagelab.model.dto.RegisterDTO;
import lostlanguagelab.model.dto.UserDto;
import lostlanguagelab.user.entity.User;
import lostlanguagelab.user.entity.UserRole;
import lostlanguagelab.user.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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

//    public User login(LoginRequest loginRequest) {
//        Optional<User> optionalUser = userRepo.findByUsername(loginRequest.getUsername());
//
//        if (optionalUser.isEmpty()) {
//            throw new RuntimeException("Invalid username or password");
//        }
//
//        String password = loginRequest.getPassword();
//        String hashedPass = optionalUser.get().getPassword();
//
//        if (!passwordEncoder.matches(password, hashedPass)) {
//            throw new RuntimeException("Invalid username or password");
//        }
//
//        return optionalUser.get();
//    }


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
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Transactional
    public User register(RegisterDTO registerDTO) {

        if (userRepo.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(UserRole.USER)
                .build();

        return userRepo.save(user);
    }
}

