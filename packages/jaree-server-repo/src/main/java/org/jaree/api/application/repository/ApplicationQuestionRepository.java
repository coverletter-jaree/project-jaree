package org.jaree.api.application.repository;

import org.jaree.api.application.entity.ApplicationQuestion;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ApplicationQuestionRepository extends Neo4jRepository<ApplicationQuestion, String> {
}
