package org.jaree.api.application.controller;

import org.jaree.api.application.dto.ApplicationOutputDTO;
import org.jaree.api.application.dto.ApplicationUpdateInputDTO;
import org.jaree.api.application.service.ApplicationService;
import org.jaree.api.auth.dto.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationOutputDTO> updateApplicationInfo(
        @PathVariable String id,
        @AuthenticationPrincipal CustomUserDetails user,
        @RequestBody ApplicationUpdateInputDTO applicationUpdateInputDTO
    ){
        ApplicationOutputDTO result = applicationService.updateApplicationInfo(id, user, applicationUpdateInputDTO);
        return ResponseEntity.ok(result);
    }

}
