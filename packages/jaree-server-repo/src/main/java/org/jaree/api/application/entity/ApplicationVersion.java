package org.jaree.api.application.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
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
 * ApplicationVersion: 자소서 커밋(자소서 버전) 엔티티
 * - 자소서의 특정 버전을 기록한 커밋을 의미함
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("ApplicationVersion")
public class ApplicationVersion {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    private String commitMessage;       // 자소서 커밋 제목

    @CreatedDate
    private LocalDateTime createdAt;    // 자소서 커밋 생성일자

    @Relationship(type = "CHANGED_FROM", direction = Relationship.Direction.OUTGOING)
    private ApplicationVersion previousVersion;     // 해당 커밋의 직전 커밋

    @Relationship(type = "ANSWERS", direction = Relationship.Direction.OUTGOING)
    private List<ApplicationAnswer> answers;        // 해당 버전에서 작성한 답변 리스트
}
