package edu.ijse.gdse71.serenity.controller;

import edu.ijse.gdse71.serenity.bo.BOFactory;
import edu.ijse.gdse71.serenity.bo.custom.impl.UserBOImpl;
import edu.ijse.gdse71.serenity.dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpScreenController implements Initializable {

    @FXML
    private Button btSignUp;

    @FXML
    private ChoiceBox<String> choiceRole;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    private final UserBOImpl userBO = (UserBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void navLogInPage(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = choiceRole.getValue();
        String confirmPassword = txtConfirmPassword.getText();

        String lastId = userBO.getLastPK().orElse("U001");

        System.out.println(lastId);
        System.out.println(userName);
        System.out.println(password);
        System.out.println(role);
        System.out.println(confirmPassword);

        if(userName.isEmpty() || password.isEmpty() || role == null || role.isEmpty() || confirmPassword.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all the fields");
            return;
        }

        if(password.length() < 8){
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 8 characters");
            return;
        }

        if(!password.equals(confirmPassword)){
            showAlert(Alert.AlertType.ERROR, "Error", "Password does not match");
            return;
        }

        if(userBO.checkUser(userName)){
            showAlert(Alert.AlertType.ERROR, "Error", "User already exists");
            return;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(lastId);
        userDTO.setUsername(userName);
        userDTO.setPassword(password);
        userDTO.setRole(role);

        boolean result = userBO.save(userDTO);

        if(result){
            showAlert(Alert.AlertType.INFORMATION, "Success", "User created successfully!");
            mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LogInScreen.fxml")));
        }else{
            showAlert(Alert.AlertType.ERROR, "Error", "User sign up failed");
            return;
        }
    }

    @FXML
    void navBackToLogInPage(MouseEvent event) throws IOException {
        mainAnchor.getChildren().clear();
        mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LogInScreen.fxml")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceRole.getItems().addAll("Admin", "Receptionist");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}