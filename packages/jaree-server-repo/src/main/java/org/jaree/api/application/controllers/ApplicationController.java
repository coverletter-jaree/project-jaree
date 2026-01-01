package org.jaree.api.application.controllers;

import java.util.List;

import org.jaree.api.application.entity.Application;
import org.jaree.api.application.outputs.ApplicationListOutputDTOItem;
import org.jaree.api.application.services.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
  private final ApplicationService applicationService;

  public ApplicationController(ApplicationService applicationService) {
    this.applicationService = applicationService;
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

