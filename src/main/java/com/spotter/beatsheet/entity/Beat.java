package com.spotter.beatsheet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="BEAT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Beat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private Timestamp timestamp;

    @OneToMany(targetEntity = Act.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "beat_act_fk", referencedColumnName = "id")
    private List<Act> acts;
}
