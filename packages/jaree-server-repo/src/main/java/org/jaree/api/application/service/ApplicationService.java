package org.jaree.api.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jaree.api.application.dto.ApplicationAnswerCreationInputDTO;
import org.jaree.api.application.dto.ApplicationCreationInputDTO;
import org.jaree.api.application.dto.ApplicationOutputDTO;
import org.jaree.api.application.dto.ApplicationQuestionSimpleDTO;
import org.jaree.api.application.dto.ApplicationUpdateInputDTO;
import org.jaree.api.application.dto.ApplicationVersionCommitMessageDTO;
import org.jaree.api.application.dto.ApplicationVersionCreationInputDTO;
import org.jaree.api.application.dto.ApplicationVersionSimpleDTO;
import org.jaree.api.application.entity.Application;
import org.jaree.api.application.entity.ApplicationAnswer;
import org.jaree.api.application.entity.ApplicationQuestion;
import org.jaree.api.application.entity.ApplicationVersion;
import org.jaree.api.application.exception.ApplicationNotFoundException;
import org.jaree.api.application.exception.ApplicationVersionNotFoundException;
import org.jaree.api.application.repository.ApplicationAnswerRepository;
import org.jaree.api.application.repository.ApplicationQuestionRepository;
import org.jaree.api.application.repository.ApplicationRepository;
import org.jaree.api.application.repository.ApplicationVersionRepository;
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
public class ApplicationService implements ApplicationServiceInterface {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobOpeningRepository jobOpeningRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final ApplicationVersionRepository applicationVersionRepository;
    private final ApplicationAnswerRepository applicationAnswerRepository;

    /**
     * 특정 자소서의 가장 최근 커밋 조회
     */
    public ApplicationVersionSimpleDTO getMostRecentApplicationVersion(String applicationId, CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.id()).orElseThrow(UserNotFoundException::new);
        Application application = applicationRepository.findByIdAndUserId(applicationId, user.getId())
            .orElseThrow(ApplicationNotFoundException::new);

        ApplicationVersion applicationVersion = applicationVersionRepository.findLatestByApplicationId(applicationId)
            .orElse(null);

        return ApplicationVersionSimpleDTO.of(applicationVersion);
    }

    /**
     * 특정 자소서의 모든 커밋을 최신순으로 조회
     */
    public List<ApplicationVersionCommitMessageDTO> getApplicationVersionList(String applicationId, CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.id()).orElseThrow(UserNotFoundException::new);
        Application application = applicationRepository.findByIdAndUserId(applicationId, user.getId())
            .orElseThrow(ApplicationNotFoundException::new);

        List<ApplicationVersion> applicationVersionList = applicationVersionRepository.findByApplicationId(applicationId);

        return applicationVersionList.stream().map(ApplicationVersionCommitMessageDTO::of).collect(Collectors.toList());
    }

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

        return ApplicationOutputDTO.from(application);
    }

    /**
     * 자소서 커밋 생성
     */
    @Transactional
    public ApplicationVersionSimpleDTO createApplicationVersion(ApplicationVersionCreationInputDTO dto, CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.id()).orElseThrow(UserNotFoundException::new);
        Application application = applicationRepository.findByIdAndUserId(dto.getApplicationId(), user.getId())
            .orElseThrow(ApplicationNotFoundException::new);

        ApplicationVersion applicationVersion = dto.toEntity();

        // ancestor 설정
        if(dto.getPreviousVersionId() != null) {
            ApplicationVersion previousVersion = applicationVersionRepository.findById(dto.getPreviousVersionId())
                .orElseThrow(ApplicationVersionNotFoundException::new);

            List<ApplicationVersion> ancestors = previousVersion.getAncestors();
            ancestors.add(previousVersion);

            applicationVersion.setAncestors(ancestors);
        }

        // answer 생성
        List<ApplicationAnswer> answers = new ArrayList<>();
        for(ApplicationAnswerCreationInputDTO answerDto: dto.getAnswers()) {
            ApplicationAnswer answer = answerDto.toEntity();
            ApplicationQuestionSimpleDTO questionDto = answerDto.getQuestion();

            // ApplicationQuestion 생성 or 조회
            ApplicationQuestion question = applicationQuestionRepository.findById(questionDto.getId())
                .orElse(applicationQuestionRepository.save(questionDto.toEntity()));

            answer.setQuestion(question);

            // ApplicationAnswer 생성
            applicationAnswerRepository.save(answer);

            answers.add(answer);
        }

        // 커밋 저장
        applicationVersion.setAnswers(answers);
        applicationVersion.setApplication(application);
        applicationVersionRepository.save(applicationVersion);

        // answer와 version 관계 설정
        for(ApplicationAnswer answer: answers) {
            answer.setApplicationVersion(applicationVersion);
        }
        applicationAnswerRepository.saveAll(answers);

        return ApplicationVersionSimpleDTO.of(applicationVersion);
    }

    /**
     * 자소서 기본 정보 수정
     */
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
