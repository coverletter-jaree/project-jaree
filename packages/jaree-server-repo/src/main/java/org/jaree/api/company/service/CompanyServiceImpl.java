package org.jaree.api.company.service;

import org.jaree.api.company.dto.CompanyWithJobOpeningsOutputDTO;
import org.jaree.api.company.entity.Company;
import org.jaree.api.company.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyWithJobOpeningsOutputDTO getCompanyInfo(Long id) {

        Company company = companyRepository.findByIdWithJobOpeningsWithQuestion(id)
            .orElseThrow(() -> new RuntimeException("Company not found"));

        return CompanyWithJobOpeningsOutputDTO.from(company);
    }
}
