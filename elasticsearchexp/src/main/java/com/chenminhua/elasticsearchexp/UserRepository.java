package com.chenminhua.elasticsearchexp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ElasticsearchRepository<User,String> {

    @Override
    Optional<User> findById(String id);

    List<User> findByName(String name);

    Page<User> findByCity(String city, Pageable pageable);
}
