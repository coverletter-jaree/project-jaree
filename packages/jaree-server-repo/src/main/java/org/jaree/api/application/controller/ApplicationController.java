package org.jaree.api.application.controller;

import java.util.List;

import org.jaree.api.application.input.ApplicationCreationInputDTO;
import org.jaree.api.application.output.ApplicationOutputDTO;
import org.jaree.api.application.input.ApplicationUpdateInputDTO;
import org.jaree.api.application.output.ApplicationVersionCommitMessageDTO;
import org.jaree.api.application.input.ApplicationVersionCreationInputDTO;
import org.jaree.api.application.output.ApplicationVersionSimpleDTO;
import org.jaree.api.application.service.ApplicationService;
import org.jaree.api.auth.dto.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/{applicationId}")
    public ResponseEntity<?> getMostRecentApplicationVersion(
        @PathVariable String applicationId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ApplicationVersionSimpleDTO outputDTO = applicationService.getMostRecentApplicationVersion(applicationId, userDetails);
        return ResponseEntity.ok(outputDTO);
    }

    @GetMapping("/{applicationId}/commits")
    public ResponseEntity<?> getApplicationVersionList(
        @PathVariable String applicationId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<ApplicationVersionCommitMessageDTO> outputDTO = applicationService.getApplicationVersionList(applicationId, userDetails);
        return ResponseEntity.ok(outputDTO);
    }

    @PostMapping
    public ResponseEntity<?> createApplication(
        @RequestBody ApplicationCreationInputDTO dto,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ApplicationOutputDTO outputDTO = applicationService.createApplication(dto, userDetails);
        return ResponseEntity.ok(outputDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationOutputDTO> updateApplicationInfo(
        @PathVariable String id,
        @AuthenticationPrincipal CustomUserDetails user,
        @RequestBody ApplicationUpdateInputDTO applicationUpdateInputDTO
    ){
        ApplicationOutputDTO result = applicationService.updateApplicationInfo(id, user, applicationUpdateInputDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{applicationId}/commit")
    public ResponseEntity<?> createApplicationVersion(
        @RequestBody ApplicationVersionCreationInputDTO dto,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ApplicationVersionSimpleDTO outputDTO = applicationService.createApplicationVersion(dto, userDetails);
        return ResponseEntity.ok(outputDTO);
    }
}
