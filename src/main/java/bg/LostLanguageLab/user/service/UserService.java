package bg.LostLanguageLab.user.service;

import bg.LostLanguageLab.model.dto.UserDto;
import bg.LostLanguageLab.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDto getById(UUID userId);
}
