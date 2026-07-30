package bg.lostlanguagelab.user.service;

import bg.lostlanguagelab.model.dto.LoginRequest;
import bg.lostlanguagelab.model.dto.RegisterDTO;
import bg.lostlanguagelab.model.dto.UserDto;
import bg.lostlanguagelab.user.entity.User;

import java.util.UUID;

public interface UserService {

    UserDto getById(UUID userId);

//    User login(LoginRequest loginRequest);

    User register(RegisterDTO registerDTO);

    void defaultAdmin();
}
