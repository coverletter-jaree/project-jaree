package org.jaree.api.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.jaree.api.application.entity.Application;
import org.jaree.api.application.enums.ApplicationStatus;
import org.jaree.api.jobopening.dto.JobOpeningSimpleDTO;
import org.jaree.api.user.dto.UserSimpleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationOutputDTO {
    private String id;
    private String title;
    private String position;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueAt;
    private List<ApplicationQuestionSimpleDTO> questions;
    private UserSimpleDTO user;
    private JobOpeningSimpleDTO jobOpening;

    public static ApplicationOutputDTO of(Application application) {
        if(application == null) {
            return null;
        }

        return ApplicationOutputDTO.builder()
            .id(application.getId())
            .title(application.getTitle())
            .position(application.getPosition())
            .status(application.getStatus())
            .createdAt(application.getCreatedAt())
            .updatedAt(application.getUpdatedAt())
            .dueAt(application.getDueAt())
            .questions(application.getQuestions().stream().map(ApplicationQuestionSimpleDTO::of).collect(Collectors.toList()))
            .user(UserSimpleDTO.of(application.getUser()))
            .jobOpening(JobOpeningSimpleDTO.of(application.getJobOpening()))
            .build();
    }
}
