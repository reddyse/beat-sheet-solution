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

    /**
     * Create a new BeatSheet.
     *
     * @param beatSheet The BeatSheet object to be created.
     * @return ResponseEntity with a success message if the creation is successful, or an error message if it fails.
     */
    public ResponseEntity<String> createBeatSheet(BeatSheet beatSheet){
        try {
            // Attempt to save the provided BeatSheet
            beatSheetRepository.save(beatSheet);

            // Return a success response
            return ResponseEntity.ok("Success");
        }catch(Exception e){
            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to create BeatSheet: " + e.getMessage());
        }
    }

    /**
     * Update an existing BeatSheet.
     *
     * @param id        The ID of the BeatSheet to update.
     * @param beatSheet The updated BeatSheet object.
     * @return ResponseEntity with a success message if the update is successful, or an error message if it fails.
     */
    public ResponseEntity<String> updateBeatSheet(long id, BeatSheet beatSheet){
        try {
            // Find the existing BeatSheet by its ID
            BeatSheet existingBeatSheet = beatSheetRepository.findById(id).get();

            // Update the existing BeatSheet properties
            existingBeatSheet.setTitle(beatSheet.getTitle());
            existingBeatSheet.setBeats(beatSheet.getBeats());

            // Update the existing BeatSheet properties
            beatSheetRepository.save(existingBeatSheet);
            return ResponseEntity.ok("Success");
        }catch(Exception e){
            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to update BeatSheet: " + e.getMessage());
        }
    }

    /**
     * Retrieve a BeatSheet by its ID.
     *
     * @param id The ID of the BeatSheet to retrieve.
     * @return ResponseEntity with the retrieved BeatSheet if successful, or an error message if it fails.
     */
    public ResponseEntity<?> retrieveBeatSheet(long id) {
        try {
            // Find and return the BeatSheet by its ID
            BeatSheet beatSheet=beatSheetRepository.findById(id).get();
            return ResponseEntity.ok(beatSheet);
        }catch (Exception e){
            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to retrieve BeatSheet: " + e.getMessage());
        }
    }

    /**
     * Delete a BeatSheet by its ID.
     *
     * @param id The ID of the BeatSheet to delete.
     * @return ResponseEntity with a success message if the deletion is successful, or a not found response if the BeatSheet does not exist, or an error message if it fails.
     */
    public ResponseEntity<String> deleteBeatSheet(long id) {
        try {
            // Attempt to find an existing BeatSheet by its ID
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);

            // Check if the BeatSheet was found
            if(optionalExistingBeatSheet.isPresent()){
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();

                // Delete the BeatSheet
                beatSheetRepository.delete(existingBeatSheet);

                // Return a success response
                return ResponseEntity.ok("Success");
            } else {
                // Return a not found response if the BeatSheet doesn't exist
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to delete BeatSheet: " + e.getMessage());
        }
    }

    /**
     * Retrieve a list of all BeatSheets.
     *
     * @return ResponseEntity with a list of all retrieved BeatSheets if successful, or an error message if it fails.
     */
    public ResponseEntity<?> retrieveAllBeatSheets() {
        try {
            // Retrieve a list of all BeatSheets from the repository
            List<BeatSheet> beatSheets = beatSheetRepository.findAll();

            // Return a success response with the list of BeatSheets
            return ResponseEntity.ok(beatSheets);
        } catch (Exception e) {

            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to retrieve BeatSheets: " + e.getMessage());
        }
    }

    /**
     * Add a Beat to a BeatSheet.
     *
     * @param id  The ID of the BeatSheet to which the Beat will be added.
     * @param beat The Beat to be added.
     * @return ResponseEntity with the updated BeatSheet if the addition is successful, or a not found response if the BeatSheet doesn't exist, or an error message if it fails.
     */
    public ResponseEntity<?> addBeatToBeatSheet(long id, Beat beat) {
        try {
            // Attempt to find an existing BeatSheet by its ID
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);

            // Check if the BeatSheet was found
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();

                // Add the provided Beat to the list of beats in the BeatSheet
                beats.add(beat);

                // Save the updated BeatSheet
                BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);

                // Return a success response with the updated BeatSheet
                return ResponseEntity.ok(updatedBeatSheet);
            } else {
                // Return a not found response if the BeatSheet doesn't exist
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to add Beat to BeatSheet: " + e.getMessage());
        }
    }

    /**
     * Update a Beat in a BeatSheet.
     *
     * @param id          The ID of the BeatSheet containing the Beat to update.
     * @param beatId      The ID of the Beat to update.
     * @param updatedBeat The updated Beat object.
     * @return ResponseEntity with the updated BeatSheet if the update is successful, or a not found response if the BeatSheet or Beat doesn't exist, or an error message if it fails.
     */
    public ResponseEntity<?> updateBeatInBeatSheet(long id, long beatId, Beat updatedBeat) {
        try {
            // Attempt to find an existing BeatSheet by its ID
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);

            // Check if the BeatSheet was found
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();

                // Find the Beat to update within the BeatSheet
                Beat oldBeat = beats.stream()
                        .filter(beat -> beatId==beat.getId())
                        .findAny()
                        .orElse(null);

                // Check if the Beat was found
                if(oldBeat!=null) {
                    // Update the properties of the existing Beat
                    oldBeat.setDescription(updatedBeat.getDescription());
                    oldBeat.setTimestamp(updatedBeat.getTimestamp());
                    oldBeat.setActs(updatedBeat.getActs());

                    // Save the updated BeatSheet
                    BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);

                    // Return a success response with the updated BeatSheet
                    return ResponseEntity.ok(updatedBeatSheet);
                }else{
                    // Return a not found response if the Beat doesn't exist
                    return ResponseEntity.notFound().build();
                }

            } else {
                // Return a not found response if the BeatSheet doesn't exist
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to update Beat in BeatSheet: " + e.getMessage());
        }
    }

    /**
     * Delete a Beat from a BeatSheet.
     *
     * @param id     The ID of the BeatSheet containing the Beat to delete.
     * @param beatId The ID of the Beat to delete.
     * @return ResponseEntity with the updated BeatSheet if the deletion is successful, or a not found response if the BeatSheet or Beat doesn't exist, or an error message if it fails.
     */
    public ResponseEntity<?> deleteBeatInBeatSheet(long id, long beatId) {
        try {
            // Attempt to find an existing BeatSheet by its ID
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);

            // Check if the BeatSheet was found
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();

                // Find the Beat to delete within the BeatSheet
                Beat oldBeat = beats.stream()
                        .filter(beat -> beatId==beat.getId())
                        .findAny()
                        .orElse(null);

                // Check if the Beat was found
                if (oldBeat != null) {
                    // Remove the Beat from the list of beats in the BeatSheet
                    beats.remove(oldBeat);

                    // Save the updated BeatSheet
                    BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);

                    // Return a success response with the updated BeatSheet
                    return ResponseEntity.ok(updatedBeatSheet);
                } else {
                    // Return a not found response if the Beat doesn't exist
                    return ResponseEntity.notFound().build();
                }
            } else {
                // Return a not found response if the BeatSheet doesn't exist
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to delete Beat in BeatSheet: " + e.getMessage());
        }
    }

    /**
     * Add an Act to a Beat in a BeatSheet.
     *
     * @param id         The ID of the BeatSheet containing the Beat.
     * @param beatId     The ID of the Beat to which the Act will be added.
     * @param updatedAct The Act to be added.
     * @return ResponseEntity with the updated BeatSheet if the addition is successful, or a not found response if the BeatSheet, Beat, or Act doesn't exist, or an error message if it fails.
     */
    public ResponseEntity<?> addActToBeat(long id, long beatId, Act updatedAct) {
        try {
            // Attempt to find an existing BeatSheet by its ID
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);

            // Check if the BeatSheet was found
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();

                // Check if the BeatSheet was found
                Beat oldBeat = beats.stream()
                        .filter(beat -> beatId==beat.getId())
                        .findAny()
                        .orElse(null);

                // Check if the Beat was found
                if (oldBeat != null) {
                    List<Act> acts = oldBeat.getActs();

                    // Add the provided Act to the list of acts in the Beat
                    acts.add(updatedAct);

                    // Save the updated BeatSheet
                    BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);

                    // Return a success response with the updated BeatSheet
                    return ResponseEntity.ok(updatedBeatSheet);
                } else {
                    // Return a not found response if the Beat doesn't exist
                    return ResponseEntity.notFound().build();
                }
            } else {
                // Return a not found response if the BeatSheet doesn't exist
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to add Act to Beat: " + e.getMessage());
        }
    }

    /**
     * Update an Act within a Beat in a BeatSheet.
     *
     * @param id         The ID of the BeatSheet containing the Beat.
     * @param beatId     The ID of the Beat containing the Act to update.
     * @param actId      The ID of the Act to update.
     * @param updatedAct The updated Act object.
     * @return ResponseEntity with the updated BeatSheet if the update is successful, or a not found response if the BeatSheet, Beat, or Act doesn't exist, or an error message if it fails.
     */
    public ResponseEntity<?> updateActInBeat(long id, long beatId, long actId, Act updatedAct) {
        try {
            // Attempt to find an existing BeatSheet by its ID
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);

            // Check if the BeatSheet was found
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();

                // Find the Beat within the BeatSheet
                Beat oldBeat = beats.stream()
                        .filter(beat -> beatId==beat.getId())
                        .findAny()
                        .orElse(null);


                List<Act> acts = null;
                if (oldBeat != null) {
                    acts = oldBeat.getActs();
                }

                // Find the Act to update within the Beat
                Act oldAct = null;
                if (acts != null) {
                    oldAct = acts.stream()
                                    .filter(act -> actId==act.getId())
                                    .findAny()
                                    .orElse(null);
                }
                // Check if the Act was found
                if (oldAct != null) {
                    // Update the properties of the existing Act
                    oldAct.setDescription(updatedAct.getDescription());
                    oldAct.setTimestamp(updatedAct.getTimestamp());
                    oldAct.setDuration(updatedAct.getDuration());
                    oldAct.setCameraAngle(updatedAct.getCameraAngle());

                    // Save the updated BeatSheet
                    BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);

                    // Return a success response with the updated BeatSheet
                    return ResponseEntity.ok(updatedBeatSheet);
                } else {
                    // Return a not found response if the Act doesn't exist
                    return ResponseEntity.notFound().build();
                }
            } else {
                // Return a not found response if the BeatSheet doesn't exist
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to update Act in Beat: " + e.getMessage());
        }
    }

    /**
     * Delete an Act from a Beat in a BeatSheet.
     *
     * @param id     The ID of the BeatSheet containing the Beat.
     * @param beatId The ID of the Beat containing the Act to delete.
     * @param actId  The ID of the Act to delete.
     * @return ResponseEntity with the updated BeatSheet if the deletion is successful, or a not found response if the BeatSheet, Beat, or Act doesn't exist, or an error message if it fails.
     */
    public ResponseEntity<?> deleteActInBeat(long id, long beatId, long actId) {
        try {
            // Attempt to find an existing BeatSheet by its ID
            Optional<BeatSheet> optionalExistingBeatSheet = beatSheetRepository.findById(id);

            // Check if the BeatSheet was found
            if (optionalExistingBeatSheet.isPresent()) {
                BeatSheet existingBeatSheet = optionalExistingBeatSheet.get();
                List<Beat> beats = existingBeatSheet.getBeats();

                // Find the Beat within the BeatSheet
                Beat oldBeat = beats.stream()
                        .filter(beat -> beatId==beat.getId())
                        .findAny()
                        .orElse(null);

                List<Act> acts = oldBeat.getActs();

                // Find the Act to delete within the Beat
                Act oldAct = acts.stream()
                        .filter(act -> actId == act.getId())
                        .findAny()
                        .orElse(null);

                // Check if the Act was found
                if (oldAct != null) {
                    // Remove the Act from the list of acts in the Beat
                    acts.remove(oldAct);

                    // Save the updated BeatSheet
                    BeatSheet updatedBeatSheet = beatSheetRepository.save(existingBeatSheet);

                    // Return a success response with the updated BeatSheet
                    return ResponseEntity.ok(updatedBeatSheet);
                } else {
                    // Return a not found response if the Act doesn't exist
                    return ResponseEntity.notFound().build();
                }
            } else {
                // Return a not found response if the BeatSheet doesn't exist
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle any exceptions and return an error response with a message
            return ResponseEntity.badRequest().body("Failed to delete Act in Beat: " + e.getMessage());
        }
    }
}
