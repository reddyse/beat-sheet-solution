package com.spotter.BeatSheet.service;

import com.spotter.BeatSheet.entity.Act;
import com.spotter.BeatSheet.entity.Beat;
import com.spotter.BeatSheet.entity.BeatSheet;
import com.spotter.BeatSheet.repository.BeatSheetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
@SpringBootTest
class BeatSheetServiceTest {

    @InjectMocks
    private BeatSheetService beatSheetService;

    @InjectMocks
    private GenerateNextBeatService generateNextBeatService;

    @InjectMocks
    private GenerateNextActService generateNextActService;

    @Mock
    private BeatSheetRepository beatSheetRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBeatSheet() {
        BeatSheet beatSheet = new BeatSheet();
        beatSheet.setTitle("Test Title");

        Beat beat = new Beat();
        beat.setDescription("Test Desc");
        beat.setTimestamp(new Timestamp(2023567894));
        List<Beat> beats = new ArrayList<>();
        beats.add(beat);
        beatSheet.setBeats(beats);

        Mockito.when(beatSheetRepository.save(beatSheet)).thenReturn(beatSheet);
        ResponseEntity<String> result = beatSheetService.createBeatSheet(beatSheet);

        assertEquals("Success", result.getBody());

    }

    @Test
    public void testRetrieveBeatSheet() {
        long id = 1L;
        BeatSheet beatSheet = new BeatSheet();
        Mockito.when(beatSheetRepository.findById(id)).thenReturn(Optional.of(beatSheet));
        ResponseEntity<?> result = beatSheetService.retrieveBeatSheet(id);
        assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(200));
    }

    @Test
    public void testRetrieveAllBeatSheetsWithOneSheet() {
        long id = 1L;
        BeatSheet beatSheet = new BeatSheet();
        beatSheet.setId(id);

        // Set up mock behavior for the repository
        Mockito.when(beatSheetRepository.findById(id)).thenReturn(Optional.of(beatSheet));

        ResponseEntity<?> response = beatSheetService.retrieveBeatSheet(id);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteBeatSheet() {
        long id = 1L;
        BeatSheet beatSheet = new BeatSheet();
        beatSheet.setId(id);

        // Set up mock behavior for the repository
        Mockito.when(beatSheetRepository.findById(id)).thenReturn(Optional.of(beatSheet));

        ResponseEntity<String> response = beatSheetService.deleteBeatSheet(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Success", response.getBody());
    }

    @Test
    public void testUpdateBeatSheet() {
        long id = 1L;
        BeatSheet existingBeatSheet = new BeatSheet();
        existingBeatSheet.setId(id);

        BeatSheet updatedBeatSheet = new BeatSheet();
        updatedBeatSheet.setId(id);

        // Set up mock behavior for the repository
        Mockito.when(beatSheetRepository.findById(id)).thenReturn(Optional.of(existingBeatSheet));
        Mockito.when(beatSheetRepository.save(existingBeatSheet)).thenReturn(updatedBeatSheet);

        ResponseEntity<String> response = beatSheetService.updateBeatSheet(id, updatedBeatSheet);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Success", response.getBody());
    }

    @Test
    public void testRetrieveAllBeatSheets() {
        List<BeatSheet> beatSheets = new ArrayList<>();
        beatSheets.add(new BeatSheet());
        beatSheets.add(new BeatSheet());

        // Set up mock behavior for the repository
        Mockito.when(beatSheetRepository.findAll()).thenReturn(beatSheets);

        ResponseEntity<?> response = beatSheetService.retrieveAllBeatSheets();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void testAddBeatToBeatSheet() {
        long id = 1L;
        BeatSheet existingBeatSheet = new BeatSheet();
        existingBeatSheet.setId(id);

        List<Beat> beats = new ArrayList<>();
        Beat beat = new Beat();
        beat.setId(id);
        beat.setDescription("testBeat");
        beat.setTimestamp(new Timestamp(123456788));
        beats.add(beat);
        existingBeatSheet.setBeats(beats);

        // Set up mock behavior for the repository
        Mockito.when(beatSheetRepository.findById(id)).thenReturn(Optional.of(existingBeatSheet));
        Mockito.when(beatSheetRepository.save(existingBeatSheet)).thenReturn(existingBeatSheet);

        ResponseEntity<?> response = beatSheetService.addBeatToBeatSheet(id, beat);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateBeatInBeatSheet() {
        long beatSheetId = 1L;
        long beatId = 2L;
        BeatSheet existingBeatSheet = new BeatSheet();
        existingBeatSheet.setId(beatSheetId);

        List<Beat> beats = new ArrayList<>();
        Beat beat = new Beat();
        beat.setId(beatId);
        beat.setDescription("Old Description");
        existingBeatSheet.setBeats(beats);

        existingBeatSheet.getBeats().add(beat);

        // Set up mock behavior for the repository
        Mockito.when(beatSheetRepository.findById(beatSheetId)).thenReturn(Optional.of(existingBeatSheet));
        Mockito.when(beatSheetRepository.save(existingBeatSheet)).thenReturn(existingBeatSheet);

        Beat updatedBeat = existingBeatSheet.getBeats().get(0);
        updatedBeat.setId(beatId);
        updatedBeat.setDescription("New Description");

        ResponseEntity<?> response = beatSheetService.updateBeatInBeatSheet(beatSheetId, beatId, updatedBeat);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("New Description", existingBeatSheet.getBeats().get(0).getDescription());
    }

    @Test
    public void testDeleteBeatInBeatSheet() {
        long beatSheetId = 1L;
        long beatId = 2L;
        BeatSheet existingBeatSheet = new BeatSheet();
        existingBeatSheet.setId(beatSheetId);

        List<Beat> beats = new ArrayList<>();
        Beat beat = new Beat();
        beat.setId(beatId);
        existingBeatSheet.setBeats(beats);

        existingBeatSheet.getBeats().add(beat);

        // Set up mock behavior for the repository
        Mockito.when(beatSheetRepository.findById(beatSheetId)).thenReturn(Optional.of(existingBeatSheet));
        Mockito.when(beatSheetRepository.save(existingBeatSheet)).thenReturn(existingBeatSheet);

        ResponseEntity<?> response = beatSheetService.deleteBeatInBeatSheet(beatSheetId, beatId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(0, existingBeatSheet.getBeats().size());
    }

    @Test
    public void testAddActToBeat() {
        long beatSheetId = 1L;
        long beatId = 2L;
        long actId=2L;
        BeatSheet existingBeatSheet = new BeatSheet();
        existingBeatSheet.setId(beatSheetId);

        List<Beat> beats = new ArrayList<>();
        Beat beat = new Beat();
        beat.setId(beatId);

        List<Act> acts = new ArrayList<>();
        Act act = new Act();
        act.setId(actId);
        acts.add(act);
        beat.setActs(acts);
        beats.add(beat);
        existingBeatSheet.setBeats(beats);

        existingBeatSheet.getBeats().add(beat);

        // Set up mock behavior for the repository
        Mockito.when(beatSheetRepository.findById(beatSheetId)).thenReturn(Optional.of(existingBeatSheet));
        Mockito.when(beatSheetRepository.save(existingBeatSheet)).thenReturn(existingBeatSheet);

        ResponseEntity<?> response = beatSheetService.addActToBeat(beatSheetId, beatId, act);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateActInBeat() {
        long beatSheetId = 1L;
        long beatId = 2L;
        long actId = 3L;
        BeatSheet existingBeatSheet = new BeatSheet();
        existingBeatSheet.setId(beatSheetId);

        List<Beat> beats = new ArrayList<>();
        Beat beat = new Beat();
        beat.setId(beatId);

        List<Act> acts = new ArrayList<>();
        Act act = new Act();
        act.setId(actId);
        act.setDescription("Old Description");

        beat.setActs(acts);
        beats.add(beat);
        existingBeatSheet.setBeats(beats);



        // Set up mock behavior for the repository
        Mockito.when(beatSheetRepository.findById(beatSheetId)).thenReturn(Optional.of(existingBeatSheet));
        Mockito.when(beatSheetRepository.save(existingBeatSheet)).thenReturn(existingBeatSheet);

        Act updatedAct = new Act();
        updatedAct.setId(actId);
        updatedAct.setDescription("New Description");
        existingBeatSheet.getBeats().get(0).getActs().add(act);
        ResponseEntity<?> response = beatSheetService.updateActInBeat(beatSheetId, beatId, actId, updatedAct);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("New Description", existingBeatSheet.getBeats().get(0).getActs().get(0).getDescription());
    }

    @Test
    public void testDeleteActInBeat() {
        long beatSheetId = 1L;
        long beatId = 2L;
        long actId = 3L;
        BeatSheet existingBeatSheet = new BeatSheet();
        existingBeatSheet.setId(beatSheetId);

        List<Beat> beats = new ArrayList<>();
        Beat beat = new Beat();
        beat.setId(beatId);

        List<Act> acts = new ArrayList<>();
        Act act = new Act();
        act.setId(actId);
        beat.setActs(acts);
        beats.add(beat);
        existingBeatSheet.setBeats(beats);

        // Set up mock behavior for the repository
        Mockito.when(beatSheetRepository.findById(beatSheetId)).thenReturn(Optional.of(existingBeatSheet));
        Mockito.when(beatSheetRepository.save(existingBeatSheet)).thenReturn(existingBeatSheet);

        ResponseEntity<?> response = beatSheetService.deleteActInBeat(beatSheetId, beatId, actId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNull(existingBeatSheet.getBeats().get(0).getActs().stream().filter(a -> a.getId() == actId).findFirst().orElse(null));
    }

}