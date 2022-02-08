package com.example.take5.controller;

import com.example.take5.model.*;
import com.example.take5.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable("id") String id) {
        User userData = userRepository.findDistinctById(id);

        if (userData != null) {
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            User _user = userRepository.save(user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserDetails(@RequestBody User user, @PathVariable("id") String id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setName(user.getName());
            _user.setFocus(user.getFocus());
            _user.setDob(user.getDob());
            if (user.getPersonality() != null) {
                _user.setPersonality(user.getPersonality());
            }
            if (user.getActivityLog() != null) {
                _user.setActivityLog(user.getActivityLog());
            }
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/personality")
    public ResponseEntity<Personality> getUserPersonality(@PathVariable("id") String id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            Personality _personality = userData.get().getPersonality();
            return new ResponseEntity<>(_personality, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/personality")
    public ResponseEntity<User> updateUserPersonality(@RequestBody Personality personality, @PathVariable("id") String id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setPersonality(personality);
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{userId}/activityLog/edit")
    public ResponseEntity<User> editActivityEntry(@RequestBody ActivityLogEntry activityLogEntry, @PathVariable("userId") String userId) {
        Optional<User> userData = userRepository.findById(userId);

        if (userData.isPresent()) {
            User _user = userData.get();
            List<ActivityLogEntry> activityList = _user.getActivityLog();
            List<ActivityLogEntry> filteredActivityList = activityList.stream()
                    .filter(logEntry -> logEntry.getId().equals(activityLogEntry.getId()))
                    .collect(Collectors.toList());

            if (activityLogEntry.getId() == null) {
                activityLogEntry.setId(new ObjectId().toString());
            }

            if (filteredActivityList.size() == 1) {
                int index = activityList.indexOf(filteredActivityList.get(0));
                activityList.set(index, activityLogEntry);
                _user.setActivityLog(activityList);
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            } else {
                activityList.add(activityLogEntry);
                _user.setActivityLog(activityList);
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}/activityLog/delete/{logId}")
    public ResponseEntity<User> deleteActivityEntry(@PathVariable("userId") String userId, @PathVariable("logId") String logId) {
        Optional<User> userData = userRepository.findById(userId);

        if (userData.isPresent()) {
            User _user = userData.get();
            List<ActivityLogEntry> activityList = _user.getActivityLog();

            List<ActivityLogEntry> filteredActivityList = activityList.stream()
                    .filter(logEntry -> logEntry.getId().equals(logId))
                    .collect(Collectors.toList());

            if (filteredActivityList.size() == 1) {
                int index = activityList.indexOf(filteredActivityList.get(0));
                activityList.remove(index);
                _user.setActivityLog(activityList);
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/{id}/score")
//    public ResponseEntity<Score> getUserScore(@PathVariable("id") String id) {
//        Optional<User> userData = userRepository.findById(id);
//
//        if (userData.isPresent()) {
//            Dictionary<KK> _score = userData.get().getScores();
//            return new ResponseEntity<>(_score, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/{id}/score")
    public ResponseEntity<User> updateUserScore(@RequestBody ScoreLog scoreLog, @PathVariable("id") String id) {
        Optional<User> userData = userRepository.findById(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date dateWithoutTime = sdf.parse(sdf.format(scoreLog.getDate()));
            if (userData.isPresent()) {
                User _user = userData.get();
                HashMap<Date, Score> scoreHistory;
                if(_user.getScores() == null){
                    scoreHistory = new HashMap<>();
                } else {
                    scoreHistory = _user.getScores();
                }
                scoreHistory.put(dateWithoutTime, scoreLog.getScore());
                _user.setScores(scoreHistory);
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(ParseException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
