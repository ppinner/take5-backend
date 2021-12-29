package com.example.take5.repository;

import com.example.take5.model.Activity;
import com.example.take5.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

    @Query
    Activity findDistinctById(String id);

    @Query(value="{category:'?0'}", fields="{'name' : 1, 'category' : 1}")
    List<Activity> findAll(String category);

    @Query("{name: {$regex : ?0, $options: 'i'}}")
    List<Activity> findByNameLike(String name);

    @Query("{ category: { $elemMatch: { $eq: '?0' } } }")
    List<Activity> findActivitiesByCategory(Category category);

    long count();
}

