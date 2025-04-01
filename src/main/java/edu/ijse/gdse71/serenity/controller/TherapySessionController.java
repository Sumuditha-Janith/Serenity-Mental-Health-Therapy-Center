package edu.ijse.gdse71.serenity.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class TherapySessionController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSchedule;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPatient;

    @FXML
    private TableColumn<?, ?> colProgram;

    @FXML
    private TableColumn<?, ?> colSessionId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTherapist;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private DatePicker datePickerSession;

    @FXML
    private Label errorMessage;

    @FXML
    private Label lblSessionId;

    @FXML
    private ChoiceBox<?> selectPatient;

    @FXML
    private ChoiceBox<?> selectProgram;

    @FXML
    private ChoiceBox<?> selectTherapist;

    @FXML
    private ChoiceBox<?> selectTime;

    @FXML
    private TableView<?> tblTherapySessions;

    @FXML
    void cancelSession(ActionEvent event) {

    }

    @FXML
    void resetForm(ActionEvent event) {

    }

    @FXML
    void scheduleSession(ActionEvent event) {

    }

    @FXML
    void sessionSelectOnAction(MouseEvent event) {

    }

    @FXML
    void updateSession(ActionEvent event) {

    }
}
