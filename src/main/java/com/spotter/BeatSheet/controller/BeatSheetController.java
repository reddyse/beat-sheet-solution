package com.spotter.BeatSheet.controller;

import com.spotter.BeatSheet.entity.Act;
import com.spotter.BeatSheet.entity.Beat;
import com.spotter.BeatSheet.entity.BeatSheet;
import com.spotter.BeatSheet.service.BeatSheetService;
import com.spotter.BeatSheet.service.GenerateNextActService;
import com.spotter.BeatSheet.service.GenerateNextBeatService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beatsheet")
public class BeatSheetController {

    @Autowired
    private BeatSheetService beatSheetService;

    @Autowired
    private GenerateNextBeatService generateNextBeatService;

    @Autowired
    private GenerateNextActService generateNextActService;


    //BeatSheet
    @PostMapping
    @Operation(summary = "Create Beat Sheet", description = "A new Beat Sheet can be created using this endpoint")
    public ResponseEntity<String> createBeatSheet(@RequestBody BeatSheet beatSheet){
        return beatSheetService.createBeatSheet(beatSheet);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve Beat Sheet", description = "Fetch an existing Beat Sheet using this endpoint")
    public ResponseEntity<?> retrieveBeatSheet(@PathVariable long id){
        return beatSheetService.retrieveBeatSheet(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Beat Sheet", description = "Update an existing Beat Sheet using this endpoint")
    public ResponseEntity<String> updateBeatSheet(@PathVariable long id, @RequestBody BeatSheet beatSheet){
        return beatSheetService.updateBeatSheet(id, beatSheet);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Beat Sheet", description = "Delete an existing Beat Sheet using this endpoint")
    public String deleteBeatSheet(@PathVariable long id){
        //TODO: Currently not deleting child records, should delete everything w.r.t the beatsheet
        beatSheetService.deleteBeatSheet(id);
        return "Success";
    }

    @GetMapping("/")
    @Operation(summary = "Retrieve All Beat Sheets", description = "Fetch all available Beat Sheets using this endpoint")
    public ResponseEntity<?> retrieveBeatSheet(){
        return beatSheetService.retrieveAllBeatSheets();
    }

    //Beat
    @PostMapping("/{id}/beat")
    @Operation(summary = "Add a new Beat to Beat Sheet", description = "Add a new Beat to Beat Sheet using this endpoint")
    public ResponseEntity<?> addBeatToBeatSheet(@PathVariable long id, @RequestBody Beat beat){
        return beatSheetService.addBeatToBeatSheet(id, beat);
    }

    @PutMapping("/{id}/beat/{beatId}")
    @Operation(summary = "Update a Beat in a Beat Sheet", description = "Update a Beat in a Beat Sheet using this endpoint")
    public ResponseEntity<?> updateBeatInBeatSheet(@PathVariable long id, @PathVariable long beatId, @RequestBody Beat updatedBeat){
        return beatSheetService.updateBeatInBeatSheet(id, beatId, updatedBeat);
    }

    @DeleteMapping("/{id}/beat/{beatId}")
    @Operation(summary = "Delete a Beat in a Beat Sheet", description = "Delete a Beat in a Beat Sheet using this endpoint")
    public ResponseEntity<?> deleteBeatInBeatSheet(@PathVariable long id, @PathVariable long beatId){
        return beatSheetService.deleteBeatInBeatSheet(id, beatId);
    }

    //Act
    @PostMapping("/{id}/beat/{beatId}/act")
    @Operation(summary = "Add a new Act to a Beat", description = "Add a new Act to a Beat in a Beat Sheet using this endpoint")
    public ResponseEntity<?> addActToBeat(@PathVariable long id, @PathVariable long beatId, @RequestBody Act updatedAct){
        return beatSheetService.addActToBeat(id,beatId, updatedAct);
    }

    @PutMapping("/{id}/beat/{beatId}/act/{actId}")
    @Operation(summary = "Update an exiting Act in a Beat in a Beat Sheet", description = "Update an existing Act in a Beat in a Beat Sheet using this endpoint")
    public ResponseEntity<?> updateActInBeat(@PathVariable long id, @PathVariable long beatId, @PathVariable long actId, @RequestBody Act updatedAct){
        return beatSheetService.updateActInBeat(id, beatId, actId, updatedAct);
    }

    @DeleteMapping("/{id}/beat/{beatId}/act/{actId}")
    @Operation(summary = "Delete an exiting Act in a Beat from a Beat Sheet", description = "Delete an existing Act in a Beat from a Beat Sheet using this endpoint")
    public ResponseEntity<?> deleteActInBeat(@PathVariable long id, @PathVariable long beatId, @PathVariable long actId){
        return beatSheetService.deleteActInBeat(id, beatId, actId);
    }

    //Predict new Beat
    @PostMapping("/generateNextBeat")
    @Operation(summary = "Generates a new beat based on context provided", description = "Generate a new beat")
    public ResponseEntity<?> generateNewBeat(@RequestBody BeatSheet beatSheet){
        return generateNextBeatService.predictBeatSheet(beatSheet);
    }

    //Predict new Act
    @PostMapping("/generateNextAct")
    @Operation(summary = "Generates a new beat based on context provided", description = "Generate a new beat")
    public ResponseEntity<?> generateNewAct(@RequestBody Beat beat){
        return generateNextActService.predictAct(beat);
    }




}
