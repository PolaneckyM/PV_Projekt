package com.example.polanecky;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TablesController {

    // Reference to the ChoiceBox for selecting a table
    @FXML
    private ChoiceBox<String> tableChoiceBox;

    // Reference to the TableView for displaying table data
    @FXML
    private TableView<Object[]> tableView;

    // Variable for the database connection
    private Connection connection;

    // Initialization method called when the controller is loaded
    public void initialize() {
        loadDBPropertiesFromCSV(); // Load database connection properties from a CSV file
        populateTableChoiceBox(); // Populate the ChoiceBox with table names from the database
    }

    // Method to load database connection properties from a CSV file
    private void loadDBPropertiesFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/polanecky/DBConnectionData.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String url = data[0].trim();
                String username = data[1].trim();

                // Establishing a connection to the database
                connection = DriverManager.getConnection(url, username, "");
                break; // Break after the first valid connection data
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Print the stack trace if an error occurs
        }
    }

    // Method to populate the ChoiceBox with table names from the database
    private void populateTableChoiceBox() {
        List<String> tableNames = getTableNames(); // Get a list of table names
        tableChoiceBox.getItems().addAll(tableNames); // Add table names to the ChoiceBox
    }

    // Method to get the list of table names from the database
    private List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData(); // Get metadata about the database
            String schema = connection.getCatalog(); // Get the current schema/catalog
            ResultSet resultSet = metaData.getTables(schema, null, "%", new String[]{"TABLE"}); // Get all table names

            while (resultSet.next()) {
                tableNames.add(resultSet.getString("TABLE_NAME")); // Add each table name to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if an error occurs
        }
        return tableNames;
    }

    // Method called when a table is selected from the ChoiceBox
    @FXML
    private void onTableSelected() {
        String selectedTable = tableChoiceBox.getValue(); // Get the selected table name
        if (selectedTable != null) {
            populateTableView(selectedTable); // Populate the TableView with data from the selected table
        }
    }

    // Method to populate the TableView with data from the specified table
    private void populateTableView(String tableName) {
        tableView.getColumns().clear(); // Clear existing columns
        tableView.getItems().clear(); // Clear existing items

        try {
            Statement statement = connection.createStatement(); // Create a new SQL statement
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName); // Execute query to get all data from the table
            int columnCount = resultSet.getMetaData().getColumnCount(); // Get the number of columns

            // Add columns to the TableView
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSet.getMetaData().getColumnName(i); // Get the column name
                TableColumn<Object[], Object> column = new TableColumn<>(columnName); // Create a new TableColumn
                final int columnIndex = i - 1; // Column index in the Object array
                column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()[columnIndex])); // Set cell value factory
                tableView.getColumns().add(column); // Add the column to the TableView
            }

            // Add rows to the TableView
            while (resultSet.next()) {
                Object[] row = new Object[columnCount]; // Create a new array for the row data
                for (int i = 0; i < columnCount; i++) {
                    row[i] = resultSet.getObject(i + 1); // Get the value for each column
                }
                tableView.getItems().add(row); // Add the row to the TableView
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if an error occurs
        }
    }
}
