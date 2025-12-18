package org.jaree.api.application.services;

import java.util.List;

import org.jaree.api.application.entity.Application;
import org.jaree.api.application.repositories.ApplicationRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
  private final ApplicationRepository applicationRepository;

  public ApplicationService(ApplicationRepository applicationRepository) {
    this.applicationRepository = applicationRepository;
  }

  public List<Application> getApplications() {
    return applicationRepository.findAll();
  }

  public boolean deleteApplication(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    applicationRepository.deleteById(id);
    return true;
  }

}
