package org.jaree.api.application.service;

import java.util.ArrayList;
import java.util.List;

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

        if(dto.getTitle() != null){
            application.setTitle(dto.getTitle());
        }
        if(dto.getPosition() != null){
            application.setPosition(dto.getPosition());
        }
        if(dto.getStatus() != null){
            application.setStatus(dto.getStatus());
        }
        if(dto.getDueAt() != null){
            application.setDueAt(dto.getDueAt());
        }

        /* questions */
        if(dto.getQuestions() != null){
            List<ApplicationUpdateInputDTO.ApplicationQuestionDTO> inputQuestions = dto.getQuestions();
            List<ApplicationQuestion> newQuestions = new ArrayList<>();

            for(ApplicationUpdateInputDTO.ApplicationQuestionDTO qdto : inputQuestions){

                if(qdto.getId() == null){
                    ApplicationQuestion created = new ApplicationQuestion();
                    created.setContent(qdto.getContent());
                    created.setDescription(qdto.getDescription());
                    created.setOrder(qdto.getOrder());
                    newQuestions.add(created);
                }else{
                    ApplicationQuestion existing = applicationQuestionRepository.findById(qdto.getId())
                        .orElseThrow(()-> new RuntimeException("ApplicationQuestion not found"));
                    newQuestions.add(existing);
                }
            }
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
