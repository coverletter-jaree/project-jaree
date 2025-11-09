package org.jaree.api.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.jaree.api.application.entity.Application;
import org.jaree.api.resume.entity.Resume;
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
 * User: 사용자 엔티티
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("User")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.INCOMING)
    private List<Application> applications; // 사용자가 작성한 자소서 리스트

    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.INCOMING)
    private Resume resume;  // 사용자가 작성한 이력서
}
