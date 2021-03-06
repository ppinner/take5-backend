package com.example.take5.controller;

import com.example.take5.model.*;
import com.example.take5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable("id") String id) {
        User userData = userRepository.findDistinctById(id);

        if (userData != null) {
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> findAll() {
        List<User> userData = userRepository.findAll();

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
            String dateWithoutTime = LocalDate.now(ZoneOffset.UTC).toString();
            HashMap<String, Score> scores = new HashMap<>();
            Score start = new Score(0.0, 0.0, 0.0, 0.0, 0.0);
            scores.put(dateWithoutTime, start);
            _user.setScores(scores);
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
            _user.setFocusStart(user.getFocusStart());
            _user.setDob(user.getDob());
            if (user.getPersonality() != null) {
                _user.setPersonality(user.getPersonality());
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

    @PutMapping("/{id}/score")
    public ResponseEntity<User> updateUserScore(@RequestBody ScoreLog scoreLog, @PathVariable("id") String id) {
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Optional<User> userData = userRepository.findById(id);

            String dateWithoutTime = LocalDate.now(ZoneOffset.UTC).toString();

            if (userData.isPresent()) {
                User _user = userData.get();
                HashMap<String, Score> scoreHistory;
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
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
