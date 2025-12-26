package org.jaree.api.user.repository;

import org.jaree.api.user.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, Long> {
}
