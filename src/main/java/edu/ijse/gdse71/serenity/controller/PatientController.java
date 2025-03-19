package edu.ijse.gdse71.serenity.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PatientController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<?> cmbGender;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colRegDate;

    @FXML
    private Label dpRegDate;

    @FXML
    private Label errorMessage;

    @FXML
    private Label lblPatientId;

    @FXML
    private TableView<?> tblPatients;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    void addPatient(ActionEvent event) {

    }

    @FXML
    void deletePatient(ActionEvent event) {

    }

    @FXML
    void petientSelectOnAction(MouseEvent event) {

    }

    @FXML
    void resetForm(ActionEvent event) {

    }

    @FXML
    void updatePatient(ActionEvent event) {

    }

}
