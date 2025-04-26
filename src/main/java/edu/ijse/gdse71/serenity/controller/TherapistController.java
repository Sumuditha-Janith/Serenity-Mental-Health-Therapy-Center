package edu.ijse.gdse71.serenity.controller;

import edu.ijse.gdse71.serenity.bo.BOFactory;
import edu.ijse.gdse71.serenity.bo.custom.impl.TherapistBOImpl;
import edu.ijse.gdse71.serenity.bo.custom.impl.TherapyProgramBOImpl;
import edu.ijse.gdse71.serenity.dto.TherapistDTO;
import edu.ijse.gdse71.serenity.exeception.RequiredFieldException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TherapistController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TherapistDTO, String> colId;

    @FXML
    private TableColumn<TherapistDTO, String> colName;

    @FXML
    private TableColumn<TherapistDTO, String> colSpecialization;

    @FXML
    private Label lblTherapistId;

    @FXML
    private ChoiceBox<String> specializationChoice;

    @FXML
    private TableView<TherapistDTO> tblTherapists;

    @FXML
    private TextField txtName;

    private String id;
    private final TherapistBOImpl therapistBO = (TherapistBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    private final TherapyProgramBOImpl therapyProgramBO = (TherapyProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @FXML
    void addTherapist(ActionEvent event) {
        try {
            String name = txtName.getText();
            String specialization = specializationChoice.getValue();

            if(name.isEmpty()) {
                throw new RequiredFieldException("therapist name");
            }
            if(specialization == null) {
                throw new RequiredFieldException("specialization");
            }

            TherapistDTO therapistDTO = new TherapistDTO();
            therapistDTO.setId(this.id);
            therapistDTO.setName(name);
            therapistDTO.setSpecialization(specialization);

            boolean isAdded = therapistBO.save(therapistDTO);

            if(!isAdded) {
                throw new Exception("Failed to add therapist");
            }

            new Alert(Alert.AlertType.INFORMATION, "Therapist added successfully").show();
            refreshPage();
            this.id = therapistBO.getLastPK().orElse("Error");
            lblTherapistId.setText(this.id);
            loadTherapistTable();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void deleteTherapist(ActionEvent event) {
        try {
            String id = lblTherapistId.getText();
            if (id == null || id.isEmpty()) {
                throw new Exception("No therapist selected");
            }

            boolean isDeleted = therapistBO.deleteByPK(id);
            if (!isDeleted) {
                throw new Exception("You cannot delete therapists associated in the Appointments table");
            }

            new Alert(Alert.AlertType.INFORMATION, "Therapist deleted successfully").show();
            refreshPage();
            loadTherapistTable();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void onClickTherapistTable(MouseEvent event) {
        try {
            TherapistDTO selectedTherapist = tblTherapists.getSelectionModel().getSelectedItem();
            if (selectedTherapist == null) return;

            lblTherapistId.setText(selectedTherapist.getId());
            txtName.setText(selectedTherapist.getName());
            specializationChoice.setValue(selectedTherapist.getSpecialization());
            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading therapist: " + e.getMessage()).show();
        }
    }

    @FXML
    void updateTherapist(ActionEvent event) {
        try {
            String id = lblTherapistId.getText();
            String name = txtName.getText();
            String specialization = specializationChoice.getValue();

            if(name.isEmpty()) {
                throw new RequiredFieldException("therapist name");
            }
            if(specialization == null) {
                throw new RequiredFieldException("specialization");
            }

            TherapistDTO therapistDTO = new TherapistDTO();
            therapistDTO.setId(id);
            therapistDTO.setName(name);
            therapistDTO.setSpecialization(specialization);

            boolean isUpdated = therapistBO.update(therapistDTO);
            if (!isUpdated) {
                throw new Exception("Failed to update therapist");
            }

            new Alert(Alert.AlertType.INFORMATION, "Therapist updated successfully").show();
            refreshPage();
            loadTherapistTable();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.id = therapistBO.getLastPK().orElse("Error");
            lblTherapistId.setText(this.id);

            ArrayList<String> programList = therapyProgramBO.getProgramList();
            specializationChoice.getItems().addAll(programList);

            colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
            colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            colSpecialization.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialization()));

            loadTherapistTable();
            refreshPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Initialization error: " + e.getMessage()).show();
        }
    }

    private void loadTherapistTable() {
        try {
            List<TherapistDTO> therapistList = therapistBO.getAll();
            ObservableList<TherapistDTO> therapistTMS = FXCollections.observableArrayList(therapistList);
            tblTherapists.setItems(therapistTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load therapists: " + e.getMessage()).show();
        }
    }

    private void refreshPage() {
        try {
            txtName.clear();
            specializationChoice.setValue(null);
            specializationChoice.setDisable(false);
            btnAdd.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            tblTherapists.getSelectionModel().clearSelection();
            this.id = therapistBO.getLastPK().orElse("Error");
            lblTherapistId.setText(this.id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Refresh error: " + e.getMessage()).show();
        }
    }
}