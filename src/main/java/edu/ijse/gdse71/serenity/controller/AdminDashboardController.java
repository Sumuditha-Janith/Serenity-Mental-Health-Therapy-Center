package edu.ijse.gdse71.serenity.controller;

import edu.ijse.gdse71.serenity.bo.BOFactory;
import edu.ijse.gdse71.serenity.bo.custom.PatientBO;
import edu.ijse.gdse71.serenity.bo.custom.TherapyProgramBO;
import edu.ijse.gdse71.serenity.bo.custom.TherapySessionBO;
import edu.ijse.gdse71.serenity.bo.custom.TherapistBO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    private AnchorPane loadPageAnchor;

    @FXML
    private Label patientsCount;

    @FXML
    private Label programsCount;

    @FXML
    private Label sessionsCount;

    @FXML
    private Label therapistsCount;

    private final PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    private final TherapyProgramBO programBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    private final TherapySessionBO sessionBO = (TherapySessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_SESSION);
    private final TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadCounts();
        } catch (Exception e) {
            handleCountLoadingError(e);
        }
    }

    private void loadCounts() throws Exception {
        int patientCount = patientBO.getAll().size();
        patientsCount.setText(String.valueOf(patientCount));

        int programCount = programBO.getAll().size();
        programsCount.setText(String.valueOf(programCount));

        int sessionCount = sessionBO.getAll().size();
        sessionsCount.setText(String.valueOf(sessionCount));

        int therapistCount = therapistBO.getAll().size();
        therapistsCount.setText(String.valueOf(therapistCount));
    }

    private void handleCountLoadingError(Exception e) {
        e.printStackTrace();
        patientsCount.setText("0");
        programsCount.setText("0");
        sessionsCount.setText("0");
        therapistsCount.setText("0");
    }

    @FXML
    void navAppointments(ActionEvent event) throws IOException {
        loadPage("/view/TherapySession.fxml");
    }

    @FXML
    void navBilling(ActionEvent event) throws IOException {
        loadPage("/view/Payment.fxml");
    }

    @FXML
    void navDash(ActionEvent event) throws IOException {
        loadPage("/view/DashBoard.fxml");
    }

    @FXML
    void navPatient(ActionEvent event) throws IOException {
        loadPage("/view/Patient.fxml");
    }

    @FXML
    void navPrograms(ActionEvent event) throws IOException {
        loadPage("/view/TherapyProgram.fxml");
    }

    @FXML
    void navTherapist(ActionEvent event) throws IOException {
        loadPage("/view/Therapist.fxml");
    }

    @FXML
    void navReports(ActionEvent event) {

    }

    @FXML
    void logOutOnAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Any unsaved changes will be lost.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            performLogout();
        }
    }

    private void performLogout() throws IOException {
        Stage stage = (Stage) loadPageAnchor.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInScreen.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Serenity");
        stage.setResizable(false);
        stage.show();

        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Logged Out");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText("You have been successfully logged out.");
        infoAlert.show();
    }

    private void loadPage(String fxmlPath) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource(fxmlPath)));
    }
}