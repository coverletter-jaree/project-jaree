package org.jaree.api.jobopening.dto;

import java.time.LocalDateTime;

import org.jaree.api.company.dto.CompanyOutputDTO;
import org.jaree.api.jobopening.entity.JobOpening;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobOpeningWithoutApplicationsAndQuestionsDTO {
    private String id;
    private String title;
    private String description;
    private String contentS3Url;
    private String imageUrl;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private LocalDateTime createdAt;
    private CompanyOutputDTO company;

    public static JobOpeningWithoutApplicationsAndQuestionsDTO from(JobOpening jobOpening){
        if(jobOpening == null) return null;

        CompanyOutputDTO company = CompanyOutputDTO.from(jobOpening.getCompany());

        return new JobOpeningWithoutApplicationsAndQuestionsDTO(
            jobOpening.getId(), jobOpening.getTitle(),
            jobOpening.getDescription(), jobOpening.getContentS3Url(),
            jobOpening.getImageUrl(), jobOpening.getStartsAt(),
            jobOpening.getEndsAt(), jobOpening.getCreatedAt(),
            company
        );
    }
}
