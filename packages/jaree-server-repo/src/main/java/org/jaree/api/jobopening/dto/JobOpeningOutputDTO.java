package org.jaree.api.jobopening.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.jaree.api.company.dto.CompanyOutputDTO;

public class JobOpeningOutputDTO{
    Long id;
    String title;
    String description;
    String contentS3Url;
    String imageUrl;
    LocalDateTime startsAt;
    LocalDateTime endsAt;
    LocalDateTime createdAt;
    CompanyOutputDTO company;
    List<ApplicationQuestionDTO> questions;

    public static class ApplicationQuestionDTO{
        Long id;
        String content;
        String description;
        Integer order;
    }
}
