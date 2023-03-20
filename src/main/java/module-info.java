module com.example.cemenuta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cemenuta to javafx.fxml;
    exports com.example.cemenuta;
}