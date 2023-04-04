package project.vapeshop.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_ = "Bearer ";
    private final JwtProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public JwtFilter(JwtProvider jwtProvider, CustomUserDetailService customUserDetailService) {
        this.jwtProvider = jwtProvider;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtProvider.validateToken(token)) {
            String login = jwtProvider.getLogin(token);
            CustomUserDetails customUserDetails = customUserDetailService.loadUserByUsername(login);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
            filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenRequest(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith(BEARER_)) {
            return bearer.substring(7);
        }
        return null;
    }

}
