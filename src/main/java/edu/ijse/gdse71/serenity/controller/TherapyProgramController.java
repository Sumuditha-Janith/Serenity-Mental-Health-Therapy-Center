package edu.ijse.gdse71.serenity.controller;

import edu.ijse.gdse71.serenity.bo.BOFactory;
import edu.ijse.gdse71.serenity.bo.custom.impl.TherapyProgramBOImpl;
import edu.ijse.gdse71.serenity.dto.TherapyProgramDTO;
import edu.ijse.gdse71.serenity.exeception.RequiredFieldException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TherapyProgramController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TherapyProgramDTO, String> colDuration;

    @FXML
    private TableColumn<TherapyProgramDTO, Double> colFee;

    @FXML
    private TableColumn<TherapyProgramDTO, String> colName;

    @FXML
    private TableColumn<TherapyProgramDTO, String> colProgramId;

    @FXML
    private Label lblProgramId;

    @FXML
    private ChoiceBox<String> selectTime;

    @FXML
    private TableView<TherapyProgramDTO> tblTherapyPrograms;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtName;

    private final TherapyProgramBOImpl therapyProgramBO = (TherapyProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    private String id;

    @FXML
    void addTherapyProgram(ActionEvent event) {
        try {
            String name = txtName.getText().trim();
            String duration = txtDuration.getText().trim();
            String timeUnit = selectTime.getValue();
            String fee = txtFee.getText().trim();

            if (name.isEmpty()) {
                throw new RequiredFieldException("program name");
            }
            if (duration.isEmpty()) {
                throw new RequiredFieldException("duration");
            }
            if (timeUnit == null) {
                throw new RequiredFieldException("time unit");
            }
            if (fee.isEmpty()) {
                throw new RequiredFieldException("fee");
            }

            if (!duration.matches("^\\d+$")) {
                throw new Exception("Duration must be a number");
            }

            if (!fee.matches("^\\d+(\\.\\d{1,2})?$")) {
                throw new Exception("Please enter a valid amount");
            }

            TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO();
            therapyProgramDTO.setProgramId(this.id);
            therapyProgramDTO.setName(name);
            therapyProgramDTO.setDuration(duration + " " + timeUnit);
            therapyProgramDTO.setFee(Double.parseDouble(fee));

            boolean isSaved = therapyProgramBO.save(therapyProgramDTO);

            if (!isSaved) {
                throw new Exception("Failed to save therapy program");
            }

            new Alert(Alert.AlertType.INFORMATION, "Therapy program added successfully").show();
            refreshPage();
            this.id = therapyProgramBO.getLastPK().orElse("Error");
            lblProgramId.setText(this.id);
            loadTherapyProgramTable();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void deleteTherapyProgram(ActionEvent event) {
        try {
            String id = lblProgramId.getText();
            if (id == null || id.isEmpty()) {
                throw new Exception("No therapy program selected");
            }

            boolean isDeleted = therapyProgramBO.deleteByPK(id);
            if (!isDeleted) {
                throw new Exception("You cannot delete therapy programs associated in the Appointments table");
            }

            new Alert(Alert.AlertType.INFORMATION, "Therapy program deleted successfully").show();
            refreshPage();
            loadTherapyProgramTable();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void onClickTherapyProgramTable(MouseEvent event) {
        try {
            TherapyProgramDTO selectedProgram = tblTherapyPrograms.getSelectionModel().getSelectedItem();
            if (selectedProgram == null) return;

            lblProgramId.setText(selectedProgram.getProgramId());
            txtName.setText(selectedProgram.getName());

            String[] durationParts = selectedProgram.getDuration().split(" ");
            txtDuration.setText(durationParts[0]);
            selectTime.setValue(durationParts[1]);

            txtFee.setText(String.valueOf(selectedProgram.getFee()));

            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading therapy program: " + e.getMessage()).show();
        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void updateTherapyProgram(ActionEvent event) {
        try {
            String id = lblProgramId.getText();
            String name = txtName.getText().trim();
            String duration = txtDuration.getText().trim();
            String timeUnit = selectTime.getValue();
            String fee = txtFee.getText().trim();

            if (name.isEmpty()) {
                throw new RequiredFieldException("program name");
            }
            if (duration.isEmpty()) {
                throw new RequiredFieldException("duration");
            }
            if (timeUnit == null) {
                throw new RequiredFieldException("time unit");
            }
            if (fee.isEmpty()) {
                throw new RequiredFieldException("fee");
            }

            if (!duration.matches("^\\d+$")) {
                throw new Exception("Duration must be a whole number");
            }

            if (!fee.matches("^\\d+(\\.\\d{1,2})?$")) {
                throw new Exception("Invalid fee format. Please enter a valid amount");
            }

            TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO();
            therapyProgramDTO.setProgramId(id);
            therapyProgramDTO.setName(name);
            therapyProgramDTO.setDuration(duration + " " + timeUnit);
            therapyProgramDTO.setFee(Double.parseDouble(fee));

            boolean isUpdated = therapyProgramBO.update(therapyProgramDTO);

            if (!isUpdated) {
                throw new Exception("Failed to update therapy program");
            }

            new Alert(Alert.AlertType.INFORMATION, "Therapy program updated successfully").show();
            refreshPage();
            loadTherapyProgramTable();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            selectTime.getItems().addAll("Weeks", "Months", "Years");
            this.id = therapyProgramBO.getLastPK().orElse("Error");
            lblProgramId.setText(this.id);

            colProgramId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProgramId()));
            colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            colDuration.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuration()));
            colFee.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFee()));

            loadTherapyProgramTable();
            refreshPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Initialization error: " + e.getMessage()).show();
        }
    }

    private void loadTherapyProgramTable() {
        try {
            List<TherapyProgramDTO> programList = therapyProgramBO.getAll();
            ObservableList<TherapyProgramDTO> programTMS = FXCollections.observableArrayList(programList);
            tblTherapyPrograms.setItems(programTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load therapy programs: " + e.getMessage()).show();
        }
    }

    private void refreshPage() {
        try {
            txtName.clear();
            txtDuration.clear();
            txtFee.clear();
            selectTime.setValue(null);
            this.id = therapyProgramBO.getLastPK().orElse("Error");
            lblProgramId.setText(this.id);

            btnAdd.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            tblTherapyPrograms.getSelectionModel().clearSelection();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error refreshing form: " + e.getMessage()).show();
        }
    }
}