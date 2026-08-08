package bg.lostlanguagelab.controller;

import bg.lostlanguagelab.model.dto.ChangeRoleDTO;
import bg.lostlanguagelab.user.repository.UserRepo;
import bg.lostlanguagelab.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepo userRepo;
    private final UserService userService;

    @GetMapping("/users")
    public ModelAndView listUsers() {
        ModelAndView mav = new ModelAndView("admin-users");
        mav.addObject("users", userRepo.findAll());
        return mav;
    }

    @PostMapping("/users/{id}/role")
    public String changeRole(@PathVariable UUID id,
                             @ModelAttribute ChangeRoleDTO changeRoleDTO) {

        userService.changeRole(id, changeRoleDTO.getRole());
        return "redirect:/admin/users";
    }
}
