package com.example.linearequations;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Equations Solver!");

            // For Image as an icon
            Image icon = new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("icon.jpg")));
            stage.getIcons().add(icon);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("ERROR: " + e);
        }
    }

    public static void main(String[] args) {
        // Launch?!
        launch();
    }
}