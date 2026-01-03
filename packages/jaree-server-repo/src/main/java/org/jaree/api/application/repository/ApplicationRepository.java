package org.jaree.api.application.repository;

import java.util.List;
import java.util.Optional;
import org.jaree.api.application.entity.Application;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationRepository extends Neo4jRepository<Application, String> {
    @Query("MATCH (u:User {id: $userId})-[:WRITTEN_BY]-(a:Application {id: $id}) RETURN a")
    Optional<Application> findByIdAndUserId(@Param("id") String id, @Param("userId") String userId);

    @Query("MATCH (u:User {id: $userId})-[:WRITTEN_BY]-(a:Application) RETURN a")
    Optional<List<Application>> findAllByUserId(@Param("userId") String userId);
}
