package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.EditProfileDTO;
import bg.lostlanguagelab.model.dto.UserDto;
import bg.lostlanguagelab.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("/profile")
    public ModelAndView getProfilePage(@AuthenticationPrincipal bg.lostlanguagelab.security.UserData userData) {

        UserDto userDto = userService.getById(userData.getId());

        EditProfileDTO editProfileDTO = new EditProfileDTO();
        editProfileDTO.setUsername(userDto.getUsername());
        editProfileDTO.setEmail(userDto.getEmail());

        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("editProfileDTO", editProfileDTO);
        return mav;
    }

    @PostMapping("/profile")
    public ModelAndView updateProfile(@AuthenticationPrincipal bg.lostlanguagelab.security.UserData userData,
                                      @Valid EditProfileDTO editProfileDTO,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("profile");
            return mav;
        }

        userService.updateProfile(userData.getId(), editProfileDTO);

        return new ModelAndView("redirect:/home");
    }
}
