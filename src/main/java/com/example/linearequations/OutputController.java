package com.example.linearequations;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class OutputController {

    @FXML
    private AnchorPane myOutput;

    public void display(String val) {
        TextArea outputLabel = new TextArea();
        // outputLabel.setMinWidth(50);
        outputLabel.setMinHeight(450);
        outputLabel.setLayoutX(25);
        outputLabel.setLayoutY(75);
        outputLabel.setFont(new Font("System", 17));
        outputLabel.setText(val);
        outputLabel.setEditable(false);
        myOutput.getChildren().add(outputLabel);
    }


}
