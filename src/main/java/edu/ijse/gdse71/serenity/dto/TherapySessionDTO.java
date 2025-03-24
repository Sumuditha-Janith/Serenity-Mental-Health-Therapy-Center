package edu.ijse.gdse71.serenity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapySessionDTO {
    private String id;
    private String date;
    private String time;
    private String status;
    private TherapistDTO therapist;
    private PatientDTO patient;
    private TherapyProgramDTO therapyProgram;

}
