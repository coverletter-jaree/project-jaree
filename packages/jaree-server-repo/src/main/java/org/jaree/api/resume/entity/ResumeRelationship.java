package org.jaree.api.resume.entity;

import org.jaree.api.resume.enums.ResumeRelationshipType;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RelationshipProperties
public class ResumeRelationship {
    private final ResumeRelationshipType type;  // 관계 종류 (이력서 항목)

    @TargetNode
    private final JsonData data;    // 이력서 저장 데이터
}
