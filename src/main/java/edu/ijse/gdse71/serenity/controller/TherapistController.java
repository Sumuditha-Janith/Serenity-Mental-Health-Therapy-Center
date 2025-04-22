package edu.ijse.gdse71.serenity.controller;

import edu.ijse.gdse71.serenity.bo.BOFactory;
import edu.ijse.gdse71.serenity.bo.custom.impl.TherapistBOImpl;
import edu.ijse.gdse71.serenity.bo.custom.impl.TherapyProgramBOImpl;
import edu.ijse.gdse71.serenity.dto.TherapistDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private Label errorMessage;

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
        String id = this.id;
        String name = txtName.getText();
        String specialization = specializationChoice.getValue();

        if(name.isEmpty() || specialization.isEmpty()){
            errorMessage.setText("Please fill all the fields");
            return;
        }

        TherapistDTO therapistDTO = new TherapistDTO();
        therapistDTO.setId(id);
        therapistDTO.setName(name);
        therapistDTO.setSpecialization(specialization);

        boolean isAdded = therapistBO.save(therapistDTO);

        if(isAdded){
            txtName.clear();
            specializationChoice.setValue(null);
            this.id = String.valueOf(therapistBO.getLastPK().orElse("Error"));
            loadTherapistTable();
        }else{
            errorMessage.setText("Failed to add therapist");
        }
    }

    @FXML
    void deleteTherapist(ActionEvent event) throws Exception {
        String id = lblTherapistId.getText();
        boolean isDeleted = therapistBO.deleteByPK(id);

        if (isDeleted) {
            txtName.clear();
            specializationChoice.setValue(null);
            specializationChoice.setDisable(false);
            lblTherapistId.setText(id);
            loadTherapistTable();
        } else {
            System.out.println("Failed to delete therapist");
        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        txtName.clear();
        specializationChoice.setValue(null);
        btnAdd.setDisable(false);
        specializationChoice.setDisable(false);
        lblTherapistId.setText(id);
    }

    @FXML
    void onClickTherapistTable(MouseEvent event) {
        TherapistDTO selectedTherapist = tblTherapists.getSelectionModel().getSelectedItem();
        if (selectedTherapist != null) {
            lblTherapistId.setText(selectedTherapist.getId());
            txtName.setText(selectedTherapist.getName());
            specializationChoice.setValue(selectedTherapist.getSpecialization());
            btnAdd.setDisable(true);
        }
    }

    @FXML
    void updateTherapist(ActionEvent event) {
        String id = lblTherapistId.getText();
        String name = txtName.getText();
        specializationChoice.setDisable(true);
        String specialization = specializationChoice.getValue();

        if(name.isEmpty() || specialization.isEmpty()){
            errorMessage.setText("Please fill all the fields");
            return;
        }

        TherapistDTO therapistDTO = new TherapistDTO();
        therapistDTO.setId(id);
        therapistDTO.setName(name);
        therapistDTO.setSpecialization(specialization);

        boolean isUpdated = therapistBO.update(therapistDTO);

        if(isUpdated){
            txtName.clear();
            specializationChoice.setValue(null);
            specializationChoice.setDisable(false);
            loadTherapistTable();
        }else{
            errorMessage.setText("Failed to update therapist");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.id = String.valueOf(therapistBO.getLastPK().orElse("Error"));
        lblTherapistId.setText(id);

        ArrayList<String> programList = therapyProgramBO.getProgramList();
        System.out.println(programList);
        specializationChoice.getItems().addAll(programList);

        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colSpecialization.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialization()));

        loadTherapistTable();
    }

    private void loadTherapistTable() {
        List<TherapistDTO> therapistList = therapistBO.getAll();
        ObservableList<TherapistDTO> therapistTMS = FXCollections.observableArrayList(therapistList);
        tblTherapists.setItems(therapistTMS);
    }
}
