package edu.ijse.gdse71.serenity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "therapists")
public class Therapist implements SuperEntity{

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String specialization;

    @OneToMany(mappedBy = "therapist")
    private List<TherapySession> therapySessions;

}
