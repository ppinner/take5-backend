package com.example.take5.repository;

import com.example.take5.entity.Activity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    List<Activity> findById(int id);
}
