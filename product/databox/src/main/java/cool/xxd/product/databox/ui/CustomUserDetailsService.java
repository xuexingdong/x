package cool.xxd.product.databox.ui;

import cool.xxd.service.user.application.service.UserService;
import cool.xxd.service.user.domain.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return createUserDetails(userService.getByUsername(username));
    }

    private UserDetails createUserDetails(cool.xxd.service.user.domain.aggregate.User user) {
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthorities(user))
                .accountLocked(user.getUserStatus() == UserStatus.LOCKED)
                .disabled(user.getUserStatus() == UserStatus.DISABLED)
                .build();
    }

    private String[] getAuthorities(cool.xxd.service.user.domain.aggregate.User user) {
        return new ArrayList<String>().toArray(new String[0]);
    }
}
