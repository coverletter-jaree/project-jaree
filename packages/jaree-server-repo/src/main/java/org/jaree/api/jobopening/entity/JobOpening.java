package org.jaree.api.jobopening.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.jaree.api.application.entity.Application;
import org.jaree.api.application.entity.ApplicationQuestion;
import org.jaree.api.company.entity.Company;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JobOpening: 채용 공고 엔티티
 * - 사용자들이 지원하는 채용 공고를 의미
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("JobOpening")
public class JobOpening {
    @Id
    @GeneratedValue
    private Long id;

    private String title;               // 공고 제목

    private String description;         // 공고 간단 설명

    private String contentS3Url;        // 공고 내용 (사진일 수 있어 S3에 공고를 저장하는 걸로)

    private String imageUrl;            // 공고 대표 이미지 링크

    private LocalDateTime startsAt;     // 지원 가능 시작일

    private LocalDateTime endsAt;       // 지원 마감일

    @CreatedDate
    private LocalDateTime createdAt;    // 공고 생성일

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.OUTGOING)
    private Company company;            // 공고 낸 회사

    @Relationship(type = "APPLIES_FOR", direction = Relationship.Direction.INCOMING)
    private List<Application> applications;     // 해당 공고에 지원한 자소서 리스트

    @Relationship(type = "ASKS", direction = Relationship.Direction.OUTGOING)
    private List<ApplicationQuestion> questions; // 해당 공고가 갖는 자소서 질문 리스트
}
