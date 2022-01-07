/**
 *
 */
module com.example.linearequations {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;


    opens com.example.linearequations to javafx.fxml;
    exports com.example.linearequations;
}