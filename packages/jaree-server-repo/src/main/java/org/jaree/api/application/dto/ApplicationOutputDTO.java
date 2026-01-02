package org.jaree.api.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.jaree.api.application.entity.Application;
import org.jaree.api.application.enums.ApplicationStatus;
import org.jaree.api.jobopening.dto.JobOpeningWithoutApplicationsAndQuestionsDTO;
import org.jaree.api.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationOutputDTO {
    private String id;
    private String title;
    private String position;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueAt;
    private List<ApplicationQuestionOutputDTO> questions;
    private UserOutputDTO user;
    private JobOpeningWithoutApplicationsAndQuestionsDTO jobOpening;

    @Getter
    @AllArgsConstructor
    public static class UserOutputDTO {
        private String id;
        private String name;
        private LocalDateTime createdAt;

        public static UserOutputDTO from(User user) {
            if(user == null) return null;

            return new UserOutputDTO(user.getId(), user.getName(), user.getCreatedAt());
        }
    }

    public static ApplicationOutputDTO from(Application application) {
        if(application == null) return null;

        List<ApplicationQuestionOutputDTO> questions = application.getQuestions() == null
            ? List.of()
            : application.getQuestions().stream()
                .filter(Objects::nonNull)
                .map(ApplicationQuestionOutputDTO::from)
                .toList();

        UserOutputDTO user = UserOutputDTO.from(application.getUser());

        JobOpeningWithoutApplicationsAndQuestionsDTO jobOpening = JobOpeningWithoutApplicationsAndQuestionsDTO.from(application.getJobOpening());

        return new ApplicationOutputDTO(
            application.getId(), application.getTitle(),
            application.getPosition(), application.getStatus(),
            application.getCreatedAt(), application.getUpdatedAt(),
            application.getDueAt(), questions,
            user, jobOpening
        );
    }
}
