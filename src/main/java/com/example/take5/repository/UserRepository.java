package com.example.take5.repository;

import com.example.take5.model.Personality;
import com.example.take5.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query
    User findDistinctById(String id);

    //TODO - Add methods to find users with similar personality to specified
//    @Query
//    List<User> findUsersByPersonalityLike(Personality personality);

    long count();
}

