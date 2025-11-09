package org.jaree.api.application.entity;


import java.util.List;

import org.jaree.api.jobopening.entity.JobOpening;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ApplicationQuestion: 자소서 질문 항목 엔티티
 * - 자소서에 등록된 질문 항목을 의미
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("ApplicationQuestion")
public class ApplicationQuestion {
    @Id
    @GeneratedValue
    private Long id;

    private String content;        // 자소서 질문

    private String description;     // 질문에 대한 부가적인 설명

    private int order;              // 질문 순서

    @Relationship(type = "ANSWERS_TO", direction = Relationship.Direction.INCOMING)
    private List<ApplicationAnswer> answers;        // 해당 질문을 답한 답변 리스트

    @Relationship(type = "HAS_QUESTION", direction = Relationship.Direction.INCOMING)
    private List<Application> applications;         // 해당 질문을 가지고 있는 자소서 리스트

    @Relationship(type = "ASKS", direction = Relationship.Direction.INCOMING)
    private List<JobOpening> jobOpenings;           // 해당 질문을 가지고 있는 지원 공고 리스트

}
