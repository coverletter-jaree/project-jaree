package org.jaree.api.application.output;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.jaree.api.application.entity.ApplicationVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationVersionSimpleDTO {
    private String id;
    private String commitMessage;
    private LocalDateTime createdAt;
    private List<String> ancestorIds;
    private List<ApplicationAnswerSimpleDTO> answers;

    public static ApplicationVersionSimpleDTO of(ApplicationVersion applicationVersion) {
        if (applicationVersion == null) {
            return null;
        }

        return ApplicationVersionSimpleDTO.builder()
            .id(applicationVersion.getId())
            .commitMessage(applicationVersion.getCommitMessage())
            .createdAt(applicationVersion.getCreatedAt())
            .ancestorIds(applicationVersion.getAncestors().stream()
                .map(ApplicationVersion::getId)
                .collect(Collectors.toList()))
            .answers(applicationVersion.getAnswers().stream()
                .map(ApplicationAnswerSimpleDTO::of)
                .collect(Collectors.toList()))
            .build();
    }
}
