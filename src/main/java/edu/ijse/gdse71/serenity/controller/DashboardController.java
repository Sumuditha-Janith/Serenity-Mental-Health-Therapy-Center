package edu.ijse.gdse71.serenity.controller;

import edu.ijse.gdse71.serenity.bo.BOFactory;
import edu.ijse.gdse71.serenity.bo.custom.PatientBO;
import edu.ijse.gdse71.serenity.bo.custom.TherapyProgramBO;
import edu.ijse.gdse71.serenity.bo.custom.TherapySessionBO;
import edu.ijse.gdse71.serenity.bo.custom.TherapistBO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label patientsCount;

    @FXML
    private Label programsCount;

    @FXML
    private Label sessionsCount;

    @FXML
    private Label therapistsCount;

    private PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    private TherapyProgramBO programBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    private TherapySessionBO sessionBO = (TherapySessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_SESSION);
    private TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            int patientCount = patientBO.getAll().size();
            patientsCount.setText(String.valueOf(patientCount));

            int programCount = programBO.getAll().size();
            programsCount.setText(String.valueOf(programCount));

            int sessionCount = sessionBO.getAll().size();
            sessionsCount.setText(String.valueOf(sessionCount));

            int therapistCount = therapistBO.getAll().size();
            therapistsCount.setText(String.valueOf(therapistCount));

        } catch (Exception e) {
            e.printStackTrace();

            patientsCount.setText("0");
            programsCount.setText("0");
            sessionsCount.setText("0");
            therapistsCount.setText("0");
        }
    }
}