package com.example.take5.controller;

import com.example.take5.model.ActivityLogEntry;
import com.example.take5.repository.ActivityLogRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@RestController
@RequestMapping("/api/activityLog")
public class ActivityLogController {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityLogEntry>> getActivities(@PathVariable("userId") String userId) {
        try {
            List<ActivityLogEntry> activities = activityLogRepository.findByUserId(userId);

            return new ResponseEntity<>(activities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteActivity(@PathVariable("id") String id) {
        activityLogRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ActivityLogEntry> updateActivityLog(@RequestBody ActivityLogEntry activityLog, @PathVariable("id") String id) {
        try {
            if (id.equals("null") || activityLog.getId() == null) {
                activityLog.setId(new ObjectId().toString());
                return new ResponseEntity<>(activityLogRepository.save(activityLog), HttpStatus.CREATED);
            }

            Optional<ActivityLogEntry> activityLogData = activityLogRepository.findById(id);

            if (activityLogData.isPresent()) {
                return new ResponseEntity<>(activityLogRepository.save(activityLog), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
