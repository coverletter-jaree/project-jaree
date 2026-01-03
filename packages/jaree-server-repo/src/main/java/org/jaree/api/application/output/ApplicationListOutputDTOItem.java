package org.jaree.api.application.output;

import java.time.LocalDateTime;
import org.jaree.api.application.entity.Application;
import org.jaree.api.core.dto.DTO;
import org.jaree.api.jobopening.entity.JobOpening;
import org.jaree.api.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApplicationListOutputDTOItem extends DTO<ApplicationListOutputDTOItem.EntityRecord> {
    private Long id;
    private String title;
    private String position;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueAt;
    private User user;
    private JobOpening jobOpening;

    public record EntityRecord(Application application) {}
}
