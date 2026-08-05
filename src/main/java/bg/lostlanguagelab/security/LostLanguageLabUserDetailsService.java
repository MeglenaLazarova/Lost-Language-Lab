package lostlanguagelab.security;

import lostlanguagelab.user.entity.User;
import lostlanguagelab.user.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LostLanguageLabUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public LostLanguageLabUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new UserData(user.getId(), user.getUsername(), user.getPassword(), user.getRole());

    }
}
