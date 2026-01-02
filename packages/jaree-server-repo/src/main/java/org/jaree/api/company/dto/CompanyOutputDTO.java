package org.jaree.api.company.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jaree.api.company.entity.Company;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyOutputDTO {
    private String id;
    private String name;
    private String description;
    private String logoUrl;
    private List<String> categories;

    public static CompanyOutputDTO from(Company company) {

        if(company == null) return null;

        List<String> categories = company.getCategories() == null
            ? new ArrayList<>()
            : company.getCategories().stream()
            .filter(Objects::nonNull)
            .map(Enum::name)
            .toList();

        return new CompanyOutputDTO(
            company.getId(), company.getName(),
            company.getDescription(), company.getLogoUrl(),
            categories
        );
    }
}
