module com.example.polanecky {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.polanecky to javafx.fxml;
    exports com.example.polanecky;
}