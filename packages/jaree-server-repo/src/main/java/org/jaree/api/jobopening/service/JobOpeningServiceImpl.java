package org.jaree.api.jobopening.service;

import org.jaree.api.jobopening.dto.JobOpeningOutputDTO;
import org.jaree.api.jobopening.entity.JobOpening;
import org.jaree.api.jobopening.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobOpeningServiceImpl implements JobOpeningService {

    private final JobOpeningRepository jobOpeningRepository;

    @Override
    public JobOpeningOutputDTO getJobOpeningInfo(String id) {

        JobOpening jobOpening = jobOpeningRepository.findByIdWithoutApplications(id)
            .orElseThrow(() -> new RuntimeException("JobOpening Not Found"));

        return JobOpeningOutputDTO.from(jobOpening);
    }
}
