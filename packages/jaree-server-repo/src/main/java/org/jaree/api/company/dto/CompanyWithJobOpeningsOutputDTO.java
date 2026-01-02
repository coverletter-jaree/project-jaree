package org.jaree.api.company.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jaree.api.company.entity.Company;
import org.jaree.api.jobopening.dto.JobOpeningWithoutCompanyOutputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyWithJobOpeningsOutputDTO {
    private String id;
    private String name;
    private String description;
    private String logoUrl;
    private List<String> categories;
    private List<JobOpeningWithoutCompanyOutputDTO> jobOpenings;

    public static CompanyWithJobOpeningsOutputDTO from(Company company){

        if(company == null) return null;

        List<String> categories = company.getCategories() == null
            ? List.of()
            : company.getCategories().stream()
                .filter(Objects::nonNull)
                .map(Enum::name)
                .toList();

        List<JobOpeningWithoutCompanyOutputDTO> jobOpenings = company.getJobOpenings() == null
            ? List.of()
            : company.getJobOpenings().stream()
                .filter(Objects::nonNull)
                .map(JobOpeningWithoutCompanyOutputDTO::from)
                .toList();

        return new CompanyWithJobOpeningsOutputDTO(
            company.getId(), company.getName(),
            company.getDescription(), company.getLogoUrl(),
            categories, jobOpenings
        );
    }
}
