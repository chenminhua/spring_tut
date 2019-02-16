package com.chenminhua.neo4jdemo.repository;

import com.chenminhua.neo4jdemo.model.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Collection;

public interface UserRepository extends Neo4jRepository<User, Long> {


    @Query("MATCH (u:User)<-[r:RATED]-(m:Movie) RETURN u,r,m")
    Collection<User> getAllUsers();
}
