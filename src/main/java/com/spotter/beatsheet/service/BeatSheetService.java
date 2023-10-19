package com.spotter.beatsheet.service;

import com.spotter.beatsheet.entity.Act;
import com.spotter.beatsheet.entity.Beat;
import com.spotter.beatsheet.entity.BeatSheet;
import org.springframework.http.ResponseEntity;

public interface BeatSheetService {

    ResponseEntity<String> createBeatSheet(BeatSheet beatSheet);

    ResponseEntity<String> updateBeatSheet(long id, BeatSheet beatSheet);

    ResponseEntity<?> retrieveBeatSheet(long id);

    ResponseEntity<String> deleteBeatSheet(long id);

    ResponseEntity<?> retrieveAllBeatSheets();

    ResponseEntity<?> addBeatToBeatSheet(long id, Beat beat);

    ResponseEntity<?> updateBeatInBeatSheet(long id, long beatId, Beat updatedBeat);

    ResponseEntity<?> deleteBeatInBeatSheet(long id, long beatId);

    ResponseEntity<?> addActToBeat(long id, long beatId, Act updatedAct);

    ResponseEntity<?> updateActInBeat(long id, long beatId, long actId, Act updatedAct);

    ResponseEntity<?> deleteActInBeat(long id, long beatId, long actId);

}
