package bg.LostLanguageLab.user.service;

import bg.LostLanguageLab.model.dto.LoginRequest;
import bg.LostLanguageLab.model.dto.RegisterDTO;
import bg.LostLanguageLab.model.dto.UserDto;
import bg.LostLanguageLab.user.entity.User;

import java.util.UUID;

public interface UserService {

    UserDto getById(UUID userId);

    User login(LoginRequest loginRequest);

    User register(RegisterDTO registerDTO);

    void defaultAdmin();
}
