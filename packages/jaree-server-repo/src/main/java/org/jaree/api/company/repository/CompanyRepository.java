package org.jaree.api.company.repository;

import java.util.Optional;

import org.jaree.api.company.entity.Company;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CompanyRepository extends Neo4jRepository<Company, String> {
    @Query("""
        MATCH p1 = (c:Company {id: $id})<-[:CREATED_BY]-(j:JobOpening)
        OPTIONAL MATCH p2 = (j)-[:ASKS]->(q:ApplicationQuestion)
        RETURN c, collect(p1) AS p1, collect(p2) AS p2
        """)
    Optional<Company> findByIdWithJobOpeningsWithQuestion(String id);
}
