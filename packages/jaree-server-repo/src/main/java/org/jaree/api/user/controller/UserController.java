package org.jaree.api.user.controller;

import org.jaree.api.auth.dto.CustomUserDetails;
import org.jaree.api.user.entity.User;
import org.jaree.api.user.exception.UserNotFoundException;
import org.jaree.api.user.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/test-auth")
    public String test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String userId = userDetails.id();
        String name = userDetails.name();

        return "Authenticated as " + name + " (ID: " + userId + ")";
    }

    @GetMapping("/mock-user")
    public String getMockUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.id()).orElseThrow(UserNotFoundException::new);

        return "Found User Id " + user.getId();
    }
}
