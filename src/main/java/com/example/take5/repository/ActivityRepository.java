package com.example.take5.repository;

import com.example.take5.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

    @Query("{name:'?0'}")
    Activity findActivityByName(String name);

    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    List<Activity> findAll(String category);

    @Query
    List<Activity> findByNameContaining(String name);

    long count();

}

