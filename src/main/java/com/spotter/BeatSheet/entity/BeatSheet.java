package com.spotter.BeatSheet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name="BEAT_SHEET")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeatSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    //@OneToMany(mappedBy = "beatSheet")
    @OneToMany(targetEntity = Beat.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "beatSheet_beats_fk", referencedColumnName = "id")
    private List<Beat> beats;
}
