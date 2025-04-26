package edu.ijse.gdse71.serenity.controller;

import edu.ijse.gdse71.serenity.bo.BOFactory;
import edu.ijse.gdse71.serenity.bo.custom.impl.PaymentBOImpl;
import edu.ijse.gdse71.serenity.bo.custom.impl.TherapySessionBOImpl;
import edu.ijse.gdse71.serenity.dto.PaymentDTO;
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

public class PaymentController implements Initializable {

    @FXML private Button btnProcess;
    @FXML private Button btnReset;
    @FXML private TableColumn<PaymentDTO, String> colAmount;
    @FXML private TableColumn<PaymentDTO, String> colDate;
    @FXML private TableColumn<PaymentDTO, String> colPatient;
    @FXML private TableColumn<PaymentDTO, String> colPaymentId;
    @FXML private TableColumn<PaymentDTO, String> colSession;
    @FXML private TableColumn<PaymentDTO, String> colStatus;
    @FXML private Label lblPaymentId;
    @FXML private TableView<PaymentDTO> tblPayments;
    @FXML private Label lblAmount;
    @FXML private Label lblDate;
    @FXML private Label lblSession;
    @FXML private Label lblPatient;

    private final PaymentBOImpl paymentBO = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    @FXML
    void paymentSelectOnAction(MouseEvent event) {
        try {
            PaymentDTO selectedPayment = tblPayments.getSelectionModel().getSelectedItem();
            if (selectedPayment == null) return;

            lblPaymentId.setText(selectedPayment.getId());
            lblAmount.setText(String.valueOf(selectedPayment.getAmount()));
            lblDate.setText(String.valueOf(selectedPayment.getDate()));
            lblSession.setText(selectedPayment.getTherapySession().getId());
            lblPatient.setText(selectedPayment.getPatient().getId());
            btnProcess.setDisable(false);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error selecting payment: " + e.getMessage()).show();
        }
    }

    @FXML
    void processPayment(ActionEvent event) {
        try {
            PaymentDTO selectedPayment = tblPayments.getSelectionModel().getSelectedItem();
            if (selectedPayment == null) {
                throw new Exception("Please select a payment to process");
            }

            boolean paymentUpdated = paymentBO.updatePaymentStatus(
                    selectedPayment.getId(), "PAID");

            if (!paymentUpdated) {
                throw new Exception("Failed to update payment status");
            }

            TherapySessionBOImpl therapySessionBO = (TherapySessionBOImpl) BOFactory.getInstance()
                    .getBO(BOFactory.BOType.THERAPY_SESSION);

            boolean sessionUpdated = therapySessionBO.updateSessionStatus(
                    selectedPayment.getTherapySession().getId(), "COMPLETED");

            if (!sessionUpdated) {
                throw new Exception("Failed to update therapy session status");
            }

            new Alert(Alert.AlertType.INFORMATION, "Payment processed successfully!").show();
            loadPatientTable();
            refreshForm();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        refreshForm();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            colPaymentId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
            colAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAmount())));
            colDate.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDate())));
            colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
            colPatient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getId()));
            colSession.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTherapySession().getId()));

            loadPatientTable();
            refreshForm();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Initialization error: " + e.getMessage()).show();
        }
    }

    private void loadPatientTable() {
        try {
            List<PaymentDTO> paymentList = paymentBO.getAll();
            ObservableList<PaymentDTO> paymentTMS = FXCollections.observableArrayList(paymentList);
            tblPayments.setItems(paymentTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load payments: " + e.getMessage()).show();
        }
    }

    private void refreshForm() {
        try {
            lblPaymentId.setText("");
            lblAmount.setText("");
            lblDate.setText("");
            lblSession.setText("");
            lblPatient.setText("");
            btnProcess.setDisable(true);
            tblPayments.getSelectionModel().clearSelection();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error refreshing form: " + e.getMessage()).show();
        }
    }
}