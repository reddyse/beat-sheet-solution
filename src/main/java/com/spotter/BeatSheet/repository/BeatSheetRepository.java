package com.spotter.BeatSheet.repository;

import com.spotter.BeatSheet.entity.BeatSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeatSheetRepository extends JpaRepository<BeatSheet,Long> {
}
