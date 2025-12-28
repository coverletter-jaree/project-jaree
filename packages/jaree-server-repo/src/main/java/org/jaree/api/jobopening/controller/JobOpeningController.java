package org.jaree.api.jobopening.controller;

import org.jaree.api.jobopening.dto.JobOpeningOutputDTO;
import org.jaree.api.jobopening.service.JobOpeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job-opening")
public class JobOpeningController {

    private final JobOpeningService jobOpeningService;

    @GetMapping("/{id}")
    public ResponseEntity<JobOpeningOutputDTO> getJobOpeningIfoById(
        @PathVariable Long id
    ){
        JobOpeningOutputDTO result = jobOpeningService.getJobOpeningInfo(id);
        return ResponseEntity.ok(result);
    }
}
