package com.example.take5.controller;

import com.example.take5.model.Activity;
import com.example.take5.model.RecommendationRating;
import com.example.take5.model.User;
import com.example.take5.repository.ActivityLogRepository;
import com.example.take5.repository.ActivityRepository;
import com.example.take5.repository.UserRepository;
import org.bson.json.JsonObject;
import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.ItemScorer;
import org.grouplens.lenskit.baseline.BaselineScorer;
import org.grouplens.lenskit.baseline.ItemMeanRatingItemScorer;
import org.grouplens.lenskit.baseline.UserMeanBaseline;
import org.grouplens.lenskit.baseline.UserMeanItemScorer;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.core.LenskitRecommender;
import org.grouplens.lenskit.data.dao.EventDAO;
import org.grouplens.lenskit.knn.NeighborhoodSize;
import org.grouplens.lenskit.knn.user.UserUserItemScorer;
import org.grouplens.lenskit.scored.ScoredId;
import org.grouplens.lenskit.transform.normalize.MeanCenteringVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.UserVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.VectorNormalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RestController
@RequestMapping("/api/recommender")
public class RecommendationController {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    private LenskitRecommender recommender;

    @GetMapping("/setupEngine")
    public ResponseEntity<HttpStatus> setupRecommender() {
        List<User> users = userRepository.findAll();
        Stream<RecommendationRating> ratings = activityLogRepository.findAll().stream().map(log ->
                new RecommendationRating(log.getUserId(), log.getActivity().getId(), log.getRating())
        );

        //configure Recommender
        LenskitConfiguration config = new LenskitConfiguration();
        config.bind(ItemScorer.class)
                .to(UserUserItemScorer.class);

        config.bind(BaselineScorer.class, ItemScorer.class)
                .to(UserMeanItemScorer.class);
        config.bind(UserMeanBaseline.class, ItemScorer.class)
                .to(ItemMeanRatingItemScorer.class);

        //Not sure this is right
        config.within(UserVectorNormalizer.class)
                .bind(VectorNormalizer.class)
                .to(MeanCenteringVectorNormalizer.class);

        config.set(NeighborhoodSize.class).to(30);

//        config.bind(EventDAO.class).to(ratings);
//
//        recommender = LenskitRecommender.create(config);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Activity> findRecommendationForUser(@PathVariable("userId") String id) {
        //Step 1: Compare users to find similar users to current user (personality, dob)
        User userData = userRepository.findDistinctById(id);
//
//        ItemRecommender irec = recommender.getItemRecommender();
//        //TODO - needs an integer ID not string and parseLong of objID too large
//        List<ScoredId>recommendations = irec.recommend(Long.parseLong(id, 36) ,1);

        //TODO - set this Id from recommendations above
        Activity suggested = activityRepository.findDistinctById("61cb458c4b2140eb65d5c1ce");

        if (userData != null && suggested != null) {
            return new ResponseEntity<>(suggested, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //TODO - for recommendation for similar activities:
    // map to just get activities from the logs
    // filter only ones that are user focus category
    // reduce to count number of activity occurrences
    // sort descending
    // take most popular

}
