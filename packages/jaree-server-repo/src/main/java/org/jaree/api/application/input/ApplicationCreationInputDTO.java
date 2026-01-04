package org.jaree.api.application.input;

import org.jaree.api.application.entity.Application;
import org.jaree.api.application.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCreationInputDTO {
    private String title;
    private String position;
    private String jobOpeningId;

    public Application toEntity() {
        return Application.builder()
            .title(title)
            .position(position)
            .status(ApplicationStatus.NOT_STARTED)
            .build();
    }
}
