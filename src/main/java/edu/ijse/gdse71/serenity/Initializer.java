package edu.ijse.gdse71.serenity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Initializer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent load = FXMLLoader.load((getClass().getResource("/view/LogInScreen.fxml")));
        stage.setScene(new Scene(load));
        stage.setTitle("Center");
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        launch();
    }

}