package org.jaree.api.company.controller;

import org.jaree.api.company.dto.CompanyWithJobOpeningsOutputDTO;
import org.jaree.api.company.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyWithJobOpeningsOutputDTO> getCompanyInfoById(
        @PathVariable String id
    ) {
        CompanyWithJobOpeningsOutputDTO result = companyService.getCompanyInfo(id);
        return ResponseEntity.ok(result);
    }
}
