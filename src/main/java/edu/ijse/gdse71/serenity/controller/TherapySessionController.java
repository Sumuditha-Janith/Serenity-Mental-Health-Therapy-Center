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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
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
    private Label errorMessage;

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
    void cancelSession(ActionEvent event) throws Exception {
        TherapySessionDTO selectedSession = tblTherapySessions.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            therapySessionBO.deleteByPK(selectedSession.getId());
            loadTherapyProgramTable();
        } else {
            errorMessage.setText("Please select a session to cancel.");
        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        lblSessionId.setText(therapySessionBO.getLastPK().orElse("1"));
        selectPatient.getSelectionModel().clearSelection();
        selectProgram.getSelectionModel().clearSelection();
        selectTherapist.getSelectionModel().clearSelection();
        selectTime.getSelectionModel().clearSelection();
        datePickerSession.setValue(null);
    }

    @FXML
    void scheduleSession(ActionEvent event) {
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

        paymentSessionBO.saveSession(therapySessionDTO,paymentDTO);

        loadTherapyProgramTable();
    }

    @FXML
    void onclickTherapySessionTable(MouseEvent event) {
        TherapySessionDTO selectedSession = tblTherapySessions.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            lblSessionId.setText(selectedSession.getId());
            datePickerSession.setValue(java.time.LocalDate.parse(selectedSession.getDate()));
            selectTime.setValue(selectedSession.getTime());
            selectPatient.setValue(selectedSession.getPatient().getName());
            selectPatient.setDisable(true);
            selectTherapist.setValue(selectedSession.getTherapist().getName());
            selectTherapist.setDisable(true);
            selectProgram.setValue(selectedSession.getTherapyProgram().getName());
            selectProgram.setDisable(true);
        }
    }

    @FXML
    void updateSession(ActionEvent event) {
        try {
            TherapySessionDTO selectedSession = tblTherapySessions.getSelectionModel().getSelectedItem();

            if (selectedSession != null) {
                String originalStatus = selectedSession.getStatus();

                TherapySessionDTO therapySessionDTO = new TherapySessionDTO();
                therapySessionDTO.setId(lblSessionId.getText());
                therapySessionDTO.setDate(datePickerSession.getValue().toString());
                therapySessionDTO.setTime(selectTime.getValue());
                therapySessionDTO.setStatus(originalStatus); // Preserve original status
                therapySessionDTO.setPatient(patientDTO);
                therapySessionDTO.setTherapist(therapistDTO);
                therapySessionDTO.setTherapyProgram(therapyProgramDTO);

                boolean updated = therapySessionBO.update(therapySessionDTO);

                if (updated) {
                    loadTherapyProgramTable();
                    errorMessage.setText("Session updated successfully!");
                } else {
                    errorMessage.setText("Failed to update session");
                }
            } else {
                errorMessage.setText("Please select a session to update");
            }
        } catch (Exception e) {
            errorMessage.setText("Error updating session: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblSessionId.setText(therapySessionBO.getLastPK().orElse("1"));

        List<String> patientList = patientBO.patientList();
        List<String> therapistList = therapistBO.therapistList();
        List<String> programList = therapyProgramBO.getProgramList();

        selectPatient.getItems().addAll(patientList);
        selectTherapist.getItems().addAll(therapistList);
        selectProgram.getItems().addAll(programList);

        selectTime.getItems().addAll("08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM");

        colSessionId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        colTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colPatient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getName()));
        colTherapist.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTherapist().getName()));
        colProgram.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTherapyProgram().getName()));

        loadTherapyProgramTable();
    }

    private void loadTherapyProgramTable() {
        List<TherapySessionDTO> therapySessionList = therapySessionBO.getAll();
        ObservableList<TherapySessionDTO> therapySessionTMS = FXCollections.observableArrayList(therapySessionList);
        tblTherapySessions.setItems(therapySessionTMS);
    }
}