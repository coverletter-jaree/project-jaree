package org.jaree.api.company.dto;

import java.util.List;

import org.jaree.api.company.entity.Company;
import org.jaree.api.company.enums.CompanyCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanySimpleDTO {
    private String id;
    private String name;
    private String description;
    private String logoUrl;
    private List<CompanyCategory> categories;

    public static CompanySimpleDTO of(Company company) {
        if(company == null) {
            return null;
        }
        return CompanySimpleDTO.builder()
            .id(company.getId())
            .name(company.getName())
            .description(company.getDescription())
            .logoUrl(company.getLogoUrl())
            .categories(company.getCategories())
            .build();
    }
}
