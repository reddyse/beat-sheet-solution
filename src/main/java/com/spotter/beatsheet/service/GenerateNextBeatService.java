package com.spotter.beatsheet.service;

import com.spotter.beatsheet.entity.Beat;
import com.spotter.beatsheet.entity.BeatSheet;
import com.spotter.beatsheet.model.MarkovModelForBeat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateNextBeatService {

    // This method predicts the next Beat based on the provided BeatSheet.
    // It returns a ResponseEntity that may contain the predicted Beat or an error message.
    public ResponseEntity<?> predictBeatSheet(BeatSheet beatSheet){
        try {
            // Retrieve the list of Beats from the given BeatSheet.
            List<Beat> beats = beatSheet.getBeats();

            // Create a list of IDs for the Beats using Java Streams and map them.
            List<Long> beatContext = beats.stream()
                    .map(Beat::getId).toList();

            // Create an instance of the MarkovModelForBeat with the context size based on the number of Beats.
            MarkovModelForBeat markovModelForBeat = new MarkovModelForBeat(beatContext.size());

            // Predict the next Beat using the Markov model.
            var newPredictedBeat = markovModelForBeat.predictNextBeat(beatContext);

            // If no prediction is available, throw an exception.
            if (newPredictedBeat==null) throw new Exception();

            // Return a "OK" response containing the predicted Beat with an informational message.
            return ResponseEntity.ok("Based on the BeatSheet, the next predicted beat is: "+ newPredictedBeat);
        }catch(Exception e){
            // If an exception occurs during prediction, return a "Bad Request" response
            // with an error message explaining the failure.
            return ResponseEntity.badRequest().body("Failed to predict the next Beat from this BeatSheet: " + e.getMessage());
        }
    }
}
