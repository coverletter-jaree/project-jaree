package org.jaree.api.application.repositories;

import org.jaree.api.application.entity.Application;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends Neo4jRepository<Application, Long> {
}
