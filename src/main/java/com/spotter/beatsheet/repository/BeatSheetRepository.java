package com.spotter.beatsheet.repository;

import com.spotter.beatsheet.entity.BeatSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeatSheetRepository extends JpaRepository<BeatSheet,Long> {
}
