package org.jaree.api.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.jaree.api.application.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationUpdateInputDTO {
    private String title;
    private String position;
    private ApplicationStatus status;
    private LocalDateTime dueAt;
    private List<ApplicationQuestionDTO> questions;
    private JobOpeningDTO jobOpening;

    @Getter
    @AllArgsConstructor
    public static class ApplicationQuestionDTO{
        private String id;
        private String content;
        private String description;
        private Integer order;
    }

    @Getter
    @AllArgsConstructor
    public static class JobOpeningDTO{
        private String id;
    }
}
