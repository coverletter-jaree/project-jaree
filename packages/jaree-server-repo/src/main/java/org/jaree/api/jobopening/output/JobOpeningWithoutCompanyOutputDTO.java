package org.jaree.api.jobopening.output;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.jaree.api.application.output.ApplicationQuestionOutputDTO;
import org.jaree.api.jobopening.entity.JobOpening;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobOpeningWithoutCompanyOutputDTO {
    private String id;
    private String title;
    private String description;
    private String contentS3Url;
    private String imageUrl;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private LocalDateTime createdAt;
    private List<ApplicationQuestionOutputDTO> questions;

    public static JobOpeningWithoutCompanyOutputDTO from(JobOpening jobOpening){
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
