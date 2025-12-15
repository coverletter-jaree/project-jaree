package org.jaree.api.application.output;

import java.time.LocalDateTime;

import org.jaree.api.jobopening.entity.JobOpening;
import org.jaree.api.user.entity.User;

import lombok.Data;

@Data
public class ApplicationListOutputDTOItem {
  private Long id;
  private String title;
  private String position;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime dueAt;
  private User user;
  private JobOpening jobOpening;
}
