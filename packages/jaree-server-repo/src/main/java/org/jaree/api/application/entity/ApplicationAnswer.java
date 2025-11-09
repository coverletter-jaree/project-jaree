package org.jaree.api.application.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("ApplicationAnswer")
public class ApplicationAnswer {
    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;    // 답변 생성일자

    private String s3Link;              // 자소서 답변이 저장되어있는 S3 link

    @Transient
    private String content;             // 실제로 노드에 저장되지는 않지만, 향후 편의성을 위해 S3에서 가져온 자소서 답변를 저장할 수 있는 필드

    @Relationship(type = "ANSWERS", direction = Relationship.Direction.INCOMING)
    private ApplicationVersion applicationVersion;        // 해당 답변이 속해있는 자소서 버전

    @Relationship(type = "ANSWERS_TO", direction = Relationship.Direction.OUTGOING)
    private ApplicationQuestion question;
}
