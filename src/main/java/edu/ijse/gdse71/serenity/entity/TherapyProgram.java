package edu.ijse.gdse71.serenity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "therapy_programs")
public class TherapyProgram implements SuperEntity{

    @Id
    private String programId;
    private String name;
    private String duration;
    private double fee;

    @OneToMany(mappedBy = "therapyProgram")
    private List<TherapySession> therapySessions;
}
