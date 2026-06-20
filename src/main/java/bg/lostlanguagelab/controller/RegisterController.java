package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.RegisterDTO;
import bg.lostlanguagelab.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("registerDTO", new RegisterDTO());

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("registerDTO")
                                 RegisterDTO registerDto,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            return new ModelAndView("register");
        }

        userService.register(registerDto);

        redirectAttributes.addFlashAttribute("successfulRegistration", "Registration Successful");
        return new ModelAndView("redirect:/login");
    }


}

