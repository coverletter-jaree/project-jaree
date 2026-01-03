package org.jaree.api.application.repository;

import java.util.List;
import java.util.Optional;

import org.jaree.api.application.entity.ApplicationVersion;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationVersionRepository extends Neo4jRepository<ApplicationVersion, String> {
    @Query("""
        MATCH (a:Application {id: $applicationId})-[:HAS_VERSION]->(v:ApplicationVersion)
        OPTIONAL MATCH (v)-[ra:ANCESTOR_OF]->(anc:ApplicationVersion)
        OPTIONAL MATCH (v)-[r:ANSWERS]->(ans:ApplicationAnswer)
        OPTIONAL MATCH (ans)-[qr:ANSWERS_TO]->(q:ApplicationQuestion)
        RETURN v, collect(anc), collect(ra), collect(r), collect(ans), collect(qr), collect(q)
        ORDER BY v.createdAt DESC LIMIT 1
    """)
    Optional<ApplicationVersion> findLatestByApplicationId(@Param("applicationId") String applicationId);

    @Query("""
        MATCH (a:Application {id: $applicationId})-[:HAS_VERSION]->(v:ApplicationVersion)
        OPTIONAL MATCH (v)-[ra:ANCESTOR_OF]->(anc:ApplicationVersion)
        RETURN v, collect(anc), collect(ra)
        ORDER BY v.createdAt DESC
    """)
    List<ApplicationVersion> findByApplicationId(@Param("applicationId") String applicationId);
}
