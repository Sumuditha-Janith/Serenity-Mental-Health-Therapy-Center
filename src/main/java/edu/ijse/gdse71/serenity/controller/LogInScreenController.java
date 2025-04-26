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
        String userName = txtUsername.getText();
        String password = txtPassword.getText();

        if(userName.isEmpty() || password.isEmpty()){
            System.out.println("Empty");
            return;
        }

        boolean result = userBO.checkUser(userName);

        if(result){
            UserDTO userDTO = userBO.checkPassword(userName);

            String role = userDTO.getRole();
            String hashedDTO = userDTO.getPassword();

            System.out.println("In controller" + hashedDTO);
            System.out.println(role);

            boolean isPasswordValid = PasswordUtils.verifyPassword(password, hashedDTO);

            if(!isPasswordValid){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                resetFields();
                alert.setContentText("Invalid Password");
                alert.show();
            }else {
                if(role.equals("Admin")){
                    mainAnchor.getChildren().clear();
                    mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml")));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Logged In");
                    alert.setHeaderText("Logged In");
                    alert.setContentText("You Are Now Logged In As Admin");
                    alert.showAndWait();

                }else if(role.equals("Receptionist")){
                    mainAnchor.getChildren().clear();
                    mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/ReceptionistDashboard.fxml")));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Logged In");
                    alert.setHeaderText("Logged In");
                    alert.setContentText("You Are Now Logged In As Receptionist");
                    alert.showAndWait();
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            resetFields();
            alert.setContentText("Invalid Username");
            alert.showAndWait();
        }
    }

    @FXML
    void navSignUp(MouseEvent event) throws IOException {
        mainAnchor.getChildren().clear();
        mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/SignUpScreen.fxml")));
    }

    private void resetFields() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

}
