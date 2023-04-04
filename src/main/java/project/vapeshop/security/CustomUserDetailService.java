package project.vapeshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import project.vapeshop.entity.user.User;
import project.vapeshop.service.user.UserService;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public CustomUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user=userService.userFindByLogin(login);
        if(user==null){
            throw new UsernameNotFoundException("doesnt found user by login");
        }
        return CustomUserDetails.fromUserEntity(user);
    }
}
