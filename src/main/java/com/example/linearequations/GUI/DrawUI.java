package com.example.linearequations.GUI;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;


// For invisible Nodes -- instead of rewriting the functions
public class DrawUI {

    public static Label addLabel(String text, int fontSize, int x, int y) {
        Label label = new Label(text);
        label.setFont(new Font("System", fontSize));
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public static ChoiceBox<String> addChoiceBox(String[] forms, int x, int y, int w, int h) {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(forms);
        choiceBox.setLayoutX(x);
        choiceBox.setLayoutY(y);
        choiceBox.prefWidth(w);
        choiceBox.prefHeight(h);
        return choiceBox;
    }

    public static TextField addTextField(int x, int y) {
        TextField textField = new TextField();
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setFont(new Font("System", 18));
        textField.setAlignment(Pos.BOTTOM_LEFT);
        textField.setCursor(Cursor.TEXT);
        return textField;
    }

}
