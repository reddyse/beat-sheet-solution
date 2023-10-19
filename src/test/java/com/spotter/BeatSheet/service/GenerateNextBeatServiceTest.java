package com.spotter.BeatSheet.service;

import com.spotter.BeatSheet.entity.Act;
import com.spotter.BeatSheet.entity.Beat;
import com.spotter.BeatSheet.entity.BeatSheet;
import com.spotter.BeatSheet.model.MarkovModelForAct;
import com.spotter.BeatSheet.model.MarkovModelForBeat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GenerateNextBeatServiceTest {

    @InjectMocks
    private GenerateNextBeatService generateNextBeatService;

    @Mock
    private MarkovModelForBeat markovModelForBeat;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPredictBeat() {
        BeatSheet beatSheet = new BeatSheet();
        Beat predictedBeat = new Beat();
        predictedBeat.setDescription("Predicted Beat");

        List<Beat> beats = new ArrayList<>();
        Beat beatFirst = new Beat();
        beatFirst.setId(1L);
        beatFirst.setDescription("TEST 1");
        beats.add(beatFirst);
        Beat beatSecond = new Beat();
        beatSecond.setId(1L);
        beatSecond.setDescription("TEST 1");
        beats.add(beatSecond);

        beatSheet.setBeats(beats);

        // Set up mock behavior for the markovModelForAct (assuming it's mocked)
        Mockito.when(markovModelForBeat.predictNextBeat(Mockito.anyList())).thenReturn(String.valueOf(predictedBeat));

        ResponseEntity<?> response = generateNextBeatService.predictBeatSheet(beatSheet);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    @Ignore
    public void testPredictActException() {
        BeatSheet beatSheet = new BeatSheet();
        Beat predictedBeat = new Beat();
        predictedBeat.setDescription("Predicted Beat");

        List<Beat> beats = new ArrayList<>();
        Beat beatFirst = new Beat();
        beatFirst.setId(1L);
        beatFirst.setDescription("TEST 1");
        beats.add(beatFirst);
        Beat beatSecond = new Beat();
        beatSecond.setId(1L);
        beatSecond.setDescription("TEST 1");
        beats.add(beatSecond);
        beatSheet.setBeats(beats);

        // Set up mock behavior for the markovModelForAct to throw an exception
        Mockito.when(markovModelForBeat.predictNextBeat(Mockito.anyList())).thenThrow(new Exception("Prediction failed"));
        ResponseEntity<?> response = generateNextBeatService.predictBeatSheet(beatSheet);
        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

}
