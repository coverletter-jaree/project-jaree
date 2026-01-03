package org.jaree.api.jobopening.dto;

import java.time.LocalDateTime;

import org.jaree.api.company.dto.CompanySimpleDTO;
import org.jaree.api.jobopening.entity.JobOpening;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobOpeningSimpleDTO {
    private String id;
    private String title;
    private String description;
    private String contentS3Url;
    private String imageUrl;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private LocalDateTime createdAt;
    private CompanySimpleDTO company;

    public static JobOpeningSimpleDTO of(JobOpening jobOpening) {
        if(jobOpening == null){
            return null;
        }

        return JobOpeningSimpleDTO.builder()
            .id(jobOpening.getId())
            .title(jobOpening.getTitle())
            .description(jobOpening.getDescription())
            .contentS3Url(jobOpening.getContentS3Url())
            .imageUrl(jobOpening.getImageUrl())
            .startsAt(jobOpening.getStartsAt())
            .endsAt(jobOpening.getEndsAt())
            .createdAt(jobOpening.getCreatedAt())
            .company(CompanySimpleDTO.of(jobOpening.getCompany()))
            .build();
    }
}
