package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.LoginRequest;
import bg.lostlanguagelab.user.entity.User;
import bg.lostlanguagelab.user.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public LoginController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(@RequestParam(name = "error", required = false) String errorMessege) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("loginRequest", new LoginRequest());

        if (errorMessege != null) {
            modelAndView.addObject("errorMessage", "Invalid username or password.");
        }
       return modelAndView;
    }


    @PostMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("loginRequest")LoginRequest loginRequest,
                              BindingResult bindingResult, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("login");

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        try {
            User user = userServiceImpl.login(loginRequest);

            session.setAttribute("userId", user.getId());
            session.setAttribute("is_admin", user.getRole().name().equals("ADMIN"));

            return new ModelAndView("redirect:/home");

        } catch (RuntimeException e) {
            modelAndView.addObject("loginError", "Invalid username or password");
            return modelAndView;
        }
    }
}

