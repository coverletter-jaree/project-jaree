package org.jaree.api.application.controllers;

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
  public ResponseEntity<Object> getApplicationVersion(
    @PathVariable("applicationId") String applicationId,
    @PathVariable("commitId") String commitId) {
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{commitId}/autosave")
  public ResponseEntity<Object> createApplication(
    @PathVariable("applicationId") String applicationId,
    @PathVariable("commitId") String commitId,
    @RequestBody Object body
  ) {
    return ResponseEntity.ok().build();
  }
}
