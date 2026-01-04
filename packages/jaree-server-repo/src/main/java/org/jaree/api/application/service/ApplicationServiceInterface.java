package org.jaree.api.application.service;

import java.util.List;

import org.jaree.api.application.dto.ApplicationCreationInputDTO;
import org.jaree.api.application.dto.ApplicationOutputDTO;
import org.jaree.api.application.dto.ApplicationUpdateInputDTO;
import org.jaree.api.application.dto.ApplicationVersionCommitMessageDTO;
import org.jaree.api.application.dto.ApplicationVersionCreationInputDTO;
import org.jaree.api.application.dto.ApplicationVersionSimpleDTO;
import org.jaree.api.auth.dto.CustomUserDetails;

public interface ApplicationServiceInterface {

    ApplicationVersionSimpleDTO getMostRecentApplicationVersion(String applicationId, CustomUserDetails userDetails);

    List<ApplicationVersionCommitMessageDTO> getApplicationVersionList(String applicationId, CustomUserDetails userDetails);

    ApplicationOutputDTO createApplication(ApplicationCreationInputDTO dto, CustomUserDetails userDetails);

    ApplicationVersionSimpleDTO createApplicationVersion(ApplicationVersionCreationInputDTO dto, CustomUserDetails userDetails);

    ApplicationOutputDTO updateApplicationInfo(String id, CustomUserDetails user, ApplicationUpdateInputDTO dto);

}
