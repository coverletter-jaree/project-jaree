package org.jaree.api.application.output;

import java.util.List;
import org.jaree.api.application.entity.ApplicationVersion;
import org.jaree.api.core.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApplicationVersionOutputDTO extends DTO<ApplicationVersionOutputDTO.EntityRecord> {
    String id;
    String commitMessage;
    List<Answer> answers;

    @Data
    public static class Answer {
        Question question;
        String s3Link;

        @Data
        public static class Question {
            String id;
            String content;
            String description;
            int order;
        }
    }

    public record EntityRecord(ApplicationVersion applicationVersion) {
    }
}
