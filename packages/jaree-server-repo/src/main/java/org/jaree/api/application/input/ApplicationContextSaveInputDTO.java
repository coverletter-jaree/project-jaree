package org.jaree.api.application.input;

import java.time.LocalDateTime;
import org.jaree.api.application.entity.ApplicationAnswer;
import org.jaree.api.core.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApplicationContextSaveInputDTO extends DTO<ApplicationContextSaveInputDTO.EntityRecord> {
    private Long id;
    private LocalDateTime createdAt;
    private String s3Link;
    private Question question;

    @Data
    public static class Question {
        private String id;
    }

    public record EntityRecord(ApplicationAnswer applicationAnswer) {
    }
}
