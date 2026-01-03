package org.jaree.api.application.input;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApplicationContextSaveInputDTO {
    private Long id;
    private LocalDateTime createdAt;
    private String s3Link;
    private Question question;

    @Data
    public static class Question {
        private String id;
    }
}
