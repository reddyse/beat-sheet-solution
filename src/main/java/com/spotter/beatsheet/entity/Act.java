package com.spotter.beatsheet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name="ACT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Act {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private Timestamp timestamp;

    private int duration;

    private String cameraAngle;

}
