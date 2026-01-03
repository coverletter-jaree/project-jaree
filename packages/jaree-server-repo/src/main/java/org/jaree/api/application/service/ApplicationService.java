package org.jaree.api.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jaree.api.application.dto.ApplicationAnswerCreationInputDTO;
import org.jaree.api.application.dto.ApplicationCreationInputDTO;
import org.jaree.api.application.dto.ApplicationOutputDTO;
import org.jaree.api.application.dto.ApplicationQuestionSimpleDTO;
import org.jaree.api.application.dto.ApplicationVersionCommitMessageDTO;
import org.jaree.api.application.dto.ApplicationVersionCreationInputDTO;
import org.jaree.api.application.dto.ApplicationVersionSimpleDTO;
import org.jaree.api.application.entity.Application;
import org.jaree.api.application.entity.ApplicationAnswer;
import org.jaree.api.application.entity.ApplicationQuestion;
import org.jaree.api.application.entity.ApplicationVersion;
import org.jaree.api.application.exception.ApplicationNotFoundException;
import org.jaree.api.application.exception.ApplicationVersionNotFoundException;
import org.jaree.api.application.input.ApplicationContextSaveInputDTO;
import org.jaree.api.application.repository.ApplicationAnswerRepository;
import org.jaree.api.application.repository.ApplicationQuestionRepository;
import org.jaree.api.application.repository.ApplicationRepository;
import org.jaree.api.application.repository.ApplicationVersionRepository;
import org.jaree.api.auth.dto.CustomUserDetails;
import org.jaree.api.core.interfaces.DTOConverter;
import org.jaree.api.core.utils.JacksonDTOConverter;
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
    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final ApplicationVersionRepository applicationVersionRepository;
    private final ApplicationAnswerRepository applicationAnswerRepository;

    private final DTOConverter converter = new JacksonDTOConverter();

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

        return ApplicationOutputDTO.of(application);
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

    public ApplicationVersion getApplicationVersion(String applicationId, String commitId) {
        if (commitId == null) {
            throw new IllegalArgumentException("Commit ID cannot be null");
        }
        ApplicationVersion applicationVersion = applicationVersionRepository.findById(commitId).get();

        if (!applicationVersion.getApplication().getId().equals(applicationId)) {
            throw new IllegalArgumentException("Invalid application ID");
        }

        return applicationVersion;
    }

    /**
     * @author junhyeong
     * 작업 중 필요하다고 생각되는 고려사항, 임시저장임을 나타내는 추가적인 스테이터스 필요함
     */
    public boolean saveAnswerTemporary(String applicationId, String commitId, ApplicationContextSaveInputDTO body) {
        if (commitId == null) {
            throw new IllegalArgumentException("Commit ID cannot be null");
        }
        ApplicationVersion applicationVersion = applicationVersionRepository.findById(commitId).get();

        if (!applicationVersion.getApplication().getId().equals(applicationId)) {
            throw new IllegalArgumentException("Invalid application ID");
        }

        ApplicationQuestion applicationQuestion = applicationQuestionRepository.findById(body.getQuestion().getId()).get();

        ApplicationAnswer applicationAnswer = this.converter.convert(body).applicationAnswer();
        applicationAnswer.setQuestion(applicationQuestion);
        applicationAnswer.setApplicationVersion(applicationVersion);

        try {
            applicationAnswerRepository.save(applicationAnswer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Application> getApplications(CustomUserDetails userDetails) {
        return applicationRepository.findAllByUserId(userDetails.id()).get();
    }

    public boolean deleteApplication(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        try {
            applicationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
