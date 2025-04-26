package edu.ijse.gdse71.serenity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent load = FXMLLoader.load((getClass().getResource("/view/LogInScreen.fxml")));
        stage.setScene(new Scene(load));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
        stage.setTitle("Serenity Mental Health Therapy Center");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Working... Please wait...");
        launch();
    }

}