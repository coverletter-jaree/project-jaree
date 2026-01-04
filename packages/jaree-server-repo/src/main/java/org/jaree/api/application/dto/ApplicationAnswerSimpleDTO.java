package org.jaree.api.application.dto;

import java.time.LocalDateTime;

import org.jaree.api.application.entity.ApplicationAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAnswerSimpleDTO {
    private String id;
    private LocalDateTime createdAt;
    private String content;
    private ApplicationQuestionSimpleDTO question;

    public static ApplicationAnswerSimpleDTO of(ApplicationAnswer answer) {
        if(answer == null) {
            return null;
        }

        return ApplicationAnswerSimpleDTO.builder()
            .id(answer.getId())
            .createdAt(answer.getCreatedAt())
            .content(answer.getContent())
            .question(ApplicationQuestionSimpleDTO.of(answer.getQuestion()))
            .build();
    }
}
