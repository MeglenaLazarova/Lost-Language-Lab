package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.UserDto;
import bg.lostlanguagelab.security.UserData;
import bg.lostlanguagelab.user.service.UserServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

    private final UserServiceImpl userServiceImpl;

    public IndexController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("index");
    };

//    @GetMapping("/home")
//    public ModelAndView home(){
//        return new ModelAndView("home");
//    }

    @GetMapping("/home")
    public ModelAndView getHomePage(@AuthenticationPrincipal UserData userData){
        UserDto user = userServiceImpl.getById(userData.getId());
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

}