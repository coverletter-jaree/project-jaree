package org.jaree.api.application.dto;

import org.jaree.api.application.entity.ApplicationAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAnswerCreationInputDTO {
    private String content;
    private ApplicationQuestionSimpleDTO question;

    public ApplicationAnswer toEntity() {
        return ApplicationAnswer.builder()
            .content(content)
            .question(question.toEntity())
            .build();
    }
}
