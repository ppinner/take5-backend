package com.example.take5.controller;

import com.example.take5.model.Activity;
import com.example.take5.model.ActivityLogEntry;
import com.example.take5.model.Category;
import com.example.take5.repository.ActivityLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(locations = "/test-context.xml")
class ActivityLogControllerTest {

    @Mock
    private ActivityLogRepository activityLogRepository;

    @InjectMocks
    private ActivityLogController activityLogController = new ActivityLogController();

    @Test
    void getActivities_validInput_returns200() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});
        ActivityLogEntry entry1 = new ActivityLogEntry(
                activity,
                "21",
                new Date(),
                "Didn't enjoy",
                1,
                "1234"
        );
        List<ActivityLogEntry> activities = new ArrayList<>();
        activities.add(entry1);

        when(activityLogRepository.findByUserId("1234")).thenReturn(activities);

        assert (activityLogController.getActivities("1234").getStatusCode()).equals(HttpStatus.OK);
        assert (activityLogController.getActivities("1234").getBody()).equals(activities);
    }

    @Test
    void getActivities_emptyLog_returns400() {
        List<ActivityLogEntry> activities = new ArrayList<>();

        when(activityLogRepository.findByUserId("1234")).thenReturn(activities);

        ResponseEntity<List<ActivityLogEntry>> responseEntity = activityLogController.getActivities("1234");
        assert (responseEntity.getStatusCode()).equals(HttpStatus.OK);
        assert (responseEntity.getBody().size() == 0);
    }

    @Test
    void getActivities_error_returns500() {
        when(activityLogRepository.findByUserId("1234")).thenThrow(new RuntimeException());

        ResponseEntity<List<ActivityLogEntry>> response = activityLogController.getActivities("1234");

        assert (response.getStatusCode()).equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void deleteActivities_validInput_returns204() {
        ResponseEntity<HttpStatus> response = activityLogController.deleteActivity("21");

        assert (response.getStatusCode()).equals(HttpStatus.NO_CONTENT);
    }

    @Test
    void updateActivity_newActivity_returns200() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});
        ActivityLogEntry entry1 = new ActivityLogEntry(
                activity,
                null,
                new Date(),
                "Didn't enjoy",
                1,
                "1234"
        );

        when(activityLogRepository.save(entry1)).thenReturn(entry1);

        ResponseEntity<ActivityLogEntry> response = activityLogController.updateActivityLog(entry1, "null");
        assert (response.getStatusCode()).equals(HttpStatus.CREATED);
        assert (response.getBody()).equals(entry1);
    }

    void updateActivity_validActivity_returns200() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});
        ActivityLogEntry entry1 = new ActivityLogEntry(
                activity,
                "21",
                new Date(),
                "Didn't enjoy",
                1,
                "1234"
        );
        List<ActivityLogEntry> activities = new ArrayList<>();
        activities.add(entry1);

        ActivityLogEntry updated = new ActivityLogEntry(
                activity,
                "21",
                new Date(),
                "Fun times",
                4,
                "1234"
        );

        when(activityLogRepository.findById("21")).thenReturn(Optional.of(entry1));

        ResponseEntity<ActivityLogEntry> response = activityLogController.updateActivityLog(updated, "21");
        assert (response.getStatusCode()).equals(HttpStatus.OK);
        assert (response.getBody()).equals(updated);
    }

    void updateActivity_activityNotFound_returns404() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});
        ActivityLogEntry updated = new ActivityLogEntry(
                activity,
                "21",
                new Date(),
                "Fun times",
                4,
                "1234"
        );

        when(activityLogRepository.findById("21")).thenReturn(Optional.empty());

        assert (activityLogController.updateActivityLog(updated, "21").getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateActivity_error_returns500() {
        Activity activity = new Activity(
                "1",
                "BBall",
                "Bounce Bounce",
                new Category[]{Category.connection, Category.physicalActivity});
        ActivityLogEntry updated = new ActivityLogEntry(
                activity,
                "21",
                new Date(),
                "Fun times",
                4,
                "1234"
        );
        when(activityLogRepository.findById("21")).thenThrow(new RuntimeException());

        assert (activityLogController.updateActivityLog(updated, "21").getStatusCode()).equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}