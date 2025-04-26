package edu.ijse.gdse71.serenity.controller;

import edu.ijse.gdse71.serenity.bo.BOFactory;
import edu.ijse.gdse71.serenity.bo.custom.impl.UserBOImpl;
import edu.ijse.gdse71.serenity.dto.UserDTO;
import edu.ijse.gdse71.serenity.util.PasswordUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.regex.Pattern;

public class LogInScreenController {

    @FXML
    private Button btSignIn;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private final UserBOImpl userBO = (UserBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void navHomePage(ActionEvent event) throws IOException {
        String userName = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (userName.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username and password cannot be empty");
            txtUsername.requestFocus();
            return;
        }

        if (userName.length() < 3) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username must be at least 3 characters long");
            txtUsername.requestFocus();
            return;
        }

        if (!Pattern.matches("^[a-zA-Z0-9_]+$", userName)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username can only contain letters, numbers and underscores");
            txtUsername.requestFocus();
            return;
        }

        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 8 characters long");
            txtPassword.requestFocus();
            return;
        }

        try {
            boolean userExists = userBO.checkUser(userName);

            if (!userExists) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid username or password");
                resetFields();
                txtUsername.requestFocus();
                return;
            }

            UserDTO userDTO = userBO.checkPassword(userName);
            String role = userDTO.getRole();
            String hashedPassword = userDTO.getPassword();

            boolean isPasswordValid = PasswordUtils.verifyPassword(password, hashedPassword);

            if (!isPasswordValid) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid username or password");
                resetFields();
                txtUsername.requestFocus();
                return;
            }

            String dashboardFxml = role.equals("Admin") ? "/view/AdminDashboard.fxml" : "/view/ReceptionistDashboard.fxml";
            String roleName = role.equals("Admin") ? "Admin" : "Receptionist";

            mainAnchor.getChildren().clear();
            mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource(dashboardFxml)));

            showAlert(Alert.AlertType.INFORMATION, "Success", "You are now logged in as " + roleName);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during login: " + e.getMessage());
            resetFields();
        }
    }

    @FXML
    void navSignUp(MouseEvent event) throws IOException {
        mainAnchor.getChildren().clear();
        mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/SignUpScreen.fxml")));
    }

    private void resetFields() {
        txtPassword.setText("");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}