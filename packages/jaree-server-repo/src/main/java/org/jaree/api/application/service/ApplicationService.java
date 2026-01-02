package org.jaree.api.application.service;

import org.jaree.api.application.dto.ApplicationOutputDTO;
import org.jaree.api.application.dto.ApplicationUpdateInputDTO;
import org.jaree.api.auth.dto.CustomUserDetails;

public interface ApplicationService {
    ApplicationOutputDTO updateApplicationInfo(String id, CustomUserDetails user, ApplicationUpdateInputDTO applicationUpdateInputDTO);
}
