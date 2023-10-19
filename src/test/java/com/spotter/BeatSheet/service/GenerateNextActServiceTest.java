package com.spotter.BeatSheet.service;

import com.spotter.BeatSheet.entity.Act;
import com.spotter.BeatSheet.entity.Beat;
import com.spotter.BeatSheet.model.MarkovModelForAct;
import com.spotter.BeatSheet.service.GenerateNextActService;
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

public class GenerateNextActServiceTest {

    @InjectMocks
    private GenerateNextActService generateNextActService;

    @Mock
    private MarkovModelForAct markovModelForAct;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPredictAct() {
        Beat beat = new Beat();
        Act predictedAct = new Act();
        predictedAct.setDescription("Predicted Act");

        List<Act> acts = new ArrayList<>();
        Act actFirst = new Act();
        actFirst.setId(1L);
        actFirst.setDescription("TEST 1");
        acts.add(actFirst);
        Act actSecond = new Act();
        actSecond.setId(1L);
        actSecond.setDescription("TEST 1");
        acts.add(actSecond);

        beat.setActs(acts);

        // Set up mock behavior for the markovModelForAct (assuming it's mocked)
        Mockito.when(markovModelForAct.predictNextAct(Mockito.anyList())).thenReturn(String.valueOf(predictedAct));

        ResponseEntity<?> response = generateNextActService.predictAct(beat);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    @Ignore
    public void testPredictActException() {
        Beat beat = new Beat();
        List<Act> acts = new ArrayList<>();
        beat.setActs(acts);

        // Set up mock behavior for the markovModelForAct to throw an exception
        Mockito.when(markovModelForAct.predictNextAct(Mockito.anyList())).thenThrow(new Exception("Prediction failed"));
        ResponseEntity<?> response = generateNextActService.predictAct(beat);
        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

}
