package org.jaree.api.application.repositories;

import org.jaree.api.application.entity.ApplicationAnswer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface applicationAnswerRepository extends Neo4jRepository<ApplicationAnswer, String> {

}
