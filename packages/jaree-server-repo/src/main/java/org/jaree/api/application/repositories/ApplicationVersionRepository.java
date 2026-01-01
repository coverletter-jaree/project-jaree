package org.jaree.api.application.repositories;

import org.jaree.api.application.entity.ApplicationVersion;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationVersionRepository extends Neo4jRepository<ApplicationVersion, String> {

}
