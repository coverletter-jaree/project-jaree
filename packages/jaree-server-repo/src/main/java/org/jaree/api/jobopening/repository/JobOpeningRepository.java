package org.jaree.api.jobopening.repository;

import java.util.Optional;

import org.jaree.api.jobopening.entity.JobOpening;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface JobOpeningRepository extends Neo4jRepository<JobOpening, Long> {
    @Query("""
        MATCH (j:JobOpening)
        WHERE elementId(j) = $id
        OPTIONAL MATCH (j:JobOpening)->[r1:CREATED_BY]-(c:Company)
        OPTIONAL MATCH (j)-[r2:ASKS]->(q:ApplicationQuestion)
        return j,
        collect(r1) AS rel_c, collect(c) as company,
        collect(r2) AS rel_q, collect(q) as questions
        """)
    Optional<JobOpening> findByIdWithoutApplications(Long id);
}
