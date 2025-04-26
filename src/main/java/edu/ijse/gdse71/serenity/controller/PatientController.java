package edu.ijse.gdse71.serenity.controller;

import edu.ijse.gdse71.serenity.bo.BOFactory;
import edu.ijse.gdse71.serenity.bo.custom.impl.PatientBOImpl;
import edu.ijse.gdse71.serenity.dto.PatientDTO;
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

public class PatientController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private TableColumn<PatientDTO, String> colContact;

    @FXML
    private TableColumn<PatientDTO, String> colGender;

    @FXML
    private TableColumn<PatientDTO, String> colId;

    @FXML
    private TableColumn<PatientDTO, String> colName;

    @FXML
    private TableColumn<PatientDTO, String> colRegDate;

    @FXML
    private Label dpRegDate;

    @FXML
    private Label lblPatientId;

    @FXML
    private TableView<PatientDTO> tblPatients;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    private final PatientBOImpl patientBO = (PatientBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    private String id;

    @FXML
    void addPatient(ActionEvent event) {
        try {
            String name = txtName.getText().trim();
            String contact = txtContact.getText().trim();
            String gender = cmbGender.getValue();
            String regDate = dpRegDate.getText();

            if (name.isEmpty() || contact.isEmpty() || gender == null || regDate.isEmpty()) {
                throw new Exception("Please fill all the fields");
            }

            if (!contact.matches("^07\\d{8}$")) {
                throw new Exception("Invalid contact number. Must start with 07 and have 10 digits");
            }

            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setId(this.id);
            patientDTO.setName(name);
            patientDTO.setContactInfo(contact);
            patientDTO.setGender(gender);
            patientDTO.setBirthDate(regDate);

            boolean isSaved = patientBO.save(patientDTO);

            if (!isSaved) {
                throw new Exception("Failed to save patient");
            }

            new Alert(Alert.AlertType.INFORMATION, "Patient added successfully").show();
            refreshPage();
            this.id = patientBO.getLastPK().orElse("Error");
            lblPatientId.setText(this.id);
            loadPatientTable();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void deletePatient(ActionEvent event) {
        try {
            String id = lblPatientId.getText();
            if (id == null || id.isEmpty()) {
                throw new Exception("No patient selected");
            }

            boolean isDeleted = patientBO.deleteByPK(id);
            if (!isDeleted) {
                throw new Exception("You cannot delete patients who have Pending/Completed appointments.");
            }

            new Alert(Alert.AlertType.INFORMATION, "Patient deleted successfully").show();
            refreshPage();
            loadPatientTable();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void onClickPatientTable(MouseEvent event) {
        try {
            PatientDTO selectedPatient = tblPatients.getSelectionModel().getSelectedItem();
            if (selectedPatient == null) return;

            lblPatientId.setText(selectedPatient.getId());
            txtName.setText(selectedPatient.getName());
            txtContact.setText(selectedPatient.getContactInfo());
            cmbGender.setValue(selectedPatient.getGender());
            dpRegDate.setText(selectedPatient.getBirthDate());

            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            dpRegDate.setDisable(true);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading patient: " + e.getMessage()).show();
        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void updatePatient(ActionEvent event) {
        try {
            String id = lblPatientId.getText();
            String name = txtName.getText().trim();
            String contact = txtContact.getText().trim();
            String gender = cmbGender.getValue();
            String regDate = dpRegDate.getText();

            if (name.isEmpty() || contact.isEmpty() || gender == null || regDate.isEmpty()) {
                throw new Exception("Please fill all the fields");
            }

            if (!contact.matches("^07\\d{8}$")) {
                throw new Exception("Invalid contact number. Must start with 07 and have 10 digits");
            }

            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setId(id);
            patientDTO.setName(name);
            patientDTO.setContactInfo(contact);
            patientDTO.setGender(gender);
            patientDTO.setBirthDate(regDate);

            boolean isUpdated = patientBO.update(patientDTO);

            if (!isUpdated) {
                throw new Exception("Failed to update patient");
            }

            new Alert(Alert.AlertType.INFORMATION, "Patient updated successfully").show();
            refreshPage();
            loadPatientTable();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dpRegDate.setText(LocalDate.now().toString());
            cmbGender.getItems().addAll("Male", "Female");
            this.id = patientBO.getLastPK().orElse("Error");
            lblPatientId.setText(this.id);

            colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
            colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            colContact.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactInfo()));
            colGender.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
            colRegDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthDate()));

            loadPatientTable();
            refreshPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Initialization error: " + e.getMessage()).show();
        }
    }

    private void loadPatientTable() {
        try {
            List<PatientDTO> patientList = patientBO.getAll();
            ObservableList<PatientDTO> patientTMS = FXCollections.observableArrayList(patientList);
            tblPatients.setItems(patientTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load patients: " + e.getMessage()).show();
        }
    }

    private void refreshPage() {
        try {
            txtName.clear();
            txtContact.clear();
            cmbGender.setValue(null);
            dpRegDate.setText(LocalDate.now().toString());
            this.id = patientBO.getLastPK().orElse("Error");
            lblPatientId.setText(this.id);

            btnAdd.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            dpRegDate.setDisable(false);

            tblPatients.getSelectionModel().clearSelection();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error refreshing form: " + e.getMessage()).show();
        }
    }
}