package org.jaree.api.jobopening.repository;

import java.util.Optional;

import org.jaree.api.jobopening.entity.JobOpening;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface JobOpeningRepository extends Neo4jRepository<JobOpening, String> {
    @Query("""
        MATCH p1 = (j:JobOpening {id: $id})-[:CREATED_BY]->(c:Company)
        OPTIONAL MATCH p2 = (j)-[:ASKS]->(q:ApplicationQuestion)
        RETURN j, collect(p1) AS p1, collect(p2) AS p2
        """)
    Optional<JobOpening> findByIdWithoutApplications(String id);
}
