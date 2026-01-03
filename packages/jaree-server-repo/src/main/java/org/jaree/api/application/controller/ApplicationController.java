package org.jaree.api.application.controller;

import java.util.List;
import org.jaree.api.application.dto.ApplicationCreationInputDTO;
import org.jaree.api.application.dto.ApplicationOutputDTO;
import org.jaree.api.application.dto.ApplicationVersionCommitMessageDTO;
import org.jaree.api.application.dto.ApplicationVersionCreationInputDTO;
import org.jaree.api.application.dto.ApplicationVersionSimpleDTO;
import org.jaree.api.application.entity.Application;
import org.jaree.api.application.output.ApplicationListOutputDTOItem;
import org.jaree.api.application.service.ApplicationService;
import org.jaree.api.auth.dto.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/{applicationId}/commit")
    public ResponseEntity<?> createApplicationVersion(
        @RequestBody ApplicationVersionCreationInputDTO dto,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ApplicationVersionSimpleDTO outputDTO = applicationService.createApplicationVersion(dto, userDetails);
        return ResponseEntity.ok(outputDTO);
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ApplicationListOutputDTOItem>> getApplications() {
        List<Application> applications = applicationService.getApplications();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable("id") String id) {
        boolean isDeleted = applicationService.deleteApplication(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
