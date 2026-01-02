package org.jaree.api.application.repository;

import org.jaree.api.application.entity.Application;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ApplicationRepository extends Neo4jRepository<Application, String> {
}
