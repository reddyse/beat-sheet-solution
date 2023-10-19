package com.spotter.beatsheet.service;

import com.spotter.beatsheet.entity.Beat;
import com.spotter.beatsheet.entity.BeatSheet;
import com.spotter.beatsheet.model.MarkovModelForBeat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateNextBeatService {

    public ResponseEntity<?> predictBeatSheet(BeatSheet beatSheet){
        try {
            List<Beat> beats = beatSheet.getBeats();
            List<Long> beatContext = beats.stream()
                    .map(Beat::getId).toList();
            MarkovModelForBeat markovModelForBeat = new MarkovModelForBeat(beatContext.size());
            var newPredictedBeat = markovModelForBeat.predictNextBeat(beatContext);
            if (newPredictedBeat==null) throw new Exception();
            return ResponseEntity.ok("Based on the BeatSheet, the next predicted beat is: "+ newPredictedBeat);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Failed to predict the next Beat from this BeatSheet: " + e.getMessage());
        }
    }
}
