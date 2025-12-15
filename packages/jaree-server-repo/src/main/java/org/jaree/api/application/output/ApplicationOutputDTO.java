package org.jaree.api.application.output;

import java.time.LocalDateTime;
import java.util.List;

import org.jaree.api.application.entity.ApplicationQuestion;
import org.jaree.api.application.entity.ApplicationVersion;
import org.jaree.api.jobopening.entity.JobOpening;
import org.jaree.api.user.entity.User;

import lombok.Data;

@Data
public class ApplicationOutputDTO {
  private Long id;
  private String title;
  private String position;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime dueAt;
  private List<ApplicationQuestion> questions;
  private User user;
  private JobOpening jobOpening;
  private List<ApplicationVersion> versions;
}
