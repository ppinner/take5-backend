package com.example.take5.controller;

import com.example.take5.model.*;
import com.example.take5.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static org.mockito.Mockito.when;

@ContextConfiguration(locations = "/test-context.xml")
@SpringBootTest
class UserControllerTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController = new UserController();

    @Test
    void findOne_validId_returns200() {
        HashMap<Date, Score> scores = new HashMap<Date, Score>();
        Score s = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        scores.put(new Date(), s);
        Personality p = new Personality(12, 21, 22, 9, 34);
        User user = new User("id", new Date(), "name", p, Category.mindfulness, scores, new Date());

        when(userRepository.findDistinctById("id")).thenReturn(user);

        assert (userController.findOne("id").getStatusCode()).equals(HttpStatus.OK);
        assert (userController.findOne("id").getBody()).equals(user);
    }

    @Test
    void findOne_invalidId_returns404() {
        when(userRepository.findDistinctById("id")).thenReturn(null);

        assert (userController.findOne("id").getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }

    @Test
    void findAll_validId_returns200() {
        HashMap<Date, Score> scores = new HashMap<Date, Score>();
        Score s = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        scores.put(new Date(), s);
        Personality p = new Personality(12, 21, 22, 9, 34);
        User user = new User("id", new Date(), "name", p, Category.mindfulness, scores, new Date());
        User user2 = new User("id2", new Date(), "name2", p, Category.learning, scores, new Date());

        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        assert (userController.findAll().getStatusCode()).equals(HttpStatus.OK);
        assert (userController.findAll().getBody()).equals(users);
    }

    @Test
    void findAll_invalidId_returns404() {
        when(userRepository.findAll()).thenReturn(null);

        assert (userController.findAll().getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }

    @Test
    void create_valid_returns201() {
        HashMap<Date, Score> scores = new HashMap<Date, Score>();
        Score s = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        scores.put(new Date(), s);
        Personality p = new Personality(12, 21, 22, 9, 34);
        User user = new User("id", new Date(), "name", p, Category.mindfulness, scores, new Date());

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        assert (userController.create(user).getStatusCode()).equals(HttpStatus.CREATED);
        assert (userController.create(user).getBody()).equals(user);
    }

    @Test
    void create_error_returns500() {
        HashMap<Date, Score> scores = new HashMap<Date, Score>();
        Score s = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        scores.put(new Date(), s);
        Personality p = new Personality(12, 21, 22, 9, 34);
        User user = new User("id", new Date(), "name", p, Category.mindfulness, scores, new Date());

        when(userRepository.save(Mockito.any(User.class))).thenThrow(new RuntimeException());

        assert (userController.create(user).getStatusCode()).equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void deleteUser_validInput_returns204() {
        ResponseEntity<HttpStatus> response = userController.deleteUser("21");

        assert (response.getStatusCode()).equals(HttpStatus.NO_CONTENT);
    }

    @Test
    void updateUserDetails_valid_returns200() {
        HashMap<Date, Score> scores = new HashMap<Date, Score>();
        Score s = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        scores.put(new Date(), s);
        Personality p = new Personality(12, 21, 22, 9, 34);
        User user = new User("id", new Date(), "name", p, Category.mindfulness, scores, new Date());

        Personality p2 = new Personality(10, 11, 12, 24, 34);
        User user2 = new User("id", new Date(), "Poppy", p2, Category.learning, scores, new Date());

        when(userRepository.findById("id")).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user2);

        ResponseEntity<User> response = userController.updateUserDetails(user2, "id");
        assert (response.getStatusCode()).equals(HttpStatus.OK);
        assert (response.getBody().equals(user2));
    }

    @Test
    void updateUserDetails_doesntExist_returns404() {
        HashMap<Date, Score> scores = new HashMap<Date, Score>();
        Score s = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        scores.put(new Date(), s);
        Personality p = new Personality(12, 21, 22, 9, 34);
        User user = new User("id", new Date(), "name", p, Category.mindfulness, scores, new Date());

        when(userRepository.findById("id")).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.updateUserDetails(user, "id");
        assert (response.getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }

    @Test
    void getPersonality_validId_returns200() {
        HashMap<Date, Score> scores = new HashMap<Date, Score>();
        Score s = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        scores.put(new Date(), s);
        Personality p = new Personality(12, 21, 22, 9, 34);
        User user = new User("id", new Date(), "name", p, Category.mindfulness, scores, new Date());

        when(userRepository.findById("id")).thenReturn(Optional.of(user));

        ResponseEntity<Personality> response = userController.getUserPersonality("id");
        assert (response.getStatusCode()).equals(HttpStatus.OK);
        assert (response.getBody()).equals(p);
    }

    @Test
    void getPersonality_invalidId_returns404() {
        when(userRepository.findById("id")).thenReturn(Optional.empty());

        ResponseEntity<Personality> response = userController.getUserPersonality("id");
        assert (response.getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }

    @Test
    void updatePersonality_validId_returns200() {
        HashMap<Date, Score> scores = new HashMap<Date, Score>();
        Score s = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        scores.put(new Date(), s);
        Personality p = new Personality(12, 21, 22, 9, 34);
        User user = new User("id", new Date(), "name", p, Category.mindfulness, scores, new Date());

        Personality p2 = new Personality(2, 11, 25, 29, 14);
        User user2 = new User("id", new Date(), "name", p2, Category.mindfulness, scores, new Date());

        when(userRepository.findById("id")).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user2);

        ResponseEntity<User> response = userController.updateUserPersonality(p, "id");
        assert (response.getStatusCode()).equals(HttpStatus.OK);
        assert (response.getBody()).equals(user2);
    }

    @Test
    void updatePersonality_invalidId_returns404() {
        Personality p = new Personality(10, 11, 12, 24, 34);

        when(userRepository.findById("id")).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.updateUserPersonality(p, "id");
        assert (response.getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateScore_validId_returns200() {
        Date today = new Date();
        HashMap<Date, Score> scores = new HashMap<Date, Score>();
        Score s = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        scores.put(today, s);
        Personality p = new Personality(12, 21, 22, 9, 34);
        User user = new User("id", new Date(), "name", p, Category.mindfulness, scores, new Date());

        HashMap<Date, Score> scores2 = new HashMap<Date, Score>();
        Score s2 = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        scores.put(today, s2);
        User user2 = new User("id", new Date(), "name", p, Category.mindfulness, scores2, new Date());

        ScoreLog sl = new ScoreLog(s2, today);

        when(userRepository.findById("id")).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user2);

        ResponseEntity<User> response = userController.updateUserScore(sl, "id");
        assert (response.getStatusCode()).equals(HttpStatus.OK);
        assert (response.getBody()).equals(user2);
    }

    @Test
    void updateScore_invalidId_returns404() {
        Score s2 = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        ScoreLog sl = new ScoreLog(s2, new Date());

        when(userRepository.findById("id")).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.updateUserScore(sl, "id");
        assert (response.getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }

//    TODO - Ability to Mock the ParseException orrrr restructure code to inject date formatter
//    @Test
//    void updateScore_invalidDate_returns406() {
//        Date today = new Date();
//        HashMap<Date, Score> scores = new HashMap<Date, Score>();
//        Score s = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
//        scores.put(today, s);
//        Personality p = new Personality(12, 21, 22, 9, 34);
//        User user = new User("id", today, "name", p, Category.mindfulness, scores, new Date());
//
//        Score s2 = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
//        ScoreLog sl = new ScoreLog(s2, new Date());
//
//        when(userRepository.findById("id")).thenReturn(Optional.of(user));
//        when(sdf.format(Mockito.any(Date.class))).thenReturn("XYZ");
//
//        ResponseEntity<User> response = userController.updateUserScore(sl, "id");
//        assert(response.getStatusCode()).equals(HttpStatus.NOT_ACCEPTABLE);
//    }

    @Test
    void updateScore_error_returns500() {
        Score s2 = new Score(0.1, 0.2, 0.3, 0.2, 0.4);
        ScoreLog sl = new ScoreLog(s2, new Date());

        when(userRepository.findById("id")).thenThrow(new RuntimeException());

        ResponseEntity<User> response = userController.updateUserScore(sl, "id");
        assert (response.getStatusCode()).equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}