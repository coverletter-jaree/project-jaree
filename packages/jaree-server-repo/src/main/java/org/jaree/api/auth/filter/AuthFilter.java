package org.jaree.api.auth.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jaree.api.auth.dto.CustomUserDetails;
import org.jaree.api.user.entity.User;
import org.jaree.api.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // 1. Find or create mock user
        User user = userRepository.findById(0L)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setId(0L);
                newUser.setName("MockUser");
                return userRepository.save(newUser);
            });

        // 2. Wrap the database entity in your Principal
        CustomUserDetails principal = CustomUserDetails.builder()
            .id(user.getId())
            .name(user.getName())
            .build();

        // 3. Set security context
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            principal, null, principal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
