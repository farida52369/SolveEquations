/**
 *
 */
module com.example.linearequations {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires org.apache.commons.lang3;
    requires java.xml;


    opens com.example.linearequations to javafx.fxml;
    exports com.example.linearequations;
}