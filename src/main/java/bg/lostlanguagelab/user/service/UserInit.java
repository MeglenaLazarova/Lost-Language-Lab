package lostlanguagelab.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserInit implements CommandLineRunner {

    private final UserService userService;

    public UserInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args)
    {
        userService.defaultAdmin();
        System.out.println(">>> DEFAULT ADMIN CREATED");
    }
}

