package org.jaree.api.application.controllers;

import java.util.List;

import org.jaree.api.application.output.ApplicationListOutputDTOItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

  @GetMapping()
  public ResponseEntity<List<ApplicationListOutputDTOItem>> getApplications() {
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteApplication(@PathVariable("id") String id) {
    return ResponseEntity.ok().build();
  }
}

