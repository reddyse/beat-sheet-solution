package com.spotter.beatsheet.service;

import com.spotter.beatsheet.entity.Act;
import com.spotter.beatsheet.entity.Beat;
import com.spotter.beatsheet.model.MarkovModelForAct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateNextActService {
    public ResponseEntity<?> predictAct(Beat beat) {
        try {
            List<Act> acts = beat.getActs();
            List<Long> actContext = acts.stream()
                    .map(Act::getId).toList();
            MarkovModelForAct markovModelForAct = new MarkovModelForAct(actContext.size());
            var newPredictedAct = markovModelForAct.predictNextAct(actContext);
            if (newPredictedAct==null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(newPredictedAct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to predict next Act from the provided list of Beats: " + e.getMessage());
        }
    }
}
