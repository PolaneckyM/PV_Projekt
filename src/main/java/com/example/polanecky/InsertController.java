package com.example.polanecky;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class InsertController {
    
    @FXML
    public Label oddeleniAlert;
    @FXML
    public Label objednavkaAlert;
    @FXML
    public Label zakaznikAlert;
    @FXML
    public Label zamestnanecAlert;
    @FXML
    public Label produktAlert;
    
    
    public void setAlertVisible(Label label){
        label.setVisible(true);
    }
    public void unSetAlertVisible(Label label){
        label.setVisible(false);
    }


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

    // Method for loadingDB info from the csv file located in the file
    // DBConnectionData.csv in the project directory
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

    // Methods for showing forms for inserting info to tables based by user
    // selection
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
        unSetAlertVisible(produktAlert);
        unSetAlertVisible(zamestnanecAlert);
        unSetAlertVisible(zakaznikAlert);
        unSetAlertVisible(oddeleniAlert);
        unSetAlertVisible(objednavkaAlert);
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

    // method that handles the user choice and runs selected method
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
            // Zde můžete zobrazit upozornění, že uživatel musí vybrat tabulku
        }
    }

    // method that initializes the form selection choicebox method and loades the
    // data and connects to DB

    public void initialize() {
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            handleSelection();
        });
        loadDBPropertiesFromCSV();

    }

    public void showSuccessAlert(String word){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Uspesne vlozne do tabulky "+word);
        alert.showAndWait();
    }

    @FXML
    private TextField oddeleniNazev;

    @FXML
    private TextField oddeleniPopis;

    // methods for inserting into existing tables into the database
    @FXML
    public void addToOddeleni() {
        String nazev = oddeleniNazev.getText();
        String popis = oddeleniPopis.getText();

        if (!nazev.isEmpty() && !popis.isEmpty()) {
            try {
                String query = "INSERT INTO Oddeleni (nazev, popis) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nazev);
                preparedStatement.setString(2, popis);
                preparedStatement.executeUpdate();
                System.out.println("VLOZENO");
                showSuccessAlert("Oddeleni");
//                setAlertVisible(oddeleniAlert);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (nazev.isEmpty()) {
                oddeleniNazev.setText("You forgot to fill me!");
            }
            if (popis.isEmpty()) {
                oddeleniPopis.setText("You forgot to fill me!");
            }
        }
    }

    @FXML
    private TextField zakaznikJmeno;
    @FXML
    private TextField zakaznikPrijmeni;
    @FXML
    private TextField zakaznikEmail;
    @FXML
    private DatePicker zakaznikDatumReg;

    @FXML
    public void addToZakaznik() {
        String jmeno = zakaznikJmeno.getText();
        String prijmeni = zakaznikPrijmeni.getText();
        String email = zakaznikEmail.getText();
        String datumReg = zakaznikDatumReg.getValue().toString();

        if (!jmeno.isEmpty() && !prijmeni.isEmpty() && !email.isEmpty() && zakaznikDatumReg.getValue() != null) {
            try {
                String query = "INSERT INTO Zakaznik (jmeno, prijmeni, email, datum_registrace) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, jmeno);
                preparedStatement.setString(2, prijmeni);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, datumReg);
                preparedStatement.executeUpdate();
                System.out.println("Vlozeno");
                showSuccessAlert("Zakaznik");
//                setAlertVisible(zakaznikAlert);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (jmeno.isEmpty()) {
            zakaznikJmeno.setText("You forgot to fill me!");
        } else if (prijmeni.isEmpty()) {
            zakaznikPrijmeni.setText("You forgot to fill me!");
        } else if (email.isEmpty()) {
            zakaznikEmail.setText("You forgot to fill me!");
        }
    }

    @FXML
    private TextField produktNazev;
    @FXML
    private TextField produktCena;
    @FXML
    private CheckBox produktSkladem;

    @FXML
    public void addProdukt() {
        String nazev = produktNazev.getText();
        String cenaText = produktCena.getText();
        boolean skladem = produktSkladem.isSelected();

        if (!nazev.isEmpty() && !cenaText.isEmpty()) {
            try {
                BigDecimal cena = new BigDecimal(cenaText);
                String query = "INSERT INTO Produkt (nazev, cena, skladem) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nazev);
                preparedStatement.setBigDecimal(2, cena);
                preparedStatement.setBoolean(3, skladem);
                preparedStatement.executeUpdate();
                System.out.println("VLOZENO");
                setAlertVisible(produktAlert);
                connection.close();
            } catch (SQLException e) {
                System.out.println("Nelze vlozit dvakrat za sebou!");

            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        } else {
            if (cenaText.isEmpty()) {
                produktCena.setText("You forgot to fill me!");
            }
            if (nazev.isEmpty()) {
                produktNazev.setText("You forgot to fill me!");
            }
        }
    }

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
    public void addToZamestnanec() {
        String jmeno = zamestnanecJmeno.getText();
        String prijmeni = zamestnanecPrijmeni.getText();
        String email = zamestnanecEmail.getText();
        String pozice = zamestnanecPozice.getText();
        String platText = zamestnanecPlat.getText();

        if (!jmeno.isEmpty() && !prijmeni.isEmpty() && !email.isEmpty() && !pozice.isEmpty() && !platText.isEmpty()) {
            try {
                BigDecimal plat = new BigDecimal(platText);

                String query = "INSERT INTO Zamestnanec (jmeno, prijmeni, pozice, plat, email) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, jmeno);
                preparedStatement.setString(2, prijmeni);
                preparedStatement.setString(3, pozice);
                preparedStatement.setBigDecimal(4, plat);
                preparedStatement.setString(5, email);
                preparedStatement.executeUpdate();
                System.out.println("VLOZENO!");
                showSuccessAlert("Zamestnanec");
                setAlertVisible(zamestnanecAlert);
                connection.close();
            } catch (SQLException e) {
                System.out.println("Nelze vlozit dvakrat za sebou!");
                e.printStackTrace();
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        } else {
            if (jmeno.isEmpty()) {
                zamestnanecJmeno.setText("You forgot to fill me !");
            }
            if (prijmeni.isEmpty()) {
                zamestnanecPrijmeni.setText("You forgot to fill me !");
            }
            if (email.isEmpty()) {
                zamestnanecEmail.setText("You forgot to fill me !");
            }
            if (platText.isEmpty()) {
                zamestnanecPlat.setText("You forgot to fill me !");
            }
            if (pozice.isEmpty()) {
                zamestnanecPozice.setText("You forgot to fill me !");
            }
        }
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
    public void addToObjednavka() {
        int zakaznikId = Integer.parseInt(objednavkaZakaznikId.getText());
        int produktId = Integer.parseInt(objednavkaProduktId.getText());
        int mnozstvi = Integer.parseInt(objednavkaMnozstvi.getText());
        BigDecimal cena = new BigDecimal(objednavkaCena.getText());
        LocalDate datum = objednavkaDatum.getValue();
        System.out.println(objednavkaDatum.getValue());
        System.out.println("objednavka mnozstvi je : "+objednavkaMnozstvi.getText().isEmpty());
        System.out.println("zakaznikid ="+Integer.parseInt(objednavkaZakaznikId.getText()));
        System.out.println("produktid ="+Integer.parseInt(objednavkaProduktId.getText()));
        System.out.println("mnozstvi ="+Integer.parseInt(objednavkaMnozstvi.getText()));

        if (zakaznikId > 0 && produktId > 0 && mnozstvi > 0) {
            try {
                String query = "INSERT INTO Objednavka (zakaznik_id, produkt_id, mnozstvi, cena, datum_objednavky) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, zakaznikId);
                preparedStatement.setInt(2, produktId);
                preparedStatement.setInt(3, mnozstvi);
                preparedStatement.setBigDecimal(4, cena);
                preparedStatement.setObject(5, datum);
                preparedStatement.executeUpdate();
                setAlertVisible(objednavkaAlert);
                System.out.println("Vlozeno!");
                showSuccessAlert("Objednavka");

                connection.close();
            } catch (SQLException | NumberFormatException e) {
                System.out.println("Nelze vlozit dvakrat za sebou!");
                e.printStackTrace();
            }
        }
    }

}
