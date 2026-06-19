package bg.LostLanguageLab.controller;

import bg.LostLanguageLab.model.dto.RegisterDTO;
import bg.LostLanguageLab.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(@ModelAttribute("registerDTO") RegisterDTO registerDTO) {
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.userExists(registerDTO.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Потребителското име е заето");
            return "register";
        }

        userService.register(
                registerDTO.getUsername(),
                registerDTO.getEmail(),
                registerDTO.getPassword()
        );

        return "redirect:/login";
    }
}

