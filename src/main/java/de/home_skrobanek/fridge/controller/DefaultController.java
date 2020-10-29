package de.home_skrobanek.fridge.controller;

import de.home_skrobanek.fridge.SmartFridge;
import javafx.fxml.FXML;

public class DefaultController {

    @FXML
    protected void login(){
        SmartFridge.mainController.changeView(MainController.WindowState.LOGIN);
    }
}
