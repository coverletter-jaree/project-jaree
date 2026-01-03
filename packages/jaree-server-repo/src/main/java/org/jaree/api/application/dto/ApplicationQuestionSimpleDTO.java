package org.jaree.api.application.dto;

import org.jaree.api.application.entity.ApplicationQuestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationQuestionSimpleDTO {
    private String id;
    private String content;
    private String description;
    private Integer order;

    public static ApplicationQuestionSimpleDTO of(ApplicationQuestion question) {
        if(question == null) {
            return null;
        }
        return ApplicationQuestionSimpleDTO.builder()
            .id(question.getId())
            .content(question.getContent())
            .description(question.getDescription())
            .order(question.getOrder())
            .build();
    }

    public ApplicationQuestion toEntity() {
        return ApplicationQuestion.builder()
                .id(id)
                .content(content)
                .description(description)
                .order(order)
                .build();
    }
}
