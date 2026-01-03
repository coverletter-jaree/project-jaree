package org.jaree.api.application.controller;

import org.jaree.api.application.entity.ApplicationVersion;
import org.jaree.api.application.input.ApplicationContextSaveInputDTO;
import org.jaree.api.application.output.ApplicationVersionOutputDTO;
import org.jaree.api.application.service.ApplicationService;
import org.jaree.api.core.interfaces.DTOConverter;
import org.jaree.api.core.utils.JacksonDTOConverter;
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
    private final DTOConverter converter;

    public ApplicationCommitController(ApplicationService applicationService) {
        this.applicationService = applicationService;
        this.converter = new JacksonDTOConverter();
    }

    @GetMapping("/{commitId}")
    public ResponseEntity<ApplicationVersionOutputDTO> getApplicationVersion(
        @PathVariable("applicationId") String applicationId,
        @PathVariable("commitId") String commitId
    ) {
        ApplicationVersion applicationVersion = applicationService.getApplicationVersion(applicationId, commitId);
        ApplicationVersionOutputDTO.EntityRecord entityRecord = new ApplicationVersionOutputDTO.EntityRecord(applicationVersion);
        ApplicationVersionOutputDTO dto = converter.convert(entityRecord);

        return ResponseEntity.ok(dto);
    }

    /**
     * 특정 커밋에서 작업 중 자동저장을 통해서 특정 문항에 대해서 답변을 저장하는 API
     * @param applicationId
     * @param commitId
     * @param body
     * @return
     */
    @PostMapping("/{commitId}/autosave")
    public ResponseEntity<Void> autoSaveApplication(
        @PathVariable("applicationId") String applicationId,
        @PathVariable("commitId") String commitId,
        @RequestBody ApplicationContextSaveInputDTO body
    ) {
        boolean isSuccess = applicationService.saveAnswerTemporary(applicationId, commitId, body);
        if (!isSuccess) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
