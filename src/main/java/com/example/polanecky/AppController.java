package com.example.polanecky;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AppController {

    // References to FXML components
    @FXML
    private Button insertButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button importCSVButton;

    @FXML
    private Button importXMLButton;

    @FXML
    private Button importJSONButton;

    // Handler method for insert button click
    @FXML
    public void handleInsert() {
        try {
            // Load the insert.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("insert.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML
            Scene scene = new Scene(root);

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Insert Data"); // Set the title of the window
            stage.setScene(scene); // Set the scene to the stage

            // Make the new window modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the new window and wait until it is closed
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an error occurs
        }
    }

    // Handler method for delete button click
    @FXML
    public void handleDelete() {
        try {
            // Load the delete.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("delete.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML
            Scene scene = new Scene(root);

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Delete Data"); // Set the title of the window
            stage.setScene(scene); // Set the scene to the stage

            // Make the new window modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the new window and wait until it is closed
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an error occurs
        }
    }

    // Handler method to show tables button click
    @FXML
    public void showTables() {
        try {
            // Load the Tables.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Tables.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML
            Scene scene = new Scene(root);

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Show Tables"); // Set the title of the window
            stage.setScene(scene); // Set the scene to the stage

            // Make the new window modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the new window and wait until it is closed
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an error occurs
        }
    }

    // Helper method to show an alert dialog
    private void showAlert(String title, String headerText, String contentText) {
        // Create a new alert of type information
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title); // Set the title of the alert
        alert.setHeaderText(headerText); // Set the header text of the alert
        alert.setContentText(contentText); // Set the content text of the alert
        alert.showAndWait(); // Show the alert and wait until it is closed
    }
}
