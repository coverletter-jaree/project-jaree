package org.jaree.api.company.repository;

import java.util.Optional;

import org.jaree.api.company.entity.Company;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CompanyRepository extends Neo4jRepository<Company, Long> {
    @Query("""
        MATCH (c:Company)
        WHERE elementId(c) = $id
        OPTIONAL MATCH (c)<-[r1:CREATED_BY]-(j:JobOpening)
        OPTIONAL MATCH (j)-[r2:ASKS]->(q:ApplicationQuestion)
        RETURN c,
        collect(r1) AS rel_j, collect(j) AS jobOpenings,
        collect(r2) AS rel_q, collect(q) AS questions
        """)
    Optional<Company> findByIdWithJobOpeningsWithQuestion(Long id);
}
