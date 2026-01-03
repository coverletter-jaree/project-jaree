package org.jaree.api.jobopening.repository;

import org.jaree.api.jobopening.entity.JobOpening;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface JobOpeningRepository extends Neo4jRepository<JobOpening, String> {
}
