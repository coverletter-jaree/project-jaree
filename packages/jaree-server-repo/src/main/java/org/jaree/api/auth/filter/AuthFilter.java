package org.jaree.api.auth.filter;

import java.io.IOException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jaree.api.auth.dto.CustomUserDetails;
import org.jaree.api.user.entity.User;
import org.jaree.api.user.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("local")
public class AuthFilter extends OncePerRequestFilter {
    private static final String MOCK_USER_ID = "MockUserId";
    private static final String MOCK_USER_NAME = "MockUser";

    private final UserRepository userRepository;
    private User mockUser;

    @PostConstruct
    public void init() {
        this.mockUser = userRepository.findById(MOCK_USER_ID)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setId(MOCK_USER_ID);
                newUser.setName(MOCK_USER_NAME);
                return userRepository.save(newUser);
            });
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        // 1. Wrap the database entity in principal
        CustomUserDetails principal = CustomUserDetails.builder()
            .id(mockUser.getId())
            .name(mockUser.getName())
            .build();

        // 2. Set security context
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            principal, null, principal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
