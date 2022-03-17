package com.example.take5.controller;

import com.example.take5.model.Activity;
import com.example.take5.model.Category;
import com.example.take5.repository.ActivityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class ActivityControllerTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityController activityController = new ActivityController();


    @Test
    void getActivities_noName_returns200() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});
        Activity activity2 = new Activity(
                "2",
                "Meditation",
                "Stop and Breathe",
                new Category[]{Category.learning, Category.mindfulness});
        Activity activity3 = new Activity(
                "3",
                "Teaching a foreign language",
                "Teach something to change a life",
                new Category[]{Category.connection, Category.giving});
        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        activities.add(activity2);
        activities.add(activity3);

        when(activityRepository.findAll()).thenReturn(activities);

        ResponseEntity<List<Activity>> response = activityController.getActivities(null);
        assert (response.getStatusCode()).equals(HttpStatus.OK);
        assert (response.getBody()).equals(activities);
    }

    @Test
    void getActivities_withTitle_match_returns200() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});
        Activity activity2 = new Activity(
                "2",
                "Meditation",
                "Stop and Breathe",
                new Category[]{Category.learning, Category.mindfulness});
        Activity activity3 = new Activity(
                "3",
                "Teaching a foreign language",
                "Teach something to change a life",
                new Category[]{Category.connection, Category.giving});
        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        activities.add(activity2);
        activities.add(activity3);

        List<Activity> matches = new ArrayList<>();
        matches.add(activity);

        when(activityRepository.findByNameLike("Ball")).thenReturn(matches);

        ResponseEntity<List<Activity>> response = activityController.getActivities("Ball");
        assert (response.getStatusCode()).equals(HttpStatus.OK);
        assert (response.getBody()).equals(matches);
    }

    @Test
    void getActivities_withTitle_noMatch_returns204() {
        when(activityRepository.findByNameLike("Ball")).thenReturn(List.of());

        ResponseEntity<List<Activity>> response = activityController.getActivities("Ball");
        assert (response.getStatusCode()).equals(HttpStatus.NO_CONTENT);
    }

    @Test
    void getActivities_noActivities_returns204() {
        when(activityRepository.findAll()).thenReturn(List.of());

        ResponseEntity<List<Activity>> response = activityController.getActivities(null);
        assert (response.getStatusCode()).equals(HttpStatus.NO_CONTENT);
    }

    @Test
    void getActivities_error_returns500() {
        when(activityRepository.findAll()).thenThrow(new RuntimeException());

        ResponseEntity<List<Activity>> response = activityController.getActivities(null);

        assert (response.getStatusCode()).equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void findOne_validId_returns200() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});

        when(activityRepository.findDistinctById("1")).thenReturn(activity);

        ResponseEntity<Activity> response = activityController.findOne("1");
        assert (response.getStatusCode()).equals(HttpStatus.OK);
        assert (response.getBody()).equals(activity);
    }

    @Test
    void findOne_invalidId_returns404() {
        when(activityRepository.findDistinctById("1")).thenReturn(null);

        ResponseEntity<Activity> response = activityController.findOne("1");
        assert (response.getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }

//    @Test
//    void findByCategory() {
//    }

    @Test
    void create_validRequest_returns200() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});

        when(activityRepository.save(activity)).thenReturn(activity);

        ResponseEntity<Activity> response = activityController.create(activity);
        assert (response.getStatusCode()).equals(HttpStatus.CREATED);
        assert (response.getBody()).equals(activity);
    }

    @Test
    void create_error_returns500() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});

        when(activityRepository.save(activity)).thenThrow(new RuntimeException());

        ResponseEntity<Activity> response = activityController.create(activity);

        assert (response.getStatusCode()).equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void deleteActivities_validInput_returns204() {
        ResponseEntity<HttpStatus> response = activityController.deleteActivity("21");

        assert (response.getStatusCode()).equals(HttpStatus.NO_CONTENT);
    }

    @Test
    void updateActivity_valid_returns200() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});

        Activity updated = new Activity(
                "1",
                "Basketball",
                "Bounce",
                new Category[]{Category.connection, Category.physicalActivity});

        when(activityRepository.findById("1")).thenReturn(Optional.of(activity));
        when(activityRepository.save(Mockito.any(Activity.class))).thenReturn(updated);

        ResponseEntity<Activity> response = activityController.updateActivity(updated, "1");
        assert (response.getStatusCode()).equals(HttpStatus.OK);
        assert (response.getBody().equals(updated));
    }

    @Test
    void updateActivity_doesntExist_returns404() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});

        when(activityRepository.findById("1")).thenReturn(Optional.empty());

        ResponseEntity<Activity> response = activityController.updateActivity(activity, "1");
        assert (response.getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }
}