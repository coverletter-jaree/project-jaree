package org.jaree.api.company.output;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.jaree.api.application.output.ApplicationQuestionOutputDTO;
import org.jaree.api.company.entity.Company;
import org.jaree.api.jobopening.entity.JobOpening;

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

    @Getter
    @AllArgsConstructor
    public static class JobOpeningWithoutCompanyOutputDTO {
        private String id;
        private String title;
        private String description;
        private String contentS3Url;
        private String imageUrl;
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
        private LocalDateTime createdAt;
        private List<ApplicationQuestionOutputDTO> questions;

        public static JobOpeningWithoutCompanyOutputDTO from(JobOpening jobOpening) {
            if(jobOpening == null) return null;

            List<ApplicationQuestionOutputDTO> questions = jobOpening.getQuestions() == null
                ? List.of()
                : jobOpening.getQuestions().stream()
                .filter(Objects::nonNull)
                .map(ApplicationQuestionOutputDTO::from)
                .toList();

            return new JobOpeningWithoutCompanyOutputDTO(
                jobOpening.getId(), jobOpening.getTitle(),
                jobOpening.getDescription(), jobOpening.getContentS3Url(),
                jobOpening.getImageUrl(), jobOpening.getStartsAt(),
                jobOpening.getEndsAt(), jobOpening.getCreatedAt(),
                questions
            );
        }
    }

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
