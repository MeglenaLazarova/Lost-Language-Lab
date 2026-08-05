package lostlanguagelab.user.service;

import lostlanguagelab.model.dto.RegisterDTO;
import lostlanguagelab.model.dto.UserDto;
import lostlanguagelab.user.entity.User;

import java.util.UUID;

public interface UserService {

    UserDto getById(UUID userId);

//    User login(LoginRequest loginRequest);

    User register(RegisterDTO registerDTO);

    void defaultAdmin();
}
