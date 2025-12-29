package org.jaree.api.company.entity;

import java.util.List;

import org.jaree.api.company.enums.CompanyCategory;
import org.jaree.api.jobopening.entity.JobOpening;
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
 * Company: 회사 엔티티
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("Company")
public class Company {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    private String name;

    private String description;         // 회사 설명

    private String logoUrl;             // 회사 대표 이미지 링크

    private List<CompanyCategory> categories;    // etc. 대기업, 중견기업, 스타트업

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.INCOMING)
    private List<JobOpening> jobOpenings;   // 회사 공고 리스트
}
