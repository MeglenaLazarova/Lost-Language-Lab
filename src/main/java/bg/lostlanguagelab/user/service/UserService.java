package bg.lostlanguagelab.user.service;

import bg.lostlanguagelab.model.dto.EditProfileDTO;
import bg.lostlanguagelab.model.dto.RegisterDTO;
import bg.lostlanguagelab.model.dto.UserDto;
import bg.lostlanguagelab.user.entity.User;
import bg.lostlanguagelab.user.entity.UserRole;

import java.util.UUID;

public interface UserService {

    UserDto getById(UUID userId);

    User register(RegisterDTO registerDTO);

    void defaultAdmin();

    void updateProfile(UUID userId, EditProfileDTO dto);

    void changeRole(UUID userId, UserRole newRole);

}
