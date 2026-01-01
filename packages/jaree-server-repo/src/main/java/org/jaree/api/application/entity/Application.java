package org.jaree.api.application.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.jaree.api.application.enums.ApplicationStatus;
import org.jaree.api.jobopening.entity.JobOpening;
import org.jaree.api.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Application: 지원서(자소서) 엔티티
 * - 특정 채용공고에 대해 사용자가 작성한 지원서/자소서를 의미
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("Application")
public class Application {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    private String title;               // 자소서 제목

    private String position;            // 지원 직무

    private ApplicationStatus status;   // 자소서 상태 (ex. 작성 중, 합격, 제출 완료..)

    @CreatedDate
    private LocalDateTime createdAt;    // 자소서 생성일자

    @LastModifiedDate
    private LocalDateTime updatedAt;    // 자소서 마지막 수정일자

    private LocalDateTime dueAt;        // 자소서 마감일자

    @Relationship(type = "HAS_QUESTION", direction = Relationship.Direction.OUTGOING)
    private List<ApplicationQuestion> questions;    // 자소서 질문 항목 리스트

    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.OUTGOING)
    private User user;  // 자소서 작성자

    @Relationship(type = "APPLIES_FOR", direction = Relationship.Direction.OUTGOING)
    private JobOpening jobOpening;  // 자소서 관련 채용 공고

    @Relationship(type = "HAS_VERSION", direction = Relationship.Direction.OUTGOING)
    private List<ApplicationVersion> versions;  // 자소서 커밋 리스트(leaf node가 여러개일 수 있기 때문에 list)
}
