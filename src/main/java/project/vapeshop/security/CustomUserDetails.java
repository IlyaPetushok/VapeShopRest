package project.vapeshop.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.vapeshop.entity.user.Privileges;
import project.vapeshop.entity.user.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetails implements UserDetails {
    private Integer id;
    private String login;
    private String password;
    public Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUserEntity(User user){

        List<GrantedAuthority> authorities = new ArrayList<>();
        CustomUserDetails c=new CustomUserDetails();
        for (Privileges privilege : user.getRole().getPrivileges()) {
            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }
        c.id=user.getId();
        c.grantedAuthorities= authorities;
        c.login= user.getLogin();
        c.password= user.getPassword();
        return c;
    }

    public Integer getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
