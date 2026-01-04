package org.jaree.api.jobopening.service;

import org.jaree.api.jobopening.dto.JobOpeningOutputDTO;

public interface JobOpeningServiceInterface {

    JobOpeningOutputDTO getJobOpeningInfo(String id);
}
