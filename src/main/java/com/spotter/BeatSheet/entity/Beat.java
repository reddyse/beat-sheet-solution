package com.spotter.BeatSheet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    //@ManyToOne(targetEntity = BeatSheet.class, cascade = CascadeType.ALL)
    //@JoinColumn(name = "beat_sheet_id", referencedColumnName = "id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    //@ManyToOne
    //@JoinColumn(name = "beat_sheet_id", referencedColumnName = "id",insertable = false, updatable = false)
    //private BeatSheet beatSheet;

    //@OneToMany(mappedBy = "beat")
    @OneToMany(targetEntity = Act.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "beat_act_fk", referencedColumnName = "id")
    private List<Act> acts;
}
