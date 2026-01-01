package org.jaree.api.application.services;

import java.util.List;

import org.jaree.api.application.entity.Application;
import org.jaree.api.application.entity.ApplicationAnswer;
import org.jaree.api.application.entity.ApplicationVersion;
import org.jaree.api.application.inputs.ApplicationContextSaveInputDTO;
import org.jaree.api.application.repositories.ApplicationRepository;
import org.jaree.api.application.repositories.ApplicationVersionRepository;
import org.jaree.api.application.repositories.applicationAnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
  private final ApplicationRepository applicationRepository;
  private final ApplicationVersionRepository applicationVersionRepository;    
  private final applicationAnswerRepository applicationAnswerRepository;

  public ApplicationService(ApplicationRepository applicationRepository, ApplicationVersionRepository applicationVersionRepository, applicationAnswerRepository applicationAnswerRepository) {
    this.applicationRepository = applicationRepository;
    this.applicationVersionRepository = applicationVersionRepository;
    this.applicationAnswerRepository = applicationAnswerRepository;
  }

  public ApplicationVersion getApplication(String applicationId, String commitId) {
    if (applicationId == null) {
      throw new IllegalArgumentException("Application ID cannot be null");
    }
    Application application = applicationRepository.findById(applicationId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + applicationId));

    List<ApplicationVersion> applicationVersions = application.getVersions();
    ApplicationVersion applicationVersion = applicationVersions.stream()
        .filter(version -> version.getId().equals(commitId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid commit ID: " + commitId));

    return applicationVersion;
  }

  public boolean saveApplication(String applicationId, String commitId, ApplicationContextSaveInputDTO body) {
    // TODO: convert to ApplicationContextSaveInputDTO to ApplicationAnswer
    ApplicationAnswer applicationAnswer = new ApplicationAnswer();

    // TODO: make ApplicationVersion with Answer
    ApplicationVersion applicationVersion = new ApplicationVersion();
    applicationVersion.setAnswers(List.of(applicationAnswer));
    applicationVersion.setId(commitId);

    // TODO: make Connection Version and Application
    applicationVersionRepository.save(applicationVersion);
    applicationAnswerRepository.save(applicationAnswer);
    

    return true;
  }

  public List<Application> getApplications() {
    return applicationRepository.findAll();
  }

  public boolean deleteApplication(String id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    applicationRepository.deleteById(id);
    return true;
  }

}
