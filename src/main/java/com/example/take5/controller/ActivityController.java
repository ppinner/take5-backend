package com.example.take5.controller;

import com.example.take5.model.Activity;
import com.example.take5.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RestController
@EnableMongoRepositories(basePackages = {"com.me.service.testservice.repository"}, considerNestedRepositories = true)
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping("")
    ResponseEntity<List<Activity>> getActivities(@RequestParam(required = false) String title) {
        try {
            List<Activity> activities = new ArrayList<>();

            if (title == null)
                activityRepository.findAll().forEach(activities::add);
            else
                activityRepository.findByNameContaining(title).forEach(activities::add);

            if (activities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(activities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> findOne(@PathVariable("id") String id) {
        Optional<Activity> activityData = activityRepository.findById(id);

        if (activityData.isPresent()) {
            return new ResponseEntity<>(activityData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Activity> create(@RequestBody Activity activity) {
        try {
            Activity _activity = activityRepository.save(activity);
            return new ResponseEntity<>(_activity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteActivity(@PathVariable("id") String id) {
        try {
            activityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@RequestBody Activity activity, @PathVariable("id") String id) {
        Optional<Activity> activityData = activityRepository.findById(id);

        if (activityData.isPresent()) {
            Activity _activity = activityData.get();
            _activity.setName(activity.getName());
            _activity.setDescription(activity.getDescription());
            _activity.setCategory(activity.getCategory());
            return new ResponseEntity<>(activityRepository.save(_activity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
