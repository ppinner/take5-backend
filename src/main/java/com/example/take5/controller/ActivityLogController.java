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

//    @GetMapping("/{userId}")
//    public ResponseEntity<Activity> findRecommendationForUser(@PathVariable("userId") String id) {
//        User userData = userRepository.findDistinctById(id);
//        List<User> users = userRepository.findAll();
//        Activity suggested = new Activity();
//
//        LenskitConfiguration config = new LenskitConfiguration();
//        config.bind(ItemScorer.class)
//                .to(UserUserItemScorer.class);
//
//        config.bind(BaselineScorer.class, ItemScorer.class)
//                .to(UserMeanItemScorer.class);
//        config.bind(UserMeanBaseline.class, ItemScorer.class)
//                .to(ItemMeanRatingItemScorer.class);
//
//        //Not sure this is right
//        config.within(UserVectorNormalizer.class)
//                .bind(VectorNormalizer.class)
//                .to(MeanCenteringVectorNormalizer.class);
//
//        config.set(NeighborhoodSize.class).to(30);
//
//        config.bind(EventDAO.class).to(userRepository.new File("ratings.csv"), ","));
//
//        LenskitRecommender rec = LenskitRecommender.create(config);
//        ItemRecommender irec = rec.getItemRecommender();
//        List<ScoredId> recommendations = irec.recommend(id, 1);
//
//        //Step 1: Compare users to find similar users to current user (personality, dob)
//
//        //Step 2: Of these users, analyse activity logs for most common activities
//        //    - filter by those which are under the user's focus goal
//        //Step 3: Output the recommendation to the user
//
//        if (userData != null) {
//            return new ResponseEntity<>(suggested, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    //TODO - for recommendation for similar activities:
    // map to just get activities from the logs
    // filter only ones that are user focus category
    // reduce to count number of activity occurrences
    // sort descending
    // take most popular

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityLogEntry>> getActivities(@PathVariable("userId") String userId) {
        try {
            List<ActivityLogEntry> activities = activityLogRepository.findByUserId(userId);

            if (activities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
                ActivityLogEntry _activityLog = activityLogData.get();
                return new ResponseEntity<>(activityLogRepository.save(_activityLog), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
