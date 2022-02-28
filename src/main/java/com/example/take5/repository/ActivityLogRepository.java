package com.example.take5.repository;

import com.example.take5.model.ActivityLogEntry;
import com.example.take5.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends MongoRepository<ActivityLogEntry, String> {

    @Query
    ActivityLogEntry findDistinctById(String id);

    @Query(value = "{category:'?0'}", fields = "{'name' : 1, 'category' : 1}")
    List<ActivityLogEntry> findAll(String category);

    @Query
    List<ActivityLogEntry> findByUserId(String userId);

    @Query("{ category: { $elemMatch: { $eq: '?0' } } }")
    List<ActivityLogEntry> findActivityLogEntriesByCategory(Category category);

    long count();
}

