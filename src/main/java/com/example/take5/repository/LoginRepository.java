package com.example.take5.repository;

import com.example.take5.model.Login;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends MongoRepository<Login, String> {
    @Query
    Login findDistinctById(String id);

    @Query
    void deleteLoginByUserId(String id);
}
