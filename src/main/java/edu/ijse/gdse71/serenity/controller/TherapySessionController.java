package edu.ijse.gdse71.serenity.controller;

import edu.ijse.gdse71.serenity.bo.BOFactory;
import edu.ijse.gdse71.serenity.bo.custom.impl.*;
import edu.ijse.gdse71.serenity.dto.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TherapySessionController implements Initializable {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSchedule;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TherapySessionDTO, String> colDate;

    @FXML
    private TableColumn<TherapySessionDTO, String> colPatient;

    @FXML
    private TableColumn<TherapySessionDTO, String> colProgram;

    @FXML
    private TableColumn<TherapySessionDTO, String> colSessionId;

    @FXML
    private TableColumn<TherapySessionDTO, String> colStatus;

    @FXML
    private TableColumn<TherapySessionDTO, String> colTherapist;

    @FXML
    private TableColumn<TherapySessionDTO, String> colTime;

    @FXML
    private DatePicker datePickerSession;

    @FXML
    private Label lblSessionId;

    @FXML
    private ChoiceBox<String> selectPatient;

    @FXML
    private ChoiceBox<String> selectProgram;

    @FXML
    private ChoiceBox<String> selectTherapist;

    @FXML
    private ChoiceBox<String> selectTime;

    @FXML
    private TableView<TherapySessionDTO> tblTherapySessions;

    private final TherapySessionBOImpl therapySessionBO = (TherapySessionBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_SESSION);
    private final PatientBOImpl patientBO = (PatientBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    private final TherapistBOImpl therapistBO = (TherapistBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    private final TherapyProgramBOImpl therapyProgramBO = (TherapyProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    private final PaymentBOImpl paymentBO = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    private final PaymentSessionBOImpl paymentSessionBO = (PaymentSessionBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT_SESSION);

    private PatientDTO patientDTO = new PatientDTO();
    private TherapistDTO therapistDTO = new TherapistDTO();
    private TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO();

    @FXML
    void cancelSession(ActionEvent event) {
        try {
            TherapySessionDTO selectedSession = tblTherapySessions.getSelectionModel().getSelectedItem();
            if (selectedSession == null) {
                throw new Exception("Please select a session to cancel");
            }

            therapySessionBO.deleteByPK(selectedSession.getId());
            loadTherapyProgramTable();
            new Alert(Alert.AlertType.INFORMATION, "Session cancelled successfully").show();
            refreshPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void scheduleSession(ActionEvent event) {
        try {
            if (selectPatient.getValue() == null || selectTherapist.getValue() == null ||
                    selectProgram.getValue() == null || datePickerSession.getValue() == null ||
                    selectTime.getValue() == null) {
                throw new Exception("Please fill all required fields");
            }

            validateSessionDate();

            String patientName = selectPatient.getValue();
            String therapistName = selectTherapist.getValue();
            String programName = selectProgram.getValue();

            patientDTO = patientBO.getAllPatient(patientName);
            therapistDTO = therapistBO.getAllTherapist(therapistName);
            therapyProgramDTO = therapyProgramBO.getAllTherapyProgram(programName);

            String sessionId = lblSessionId.getText();
            String date = datePickerSession.getValue().toString();
            String time = selectTime.getValue();
            String status = "Pending";

            TherapySessionDTO therapySessionDTO = new TherapySessionDTO();
            therapySessionDTO.setId(sessionId);
            therapySessionDTO.setDate(date);
            therapySessionDTO.setTime(time);
            therapySessionDTO.setStatus(status);
            therapySessionDTO.setPatient(patientDTO);
            therapySessionDTO.setTherapist(therapistDTO);
            therapySessionDTO.setTherapyProgram(therapyProgramDTO);

            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(paymentBO.getLastPK().orElse("1"));
            paymentDTO.setAmount(therapyProgramBO.getAmount(programName));
            paymentDTO.setDate(date);
            paymentDTO.setStatus("Pending");
            paymentDTO.setPatient(patientDTO);
            paymentDTO.setTherapySession(therapySessionDTO);

            paymentSessionBO.saveSession(therapySessionDTO, paymentDTO);

            loadTherapyProgramTable();
            new Alert(Alert.AlertType.INFORMATION, "Session scheduled successfully").show();
            refreshPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void onclickTherapySessionTable(MouseEvent event) {
        try {
            TherapySessionDTO selectedSession = tblTherapySessions.getSelectionModel().getSelectedItem();
            if (selectedSession == null) return;

            lblSessionId.setText(selectedSession.getId());
            datePickerSession.setValue(java.time.LocalDate.parse(selectedSession.getDate()));
            selectTime.setValue(selectedSession.getTime());
            selectPatient.setValue(selectedSession.getPatient().getName());
            selectPatient.setDisable(true);
            selectTherapist.setValue(selectedSession.getTherapist().getName());
            selectTherapist.setDisable(true);
            selectProgram.setValue(selectedSession.getTherapyProgram().getName());
            selectProgram.setDisable(true);
            btnSchedule.setDisable(true);
            btnUpdate.setDisable(false);

            btnCancel.setDisable("Completed".equalsIgnoreCase(selectedSession.getStatus()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading session details: " + e.getMessage()).show();
        }
    }

    @FXML
    void updateSession(ActionEvent event) {
        try {
            TherapySessionDTO selectedSession = tblTherapySessions.getSelectionModel().getSelectedItem();
            if (selectedSession == null) {
                throw new Exception("Please select a session to update");
            }

            if (datePickerSession.getValue() == null || selectTime.getValue() == null) {
                throw new Exception("Please fill all required fields");
            }

            validateSessionDate();

            String originalStatus = selectedSession.getStatus();

            TherapySessionDTO therapySessionDTO = new TherapySessionDTO();
            therapySessionDTO.setId(lblSessionId.getText());
            therapySessionDTO.setDate(datePickerSession.getValue().toString());
            therapySessionDTO.setTime(selectTime.getValue());
            therapySessionDTO.setStatus(originalStatus);
            therapySessionDTO.setPatient(patientDTO);
            therapySessionDTO.setTherapist(therapistDTO);
            therapySessionDTO.setTherapyProgram(therapyProgramDTO);

            boolean updated = therapySessionBO.update(therapySessionDTO);

            if (updated) {
                loadTherapyProgramTable();
                new Alert(Alert.AlertType.INFORMATION, "Session updated successfully").show();
                refreshPage();

                if ("Completed".equalsIgnoreCase(originalStatus)) {
                    btnCancel.setDisable(true);
                }
            } else {
                throw new Exception("Failed to update session");
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            lblSessionId.setText(therapySessionBO.getLastPK().orElse("1"));

            List<String> patientList = patientBO.patientList();
            List<String> therapistList = therapistBO.therapistList();
            List<String> programList = therapyProgramBO.getProgramList();

            selectPatient.getItems().addAll(patientList);
            selectTherapist.getItems().addAll(therapistList);
            selectProgram.getItems().addAll(programList);

            selectTime.getItems().addAll("08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
                    "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM");

            colSessionId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
            colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
            colTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));
            colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
            colPatient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getName()));
            colTherapist.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTherapist().getName()));
            colProgram.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTherapyProgram().getName()));

            loadTherapyProgramTable();
            refreshPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to initialize: " + e.getMessage()).show();
        }
    }

    private void loadTherapyProgramTable() {
        try {
            List<TherapySessionDTO> therapySessionList = therapySessionBO.getAll();
            ObservableList<TherapySessionDTO> therapySessionTMS = FXCollections.observableArrayList(therapySessionList);
            tblTherapySessions.setItems(therapySessionTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load sessions: " + e.getMessage()).show();
        }
    }

    private void refreshPage() {
        try {
            lblSessionId.setText(therapySessionBO.getLastPK().orElse("1"));
            datePickerSession.setValue(null);
            selectTime.setValue(null);
            selectPatient.setValue(null);
            selectTherapist.setValue(null);
            selectProgram.setValue(null);

            selectPatient.setDisable(false);
            selectTherapist.setDisable(false);
            selectProgram.setDisable(false);

            btnSchedule.setDisable(false);
            btnUpdate.setDisable(true);
            btnCancel.setDisable(true);

            tblTherapySessions.getSelectionModel().clearSelection();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to refresh page: " + e.getMessage()).show();
        }
    }

    private void validateSessionDate() throws Exception {
        if (datePickerSession.getValue() == null) {
            throw new Exception("Please select a session date");
        }

        LocalDate selectedDate = datePickerSession.getValue();
        LocalDate currentDate = LocalDate.now();

        if (selectedDate.isBefore(currentDate)) {
            throw new Exception("Session date cannot be before today's date");
        }
    }

}