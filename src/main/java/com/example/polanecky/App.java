package com.example.polanecky;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

// Main application class extending JavaFX Application
public class App extends Application {

    // The start method is the main entry point for all JavaFX applications
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file and initialize the scene graph
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("app.fxml")));

        // Set the title of the primary stage (window)
        primaryStage.setTitle("DatabaseProject");

        // Set the scene with the specified width and height
        primaryStage.setScene(new Scene(root, 800, 600));

        // Display the primary stage
        primaryStage.show();
    }

    // The main method that launches the JavaFX application
    public static void main(String[] args) {
        launch(args); // This method is inherited from Application class and starts the JavaFX runtime.
    }
}
