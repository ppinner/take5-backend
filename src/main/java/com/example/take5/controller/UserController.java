package com.example.take5.controller;

import com.example.take5.model.ActivityLogEntry;
import com.example.take5.model.Personality;
import com.example.take5.model.Score;
import com.example.take5.model.User;
import com.example.take5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable("id") String id) {
        User activityData = userRepository.findDistinctById(id);

        if (activityData != null) {
            return new ResponseEntity<>(activityData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //TODO - ask usman best way to implement this - by user id and compare directly or by personality?
    // Think best to write query myself/do logic self instead of relying on db?

//    @GetMapping("/personalityMatch")
//    public ResponseEntity<List<User>> findSimilarPersonalityUsers(@RequestBody Personality personality) {
//        try {
//            List<User> users = userRepository.findUsersByPersonalityContains(personality);
//
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

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

    @RequestMapping(
            value = "/{id}/activityLog/add",
            method = RequestMethod.PUT,
            produces = "application/json"
    )
    public ResponseEntity<User> addActivityEntry(@RequestBody ActivityLogEntry activityLogEntry, @PathVariable("id") String id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            List<ActivityLogEntry> activityList = _user.getActivityLog();
            if (activityList == null) {
                activityList = new ArrayList<>();
            }
            //TODO - add ID field if not provided
            activityList.add(activityLogEntry);
            _user.setActivityLog(activityList);
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/activityLog/edit")
    public ResponseEntity<User> editActivityEntry(@RequestBody ActivityLogEntry entry, @PathVariable("id") String id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            List<ActivityLogEntry> activityList = _user.getActivityLog();
            List<ActivityLogEntry> filteredActivityList = activityList.stream()
                    .filter(logEntry -> logEntry.getId().equals(entry.getId()))
                    .collect(Collectors.toList());

            if (filteredActivityList.size() == 1) {
                int index = activityList.indexOf(filteredActivityList.get(0));
                activityList.set(index, entry);
                _user.setActivityLog(activityList);
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/activityLog/delete")
    public ResponseEntity<User> deleteActivityEntry(@RequestBody ActivityLogEntry entry, @PathVariable("id") String id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            List<ActivityLogEntry> activityList = _user.getActivityLog();

            List<ActivityLogEntry> filteredActivityList = activityList.stream()
                    .filter(logEntry -> logEntry.getId().equals(entry.getId()))
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

    @GetMapping("/{id}/score")
    public ResponseEntity<Score> getUserScore(@PathVariable("id") String id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            Score _score = userData.get().getScores();
            return new ResponseEntity<>(_score, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/score")
    public ResponseEntity<User> updateUserScore(@RequestBody Score score, @PathVariable("id") String id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setScores(score);
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
