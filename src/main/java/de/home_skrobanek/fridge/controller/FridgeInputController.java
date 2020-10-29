package de.home_skrobanek.fridge.controller;

import com.blogspot.debukkitsblog.net.Datapackage;
import de.home_skrobanek.fridge.SmartFridge;
import de.home_skrobanek.fridge.config.JSONManager;
import de.home_skrobanek.fridge.utils.Animation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.UnsupportedEncodingException;

public class FridgeInputController {

    @FXML
    ChoiceBox einheit;

    @FXML
    ChoiceBox category;

    @FXML
    ScrollBar scrollbar;

    @FXML
    Label addFood;

    @FXML
    Button addButton;

    @FXML
    TextField nameInput, amountInput;

    public void initialize() throws UnsupportedEncodingException {

        //values for the choiceboxes
        einheit.getItems().add("Gramm");
        einheit.getItems().add("Kilogramm");
        einheit.getItems().add(new String("Stück".getBytes(),"UTF-8"));
        einheit.getItems().add("Liter");
        einheit.getItems().add("Milliliter");

        category.getItems().add(new String("Getränke".getBytes(),"UTF-8"));
        category.getItems().add(new String("Obst/Gemüse".getBytes(),"UTF-8"));
        category.getItems().add(new String("Getreideprodukte".getBytes(),"UTF-8"));
        category.getItems().add(new String("Milchprodukte".getBytes(),"UTF-8"));
        category.getItems().add(new String("Fleisch/Fisch".getBytes(),"UTF-8"));
        category.getItems().add(new String("Fette/Öle".getBytes(),"UTF-8"));
        category.getItems().add(new String("Süßwaren".getBytes(),"UTF-8"));
    }

    @FXML
    protected void dragged() {
        Pane pane = (Pane) SmartFridge.loginPane.lookup("#contentPane");
        pane.setLayoutY(pane.getLayoutY() - scrollbar.getValue());
    }

    @FXML
    protected void addToJSON() {
        try {
            SmartFridge.client.sendMessage(new Datapackage("INSERT_INTO_FRIDGE", SmartFridge.USERNAME, SmartFridge.TMP_KEY,
                    nameInput.getText(), Float.parseFloat(amountInput.getText()), einheit.getValue(), category.getValue()));

            Animation.startAnimation(new Runnable() {
                @Override
                public void run() {
                    try {
                        addFood.setVisible(true);
                        addButton.setDisable(true);
                        Thread.sleep(2000);
                        addButton.setDisable(false);
                        addFood.setVisible(false);

                        nameInput.setText("");
                        amountInput.setText("");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch(NumberFormatException e){

            nameInput.setText("");
            amountInput.setText("");

        }
    }

}
