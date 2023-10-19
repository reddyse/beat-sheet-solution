package com.spotter.beatsheet.service;

import com.spotter.beatsheet.entity.Act;
import com.spotter.beatsheet.entity.Beat;
import com.spotter.beatsheet.model.MarkovModelForAct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateNextActService {

    // This method predicts the next Act based on the provided Beat.
    // It returns a ResponseEntity that may contain the predicted Act or an error message.
    public ResponseEntity<?> predictAct(Beat beat) {
        try {
            // Retrieve the list of Acts from the given Beat.
            List<Act> acts = beat.getActs();

            // Create a list of IDs for the Acts using Java Streams and map them.
            List<Long> actContext = acts.stream()
                    .map(Act::getId).toList();

            // Create an instance of the MarkovModelForAct with the context size based on the number of Acts.
            MarkovModelForAct markovModelForAct = new MarkovModelForAct(actContext.size());

            // Predict the next Act using the Markov model.
            var newPredictedAct = markovModelForAct.predictNextAct(actContext);

            // If no prediction is available, return a "Not Found" response.
            if (newPredictedAct==null) {
                return ResponseEntity.notFound().build();
            }

            // Return a "OK" response containing the predicted Act.
            return ResponseEntity.ok("The next predicted Act from the provided list of Beats is: "+newPredictedAct);
        } catch (Exception e) {
            // If an exception occurs during prediction, return a "Bad Request" response
            // with an error message explaining the failure.
            return ResponseEntity.badRequest().body("Failed to predict next Act from the provided list of Beats: " + e.getMessage());
        }
    }
}
