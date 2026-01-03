package org.jaree.api.application.controller;

import org.jaree.api.application.entity.ApplicationVersion;
import org.jaree.api.application.input.ApplicationContextSaveInputDTO;
import org.jaree.api.application.output.ApplicationOutputDTO;
import org.jaree.api.application.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/applications/{applicationId}/commits")
public class ApplicationCommitController {
    private final ApplicationService applicationService;

    public ApplicationCommitController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/{commitId}")
    public ResponseEntity<ApplicationOutputDTO> getApplicationVersion(
        @PathVariable("applicationId") String applicationId,
        @PathVariable("commitId") String commitId
    ) {
        ApplicationVersion applicationVersion = applicationService.getApplication(applicationId, commitId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commitId}/autosave")
    public ResponseEntity<Void> createApplication(
        @PathVariable("applicationId") String applicationId,
        @PathVariable("commitId") String commitId,
        @RequestBody ApplicationContextSaveInputDTO body
    ) {
        boolean isSuccess = applicationService.saveApplication(applicationId, commitId, body);
        if (!isSuccess) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
