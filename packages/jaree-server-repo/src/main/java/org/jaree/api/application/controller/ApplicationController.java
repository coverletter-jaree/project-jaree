package org.jaree.api.application.controller;

import org.jaree.api.application.dto.ApplicationCreationInputDTO;
import org.jaree.api.application.dto.ApplicationOutputDTO;
import org.jaree.api.application.service.ApplicationService;
import org.jaree.api.auth.dto.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<?> createApplication(
        @RequestBody ApplicationCreationInputDTO dto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        ApplicationOutputDTO outputDTO= applicationService.createApplication(dto, userDetails);
        return ResponseEntity.ok(outputDTO);
    }
}
