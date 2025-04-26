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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private Button btnProcess;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<PaymentDTO, String> colAmount;

    @FXML
    private TableColumn<PaymentDTO, String> colDate;

    @FXML
    private TableColumn<PaymentDTO, String> colPatient;

    @FXML
    private TableColumn<PaymentDTO, String> colPaymentId;

    @FXML
    private TableColumn<PaymentDTO, String> colSession;

    @FXML
    private TableColumn<PaymentDTO, String> colStatus;

    @FXML
    private Label errorMessage;

    @FXML
    private Label lblPaymentId;

    @FXML
    private TableView<PaymentDTO> tblPayments;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblSession;

    @FXML
    private Label lblPatient;

    private final PaymentBOImpl paymentBO = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    @FXML
    void paymentSelectOnAction(MouseEvent event) {
        PaymentDTO selectedPayment = tblPayments.getSelectionModel().getSelectedItem();
        if (selectedPayment != null) {
            lblPaymentId.setText(selectedPayment.getId());
            lblAmount.setText(String.valueOf(selectedPayment.getAmount()));
            lblDate.setText(String.valueOf(selectedPayment.getDate()));
            lblSession.setText(selectedPayment.getTherapySession().getId());
            lblPatient.setText(selectedPayment.getPatient().getId());
        }
    }

    @FXML
    void processPayment(ActionEvent event) {
        PaymentDTO selectedPayment = tblPayments.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            try {
                boolean paymentUpdated = paymentBO.updatePaymentStatus(
                        selectedPayment.getId(), "PAID");

                if (paymentUpdated) {
                    TherapySessionBOImpl therapySessionBO = (TherapySessionBOImpl) BOFactory.getInstance()
                            .getBO(BOFactory.BOType.THERAPY_SESSION);

                    boolean sessionUpdated = therapySessionBO.updateSessionStatus(
                            selectedPayment.getTherapySession().getId(), "COMPLETED");

                    if (sessionUpdated) {
                        loadPatientTable();
                        errorMessage.setText("Payment processed successfully!");
                    } else {
                        errorMessage.setText("Failed to update therapy session status");
                    }
                } else {
                    errorMessage.setText("Failed to update payment status");
                }
            } catch (Exception e) {
                errorMessage.setText("Error processing payment: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            errorMessage.setText("Please select a payment to process.");
        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        lblPaymentId.setText("");
        lblAmount.setText("");
        lblDate.setText("");
        lblSession.setText("");
        lblPatient.setText("");
        errorMessage.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colPaymentId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAmount())));
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDate())));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colPatient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getId()));
        colSession.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTherapySession().getId()));
        loadPatientTable();
    }

    private void loadPatientTable() {
        List<PaymentDTO> paymentList = paymentBO.getAll();
        ObservableList<PaymentDTO> paymentTMS = FXCollections.observableArrayList(paymentList);
        tblPayments.setItems(paymentTMS);
    }
}
