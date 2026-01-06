package org.jaree.api.company.service;

import org.jaree.api.company.exception.CompanyNotFoundException;
import org.jaree.api.company.output.CompanyWithJobOpeningsOutputDTO;
import org.jaree.api.company.entity.Company;
import org.jaree.api.company.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService implements CompanyServiceInterface {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyWithJobOpeningsOutputDTO getCompanyInfo(String id) {

        Company company = companyRepository.findByIdWithJobOpeningsWithQuestion(id).orElseThrow(CompanyNotFoundException::new);

        return CompanyWithJobOpeningsOutputDTO.from(company);
    }
}
