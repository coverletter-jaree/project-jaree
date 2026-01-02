package org.jaree.api.application.service;

import java.util.List;
import java.util.Optional;

import org.jaree.api.application.dto.ApplicationOutputDTO;
import org.jaree.api.application.dto.ApplicationUpdateInputDTO;
import org.jaree.api.application.entity.Application;
import org.jaree.api.application.entity.ApplicationQuestion;
import org.jaree.api.application.repository.ApplicationQuestionRepository;
import org.jaree.api.application.repository.ApplicationRepository;
import org.jaree.api.auth.dto.CustomUserDetails;
import org.jaree.api.jobopening.entity.JobOpening;
import org.jaree.api.jobopening.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{

    private final ApplicationRepository applicationRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final JobOpeningRepository jobOpeningRepository;

    @Override
    @Transactional
    public ApplicationOutputDTO updateApplicationInfo(String id, CustomUserDetails user,
        ApplicationUpdateInputDTO dto) {

        Application application = applicationRepository.findById(id)
            .orElseThrow(()-> new RuntimeException("Application not found"));

        if(!application.getUser().getId().equals(user.id())){
            throw new RuntimeException("User is not an owner of this application");
        }

        Optional.ofNullable(dto.getTitle()).ifPresent(application::setTitle);
        Optional.ofNullable(dto.getPosition()).ifPresent(application::setPosition);
        Optional.ofNullable(dto.getStatus()).ifPresent(application::setStatus);
        Optional.ofNullable(dto.getDueAt()).ifPresent(application::setDueAt);

        /* questions */
        if(dto.getQuestions() != null){
            List<ApplicationQuestion> newQuestions =  dto.getQuestions().stream()
                .map(qdto -> {
                    if(qdto.getId() == null){
                        return ApplicationQuestion.builder()
                            .content(qdto.getContent())
                            .description(qdto.getDescription())
                            .order(qdto.getOrder())
                            .build();
                    }
                    return applicationQuestionRepository.findById(qdto.getId())
                        .orElseThrow(() -> new RuntimeException("ApplicationQuestion not found"));
                }).toList();
            application.setQuestions(newQuestions);
        }

        /* jobOpening */
        if(dto.getJobOpening() != null){
            String inputJobOpeningId = dto.getJobOpening().getId();
            if(inputJobOpeningId == null){
                application.setJobOpening(null);
            }else{
                JobOpening jobOpening = jobOpeningRepository.findById(inputJobOpeningId)
                    .orElseThrow(()-> new RuntimeException("JobOpening not found"));
                application.setJobOpening(jobOpening);
            }
        }

        return ApplicationOutputDTO.from(applicationRepository.save(application));
    }
}
