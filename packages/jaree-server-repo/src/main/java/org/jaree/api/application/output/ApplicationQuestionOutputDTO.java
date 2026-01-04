package org.jaree.api.application.output;

import org.jaree.api.application.entity.ApplicationQuestion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationQuestionOutputDTO {
    private String id;
    private String content;
    private String description;
    private Integer order;

    public static ApplicationQuestionOutputDTO from(ApplicationQuestion question){
        if(question == null) return null;

        return new ApplicationQuestionOutputDTO(
            question.getId(), question.getContent(),
            question.getDescription(), question.getOrder());
    }
}
