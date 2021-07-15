package kz.edy.astanait.product.security;
import kz.edy.astanait.product.service.UserService;
import kz.edy.astanait.product.service.security.PostRequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static kz.edy.astanait.product.constant.SecurityConstant.*;
import static org.springframework.http.HttpStatus.*;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PostRequestCounterService postRequestCounterService;

    @Autowired
    public JWTAuthorizationFilter(JWTTokenProvider jwtTokenProvider, UserService userService, PostRequestCounterService postRequestCounterService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.postRequestCounterService = postRequestCounterService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
            response.setStatus(OK.value());
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username;
            UserPrincipal userPrincipal;
            try {
             username = jwtTokenProvider.getSubject(token);
             userPrincipal = (UserPrincipal) userService.loadUserByUsername(username);
            if (!userPrincipal.isAccountNonLocked()) {
                response.setStatus(UNAUTHORIZED.value());
                return;
            }
                if (jwtTokenProvider.isTokenValid(username, token, request)) {
                    List<GrantedAuthority> authorities = new ArrayList<>(userPrincipal.getAuthorities());
                    Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } catch (Exception e) {
                response.setStatus(UNAUTHORIZED.value());
                return;
            }

            if (!postRequestCounterService.validatePostRequest(userPrincipal.getUsername(), request) && userPrincipal.getAuthorities().toString().contains("ROLE_USER")) {
                response.setStatus(500);
                return;
            }

            if (!userPrincipal.getAuthorities().toString().contains(request.getRequestURI())) {
                response.setStatus(FORBIDDEN.value());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
