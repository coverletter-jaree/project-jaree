package org.jaree.api.application.repository;

import org.jaree.api.application.entity.ApplicationAnswer;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ApplicationAnswerRepository extends Neo4jRepository<ApplicationAnswer, String> {
}
