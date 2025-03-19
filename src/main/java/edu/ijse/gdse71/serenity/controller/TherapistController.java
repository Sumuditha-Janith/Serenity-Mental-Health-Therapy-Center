package edu.ijse.gdse71.serenity.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TherapistController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSpecialization;

    @FXML
    private Label errorMessage;

    @FXML
    private Label lblTherapistId;

    @FXML
    private ChoiceBox<?> specializationChoice;

    @FXML
    private TableView<?> tblTherapists;

    @FXML
    private TextField txtName;

    @FXML
    void addTherapist(ActionEvent event) {

    }

    @FXML
    void deleteTherapist(ActionEvent event) {

    }

    @FXML
    void resetForm(ActionEvent event) {

    }

    @FXML
    void therapistSelectOnAction(MouseEvent event) {

    }

    @FXML
    void updateTherapist(ActionEvent event) {

    }

}
