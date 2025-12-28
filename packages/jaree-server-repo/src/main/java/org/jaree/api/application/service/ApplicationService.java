package org.jaree.api.application.service;

import java.util.List;

import org.jaree.api.application.dto.ApplicationCreationInputDTO;
import org.jaree.api.application.dto.ApplicationOutputDTO;
import org.jaree.api.application.entity.Application;
import org.jaree.api.application.entity.ApplicationQuestion;
import org.jaree.api.application.repository.ApplicationRepository;
import org.jaree.api.auth.dto.CustomUserDetails;
import org.jaree.api.jobopening.entity.JobOpening;
import org.jaree.api.jobopening.exception.JobOpeningNotFoundException;
import org.jaree.api.jobopening.repository.JobOpeningRepository;
import org.jaree.api.user.entity.User;
import org.jaree.api.user.exception.UserNotFoundException;
import org.jaree.api.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobOpeningRepository jobOpeningRepository;

    /**
     * 자소서 생성
     */
    @Transactional
    public ApplicationOutputDTO createApplication(ApplicationCreationInputDTO dto, CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.id()).orElseThrow(UserNotFoundException::new);

        Application application = dto.toEntity();
        application.setUser(user);

        if(dto.getJobOpeningId() != null) {
            JobOpening jobOpening = jobOpeningRepository.findById(dto.getJobOpeningId())
                .orElseThrow(JobOpeningNotFoundException::new);

            List<ApplicationQuestion> questions = jobOpening.getQuestions();
            application.setQuestions(questions);
        }

        applicationRepository.save(application);

        return ApplicationOutputDTO.of(application);
    }
}
