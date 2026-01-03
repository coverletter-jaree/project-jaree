package org.jaree.api.application.dto;

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
public class ApplicationVersionCommitMessageDTO {
    private String id;
    private String commitMessage;
    private LocalDateTime createdAt;
    private List<String> ancestorIds;

    public static ApplicationVersionCommitMessageDTO of(ApplicationVersion applicationVersion) {
        if (applicationVersion == null) {
            return null;
        }

        return ApplicationVersionCommitMessageDTO.builder()
            .id(applicationVersion.getId())
            .commitMessage(applicationVersion.getCommitMessage())
            .createdAt(applicationVersion.getCreatedAt())
            .ancestorIds(applicationVersion.getAncestors().stream()
                .map(ApplicationVersion::getId)
                .collect(Collectors.toList()))
            .build();
    }
}
