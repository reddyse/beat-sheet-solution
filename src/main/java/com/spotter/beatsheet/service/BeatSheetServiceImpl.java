package com.spotter.beatsheet.service;

import com.spotter.beatsheet.entity.Act;
import com.spotter.beatsheet.entity.Beat;
import com.spotter.beatsheet.entity.BeatSheet;
import com.spotter.beatsheet.repository.BeatSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeatSheetServiceImpl implements BeatSheetService {

    @Autowired
    private BeatSheetRepository beatSheetRepository;

    public ResponseEntity<String> createBeatSheet(BeatSheet beatSheet){
        try {
            beatSheetRepository.save(beatSheet);
            return ResponseEntity.ok("Success");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Failed to create BeatSheet: " + e.getMessage());
        }
    }

    public ResponseEntity<String> updateBeatSheet(long id, BeatSheet beatSheet){
        try {
            BeatSheet existingBeatSheet = beatSheetRepository.findById(id).get();
            existingBeatSheet.setTitle(beatSheet.getTitle());
            existingBeatSheet.setBeats(beatSheet.getBeats());
            beatSheetRepository.save(existingBeatSheet);
            return ResponseEntity.ok("Success");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Failed to update BeatSheet: " + e.getMessage());
        }
    }

    public ResponseEntity<?> retrieveBeatSheet(long id) {
        try {
            BeatSheet beatSheet=beatSheetRepository.findById(id).get();
            return ResponseEntity.ok(beatSheet);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to retrieve BeatSheet: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteBeatSheet(long id) {
        try {
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);
            if(optionalExistingBeatSheet.isPresent()){
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                beatSheetRepository.delete(existingBeatSheet);
                return ResponseEntity.ok("Success");
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to delete BeatSheet: " + e.getMessage());
        }
    }

    public ResponseEntity<?> retrieveAllBeatSheets() {
        try {
            List<BeatSheet> beatSheets = beatSheetRepository.findAll();
            return ResponseEntity.ok(beatSheets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve BeatSheets: " + e.getMessage());
        }
    }

    public ResponseEntity<?> addBeatToBeatSheet(long id, Beat beat) {
        try {
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();
                beats.add(beat);
                BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);
                return ResponseEntity.ok(updatedBeatSheet);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add Beat to BeatSheet: " + e.getMessage());
        }
    }

    public ResponseEntity<?> updateBeatInBeatSheet(long id, long beatId, Beat updatedBeat) {
        try {
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();
                Beat oldBeat = beats.stream()
                        .filter(beat -> beatId==beat.getId())
                        .findAny()
                        .orElse(null);

                assert oldBeat != null;
                oldBeat.setDescription(updatedBeat.getDescription());
                oldBeat.setTimestamp(updatedBeat.getTimestamp());
                oldBeat.setActs(updatedBeat.getActs());
                BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);
                return ResponseEntity.ok(updatedBeatSheet);

            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update Beat in BeatSheet: " + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteBeatInBeatSheet(long id, long beatId) {
        try {
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();
                Beat oldBeat = beats.stream()
                        .filter(beat -> beatId==beat.getId())
                        .findAny()
                        .orElse(null);

                beats.remove(oldBeat);
                BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);
                return ResponseEntity.ok(updatedBeatSheet);

            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete Beat in BeatSheet: " + e.getMessage());
        }
    }

    public ResponseEntity<?> addActToBeat(long id, long beatId, Act updatedAct) {
        try {
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();
                Beat oldBeat = beats.stream()
                        .filter(beat -> beatId==beat.getId())
                        .findAny()
                        .orElse(null);

                assert oldBeat != null;
                List<Act> acts = oldBeat.getActs();
                acts.add(updatedAct);
                BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);
                return ResponseEntity.ok(updatedBeatSheet);

            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add Act to Beat: " + e.getMessage());
        }
    }

    public ResponseEntity<?> updateActInBeat(long id, long beatId, long actId, Act updatedAct) {
        try {
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();
                Beat oldBeat = beats.stream()
                        .filter(beat -> beatId==beat.getId())
                        .findAny()
                        .orElse(null);


                List<Act> acts = null;
                if (oldBeat != null) {
                    acts = oldBeat.getActs();
                }

                Act oldAct = null;
                if (acts != null) {
                    oldAct = acts.stream()
                                    .filter(act -> actId==act.getId())
                                    .findAny()
                                    .orElse(null);
                }
                assert oldAct != null;
                oldAct.setDescription(updatedAct.getDescription());
                oldAct.setTimestamp(updatedAct.getTimestamp());
                oldAct.setDuration(updatedAct.getDuration());
                oldAct.setCameraAngle(updatedAct.getCameraAngle());
                BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);
                return ResponseEntity.ok(updatedBeatSheet);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update Act in Beat: " + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteActInBeat(long id, long beatId, long actId) {
        try {
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();
                Beat oldBeat = beats.stream()
                        .filter(beat -> beatId==beat.getId())
                        .findAny()
                        .orElse(null);

                assert oldBeat != null;
                List<Act> acts = oldBeat.getActs();
                Act oldAct = acts.stream()
                        .filter(act -> actId==act.getId())
                        .findAny()
                        .orElse(null);

                acts.remove(oldAct);
                BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);
                return ResponseEntity.ok(updatedBeatSheet);

            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete Act in Beat: " + e.getMessage());
        }
    }
}
