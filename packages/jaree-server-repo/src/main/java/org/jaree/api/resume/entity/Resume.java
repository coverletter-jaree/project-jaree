package org.jaree.api.resume.entity;

import java.util.List;

import org.jaree.api.user.entity.User;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Resume: 이력서 엔티티
 * - 학력, 경험, 경력 등을 작성하는 이력서
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("Resume")
public class Resume {
    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.OUTGOING)
    private User user;      // 이력서 작성한 사용자

    @Relationship(direction = Relationship.Direction.OUTGOING)
    private List<ResumeRelationship> data;     // 이력서 저장 데이터(내용) 목록
    }
