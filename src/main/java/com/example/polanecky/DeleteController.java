package com.example.polanecky;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class DeleteController {

    String selectedTable;

    @FXML
    private MenuButton menuButton;

    @FXML
    private VBox oddeleniContainer;

    @FXML
    private VBox objednavkaContainer;

    @FXML
    private VBox zakaznikContainer;

    @FXML
    private VBox zamestnanecContainer;

    @FXML
    private VBox produktContainer;

    private Connection connection;

    // Method that connects to the database server
    // gets the information of data needed to login to database server from file
    // located in the file DBConnectionData.csv
    private void loadDBPropertiesFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/polanecky/DBConnectionData.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String url = data[0].trim();
                String username = data[1].trim();
                connection = DriverManager.getConnection(url, username, "");
                break;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods for showing forms in the delete.fxml
    @FXML
    public void showOddeleniForm() {
        oddeleniContainer.setVisible(true);
        oddeleniContainer.setManaged(true);
    }

    @FXML
    void showObjednavkaForm() {
        objednavkaContainer.setVisible(true);
        objednavkaContainer.setManaged(true);
    }

    @FXML
    public void showZakaznikForm() {
        zakaznikContainer.setVisible(true);
        zakaznikContainer.setManaged(true);
    }

    @FXML
    public void showZamestnanecForm() {
        zamestnanecContainer.setVisible(true);
        zamestnanecContainer.setManaged(true);
    }

    @FXML
    public void showProduktForm() {
        produktContainer.setVisible(true);
        produktContainer.setManaged(true);
    }

    @FXML
    public void hideForms() {
        produktContainer.setVisible(false);
        produktContainer.setManaged(false);
        zamestnanecContainer.setVisible(false);
        zamestnanecContainer.setManaged(false);
        zakaznikContainer.setVisible(false);
        zakaznikContainer.setManaged(false);
        objednavkaContainer.setVisible(false);
        objednavkaContainer.setManaged(false);
        oddeleniContainer.setVisible(false);
        oddeleniContainer.setManaged(false);
    }

    @FXML
    private ChoiceBox<String> choiceBox;

    // methods that checks what user selected in the choicebox and starts the method
    // that will show the form by choice of the user
    @FXML
    public void handleSelection() {
        String selectedTable = choiceBox.getValue();
        if (selectedTable != null) {
            switch (selectedTable) {
                case "Oddeleni":
                    hideForms();
                    showOddeleniForm();

                    break;
                case "Objednavka":
                    hideForms();
                    showObjednavkaForm();
                    break;
                case "Zakaznik":
                    hideForms();
                    showZakaznikForm();
                    break;
                case "Zamestnanec":
                    hideForms();
                    showZamestnanecForm();
                    break;
                case "Produkt":
                    hideForms();
                    showProduktForm();
                    break;
                default:
                    System.out.println("Není vybrána žádná akce.");
            }
        } else {
            System.out.println("Není vybrána žádná tabulka.");
        }
    }

    public void initialize() {
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            handleSelection();
        });
        loadDBPropertiesFromCSV();

    }

    @FXML
    private TextField oddeleniNazev;

    @FXML
    private TextField oddeleniPopis;

    // Methods for removing data from tables based on if a table with data that user
    // declared exists and deletes it
    @FXML
    public void removeFromOddeleni() throws SQLException {
        String nazev = oddeleniNazev.getText();
        String popis = oddeleniPopis.getText();

        if (nazev.isEmpty() && popis.isEmpty()) {
            if (nazev.isEmpty()) {
                oddeleniNazev.setText("You forgot to fill me!");

            } else if (popis.isEmpty()) {
                oddeleniPopis.setText("You forgot to fill me !");
            }
            return;
        } else {

            String query = "DELETE FROM Oddeleni WHERE nazev = ? OR popis = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nazev);
            preparedStatement.setString(2, popis);

            int deletedRows = preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (deletedRows > 0) {
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Oddeleni bylo úspěšně odstraněno z databáze.");
            } else {
                alert.setTitle("Failure");
                alert.setHeaderText(null);
                alert.setContentText("Oddeleni nebylo nalezeno nebo nebylo odstraněn.");
            }
            alert.showAndWait();

        }
    }

    @FXML
    private TextField zakaznikId;
    @FXML
    private TextField zakaznikJmeno;
    @FXML
    private TextField zakaznikPrijmeni;
    @FXML
    private TextField zakaznikEmail;
    @FXML
    private DatePicker zakaznikDatumReg;

    @FXML
    public void removeFromZakaznik() throws SQLException {
        String id = zakaznikId.getText();
        String jmeno = zakaznikJmeno.getText();
        String prijmeni = zakaznikPrijmeni.getText();
        String email = zakaznikEmail.getText();
        String datumReg = "";
        if(zakaznikDatumReg.getValue() != null) datumReg = zakaznikDatumReg.getValue().toString();


        StringBuilder queryBuilder = new StringBuilder("DELETE FROM Zakaznik WHERE ");
        boolean hasConditions = false;

        if (!id.isEmpty()) {
            queryBuilder.append("id = ?");
            hasConditions = true;
        }
        if (!jmeno.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" AND ");
            queryBuilder.append("jmeno = ?");
            hasConditions = true;
        }
        if (!prijmeni.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" AND ");
            queryBuilder.append("prijmeni = ?");
            hasConditions = true;
        }
        if (!email.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" AND ");
            queryBuilder.append("email = ?");
            hasConditions = true;
        }
        if (!datumReg.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" AND ");
            queryBuilder.append("datum_registrace = ?");
        }

        PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
        int parameterIndex = 1;
        if (!id.isEmpty()) {
            preparedStatement.setString(parameterIndex++, id);
        }
        if (!jmeno.isEmpty()) {
            preparedStatement.setString(parameterIndex++, jmeno);
        }
        if (!prijmeni.isEmpty()) {
            preparedStatement.setString(parameterIndex++, prijmeni);
        }
        if (!email.isEmpty()) {
            preparedStatement.setString(parameterIndex++, email);
        }
        if (!datumReg.isEmpty()) {
            preparedStatement.setString(parameterIndex, datumReg);
        }

        int deletedRows = preparedStatement.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (deletedRows > 0) {
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Zakaznik byl úspěšně odstraněn z databáze.");
        } else {
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("Zakaznik nebyl nalezen nebo nebyl odstraněn.");
        }
        alert.showAndWait();

    }

    @FXML
    private TextField produktId;
    @FXML
    private TextField produktNazev;
    @FXML
    private TextField produktCena;
    @FXML
    private CheckBox produktSkladem;

    @FXML
    public void removeFromProdukt() throws SQLException {
        String id = produktId.getText();
        String nazev = produktNazev.getText();
        String cenaText = produktCena.getText();
        boolean skladem = produktSkladem.isSelected();

        StringBuilder queryBuilder = new StringBuilder("DELETE FROM Produkt WHERE ");
        boolean hasConditions = false;

        if (!id.isEmpty()) {
            queryBuilder.append("id = ?");
            hasConditions = true;
        }
        if (!nazev.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" OR ");
            queryBuilder.append("nazev = ?");
            hasConditions = true;
        }
        if (!cenaText.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" OR ");
            queryBuilder.append("cena = ?");
            hasConditions = true;
        }
        if (skladem) {
            if (hasConditions)
                queryBuilder.append(" OR ");
            queryBuilder.append("skladem = ?");
        }

        PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
        int parameterIndex = 1;
        if (!id.isEmpty())
            preparedStatement.setString(parameterIndex++, id);
        if (!nazev.isEmpty())
            preparedStatement.setString(parameterIndex++, nazev);
        if (!cenaText.isEmpty())
            preparedStatement.setString(parameterIndex++, cenaText);
        if (skladem)
            preparedStatement.setBoolean(parameterIndex, skladem);

        int deletedRows = preparedStatement.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (deletedRows > 0) {
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Produkt byl úspěšně odstraněn z databáze.");
        } else {
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("Produkt nebyl nalezen nebo nebyl odstraněn.");
        }
        alert.showAndWait();
    }

    @FXML
    private TextField zamestnanecId;
    @FXML
    private TextField zamestnanecJmeno;
    @FXML
    private TextField zamestnanecPrijmeni;
    @FXML
    private TextField zamestnanecEmail;
    @FXML
    private TextField zamestnanecPozice;
    @FXML
    private TextField zamestnanecPlat;

    @FXML
    public void removeFromZamestnanec() throws SQLException {
        String id = zamestnanecId.getText();
        String jmeno = zamestnanecJmeno.getText();
        String prijmeni = zamestnanecPrijmeni.getText();
        String email = zamestnanecEmail.getText();
        String pozice = zamestnanecPozice.getText();
        String platText = zamestnanecPlat.getText();

        StringBuilder queryBuilder = new StringBuilder("DELETE FROM Zamestnanec WHERE ");
        boolean hasConditions = false;

        if (!id.isEmpty()) {
            queryBuilder.append("id = ?");
            hasConditions = true;
        }
        if (!jmeno.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" AND ");
            queryBuilder.append("jmeno = ?");
            hasConditions = true;
        }
        if (!prijmeni.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" AND ");
            queryBuilder.append("prijmeni = ?");
            hasConditions = true;
        }
        if (!email.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" AND ");
            queryBuilder.append("email = ?");
            hasConditions = true;
        }
        if (!pozice.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" AND ");
            queryBuilder.append("pozice = ?");
            hasConditions = true;
        }
        if (!platText.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" AND ");
            queryBuilder.append("plat = ?");
        }

        PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
        int parameterIndex = 1;
        if (!id.isEmpty())
            preparedStatement.setString(parameterIndex++, id);
        if (!jmeno.isEmpty())
            preparedStatement.setString(parameterIndex++, jmeno);
        if (!prijmeni.isEmpty())
            preparedStatement.setString(parameterIndex++, prijmeni);
        if (!email.isEmpty())
            preparedStatement.setString(parameterIndex++, email);
        if (!pozice.isEmpty())
            preparedStatement.setString(parameterIndex++, pozice);
        if (!platText.isEmpty())
            preparedStatement.setString(parameterIndex, platText);

        int deletedRows = preparedStatement.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (deletedRows > 0) {
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Zamestnanec byl úspěšně odstraněn z databáze.");
        } else {
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("Zamestnanec nebyl nalezen nebo nebyl odstraněn.");
        }
        alert.showAndWait();

    }

    @FXML
    private TextField objednavkaZakaznikId;
    @FXML
    private TextField objednavkaProduktId;
    @FXML
    private TextField objednavkaMnozstvi;
    @FXML
    private TextField objednavkaCena;
    @FXML
    private DatePicker objednavkaDatum;

    @FXML
    public void removeFromObjednavka() throws SQLException {
        String zakaznikId = objednavkaZakaznikId.getText();
        String produktId = objednavkaProduktId.getText();
        String mnozstvi = objednavkaMnozstvi.getText();
        String cena = objednavkaCena.getText();
        String datum = (objednavkaDatum.getValue() != null) ? objednavkaDatum.getValue().toString() : null;

        StringBuilder queryBuilder = new StringBuilder("DELETE FROM Objednavka WHERE ");
        boolean hasConditions = false;

        if (!zakaznikId.isEmpty()) {
            queryBuilder.append("zakaznik_id = ?");
            hasConditions = true;
        }
        if (!produktId.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" OR ");
            queryBuilder.append("produkt_id = ?");
            hasConditions = true;
        }
        if (!mnozstvi.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" OR ");
            queryBuilder.append("mnozstvi = ?");
            hasConditions = true;
        }
        if (!cena.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" OR ");
            queryBuilder.append("cena = ?");
            hasConditions = true;
        }
        if (datum != null && !datum.isEmpty()) {
            if (hasConditions)
                queryBuilder.append(" OR ");
            queryBuilder.append("datum_objednavky = ?");
        }

        PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
        int parameterIndex = 1;

        if (!zakaznikId.isEmpty())
            preparedStatement.setString(parameterIndex++, zakaznikId);
        if (!produktId.isEmpty())
            preparedStatement.setString(parameterIndex++, produktId);
        if (!mnozstvi.isEmpty())
            preparedStatement.setString(parameterIndex++, mnozstvi);
        if (!cena.isEmpty())
            preparedStatement.setString(parameterIndex++, cena);
        if (datum != null && !datum.isEmpty())
            preparedStatement.setString(parameterIndex, datum);

        int deletedRows = preparedStatement.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (deletedRows > 0) {
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Objednavka byla úspěšně odstraněn z databáze.");
        } else {
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("Objednavka nebyla nalezen nebo nebyl odstraněn.");
        }
        alert.showAndWait();
        connection.close();
    }



    }

