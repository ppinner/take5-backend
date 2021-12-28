package com.example.take5.controller;

import com.example.take5.entity.Activity;
import com.example.take5.exception.ActivityIdMismatchException;
import com.example.take5.exception.ActivityNotFoundException;
import com.example.take5.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@EnableMongoRepositories(basePackages = {"com.me.service.testservice.repository"}, considerNestedRepositories = true)
@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping
    public Iterable findAll() {
        return activityRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Activity> findOne(@PathVariable Long id) {
        return activityRepository.findById(id).stream().findFirst();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Activity create(@RequestBody Activity book) {
        return activityRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        activityRepository.findById(id);
        activityRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Activity updateActivity(@RequestBody Activity activity, @PathVariable Long id) {
        activityRepository.findById(id);
        return activityRepository.save(activity);
    }
}