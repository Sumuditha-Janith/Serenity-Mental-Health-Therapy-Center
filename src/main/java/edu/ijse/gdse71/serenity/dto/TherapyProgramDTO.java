package edu.ijse.gdse71.serenity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapyProgramDTO {
    private String programId;
    private String name;
    private String duration;
    private double fee;

//    @OneToMany(mappedBy = "therapyProgram")
//    private List<TherapySessionsDTO> therapySessions;
}
