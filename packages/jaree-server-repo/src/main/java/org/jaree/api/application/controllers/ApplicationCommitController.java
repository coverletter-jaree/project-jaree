package org.jaree.api.application.controllers;

import org.jaree.api.application.entity.ApplicationVersion;
import org.jaree.api.application.inputs.ApplicationContextSaveInputDTO;
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

  @GetMapping("/{commitId}")
  public ResponseEntity<ApplicationVersion> getApplicationVersion(
    @PathVariable("applicationId") String applicationId,
    @PathVariable("commitId") String commitId) {
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{commitId}/autosave")
  public ResponseEntity<Void> createApplication(
    @PathVariable("applicationId") String applicationId,
    @PathVariable("commitId") String commitId,
    @RequestBody ApplicationContextSaveInputDTO body
  ) {
    return ResponseEntity.ok().build();
  }
}
