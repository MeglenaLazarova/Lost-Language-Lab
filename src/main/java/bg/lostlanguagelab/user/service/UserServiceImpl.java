package bg.lostlanguagelab.user.service;

import bg.lostlanguagelab.model.dto.EditProfileDTO;
import bg.lostlanguagelab.model.dto.LoginRequest;
import bg.lostlanguagelab.model.dto.RegisterDTO;
import bg.lostlanguagelab.model.dto.UserDto;
import bg.lostlanguagelab.user.entity.User;
import bg.lostlanguagelab.user.entity.UserRole;
import bg.lostlanguagelab.user.repository.UserRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    public void defaultAdmin() {
        if (userRepo.count() == 0) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .build();

            userRepo.save(admin);
            log.info("Default admin created successfully");
        }
    }

    @Transactional
    @Override
    public void updateProfile(UUID userId, EditProfileDTO dto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        log.info("User {} updated profile: username={}, email={}",
                userId, dto.getUsername(), dto.getEmail());

    }

    @Override
    public void changeRole(UUID userId, UserRole newRole) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);

        log.info("Admin changed role of user {} to {}", userId, newRole);
    }

    @Override
    public UserDto getById(UUID userId) {
        log.info("Fetching user by id={}", userId);
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

        log.info("User registered successfully: {}", user);

        return userRepo.save(user);
    }

    public User login(@Valid LoginRequest loginRequest) {
        User user = userRepo.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        log.info("User logged in successfully: {}", loginRequest.getUsername());
        return user;
    }


}

