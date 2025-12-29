package org.jaree.api.application.dto;

import java.util.List;

import org.jaree.api.application.entity.ApplicationVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationVersionCreationInputDTO {
    private String applicationId;
    private String commitMessage;
    private String previousVersionId;
    private List<ApplicationAnswerCreationInputDTO> answers;

    public ApplicationVersion toEntity() {
        return ApplicationVersion.builder()
            .commitMessage(commitMessage)
            .build();
    }
}
