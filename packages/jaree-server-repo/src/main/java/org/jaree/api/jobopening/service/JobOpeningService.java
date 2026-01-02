package org.jaree.api.jobopening.service;

import org.jaree.api.jobopening.dto.JobOpeningOutputDTO;

public interface JobOpeningService {

    JobOpeningOutputDTO getJobOpeningInfo(String id);
}
