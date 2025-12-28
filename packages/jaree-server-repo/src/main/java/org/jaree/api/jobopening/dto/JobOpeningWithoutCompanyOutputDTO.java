package org.jaree.api.jobopening.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.jaree.api.application.entity.ApplicationQuestion;
import org.jaree.api.jobopening.entity.JobOpening;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JobOpeningWithoutCompanyOutputDTO {
    Long id;
    String title;
    String description;
    String contentS3Url;
    String imageUrl;
    LocalDateTime startsAt;
    LocalDateTime endsAt;
    LocalDateTime createdAt;
    List<JobOpeningWithoutCompanyOutputDTO.ApplicationQuestionDTO> questions;

    @AllArgsConstructor
    public static class ApplicationQuestionDTO{
        Long id;
        String content;
        String description;
        Integer order;

        public static ApplicationQuestionDTO from(ApplicationQuestion question){
            if(question == null) return null;

            return new ApplicationQuestionDTO(
                question.getId(), question.getContent(),
                question.getDescription(), question.getOrder());

        }
    }

    public static JobOpeningWithoutCompanyOutputDTO from(JobOpening jobOpening){
        if(jobOpening == null) return null;

        List<ApplicationQuestionDTO> questions = jobOpening.getQuestions() == null
            ? List.of()
            : jobOpening.getQuestions().stream()
                .filter(Objects::nonNull)
                .map(ApplicationQuestionDTO::from)
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
