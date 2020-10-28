package de.home_skrobanek.fridge.controller;

import de.home_skrobanek.fridge.SmartFridge;
import de.home_skrobanek.fridge.console.management.Console;
import de.home_skrobanek.fridge.utils.Animation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MainController {

    @FXML
    public Pane contentPane;

    @FXML
    Label server;

    @FXML
    Button startButton, insertButton, viewButton, buyButton, recipeButton,
            settingsButton, ownRecipeButton, calculator;

    private WindowState lastState = WindowState.DEFAULT;

    public ReceipesController receipesController;
    public FridgeController fridgeController;

    @FXML
    protected void selectStart(){
        setButtonColor(startButton);
        changeView(WindowState.DEFAULT);
    }

    @FXML
    protected void selectFridge(){
        setButtonColor(viewButton);
        if(SmartFridge.loggedIn)
            changeView(WindowState.FRIDGE);
        else {
            lastState = WindowState.FRIDGE;
            changeView(WindowState.LOGIN);
        }
    }

    @FXML
    protected void selectFridgeInput(){
        setButtonColor(insertButton);
        if(SmartFridge.loggedIn)
            changeView(WindowState.FRIDGE_INPUT);
        else {
            lastState = WindowState.FRIDGE_INPUT;
            changeView(WindowState.LOGIN);
        }
    }

    @FXML
    protected void selectSettings(){
        setButtonColor(settingsButton);
        changeView(WindowState.SETTINGS);
    }

    @FXML
    protected void selectListToBuy(){
        setButtonColor(buyButton);
        changeView(WindowState.SETTINGS);
    }

    @FXML
    protected void selectOwnRecipe(){
        setButtonColor(ownRecipeButton);
        if(SmartFridge.loggedIn)
            changeView(WindowState.OWN_RECIPES);
        else {
            lastState = WindowState.OWN_RECIPES;
            changeView(WindowState.LOGIN);
        }
    }

    @FXML
    protected void selectReceipes(){
        setButtonColor(recipeButton);
        if(SmartFridge.loggedIn)
            changeView(WindowState.RECEIPES);
        else {
            lastState = WindowState.RECEIPES;
            changeView(WindowState.LOGIN);
        }
    }

    @FXML
    protected void selectCalculator(){
        setButtonColor(calculator);
        changeView(WindowState.CALCULATOR);

    }

    public void setLastState(){
        changeView(lastState);
    }

    //Select the different views in the side menu
    public void changeView(WindowState state){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            SmartFridge.loginPane.getChildren().remove(contentPane);
            switch (state) {
                case DEFAULT:
                    fxmlLoader.setLocation(getClass().getResource("/FXML/default.fxml"));
                    contentPane = fxmlLoader.load();
                    break;
                case FRIDGE:
                    contentPane = fxmlLoader.load(getClass().getResource("/FXML/fridge.fxml").openStream());
                    fridgeController = (FridgeController) fxmlLoader.getController();
                    break;
                case FRIDGE_INPUT:
                    fxmlLoader.setLocation(getClass().getResource("/FXML/fridgeInput.fxml"));
                    contentPane = fxmlLoader.load();
                    break;
                case LIST_TO_BUY:
                    fxmlLoader.setLocation(getClass().getResource("/FXML/list.fxml"));
                    contentPane = fxmlLoader.load();
                    break;
                case RECEIPES:
                    contentPane = fxmlLoader.load(getClass().getResource("/FXML/receipes.fxml").openStream());
                    receipesController = (ReceipesController) fxmlLoader.getController();
                    break;
                case OWN_RECIPES:
                    fxmlLoader.setLocation(getClass().getResource("/FXML/ownRecipes.fxml"));
                    contentPane = fxmlLoader.load();
                    break;
                case SETTINGS:
                    fxmlLoader.setLocation(getClass().getResource("/FXML/settings.fxml"));
                    contentPane = fxmlLoader.load();
                    break;
                case CALCULATOR:
                    fxmlLoader.setLocation(getClass().getResource("/FXML/calculator.fxml"));
                    contentPane = fxmlLoader.load();
                    break;
                case LOGIN:
                    fxmlLoader.setLocation(getClass().getResource("/FXML/login.fxml"));
                    contentPane = fxmlLoader.load();
                    break;
                case RECIPE_DETAILES:
                    fxmlLoader.setLocation(getClass().getResource("/FXML/receipesDetail.fxml"));
                    contentPane = fxmlLoader.load();
                    break;
                default:
                    Console.warnInConsole("Error while loading other WindowState");
                    changeView(WindowState.DEFAULT);
                    break;
            }
            SmartFridge.loginPane.getChildren().add(contentPane);

            contentPane.setLayoutX(265);
            System.out.println("Setup");
            Console.debugMessage("Setup loaded");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void setButtonColor(Button b){
        startButton.setStyle("-fx-background-color: darkgreen; -fx-alignment: center-left;");
        insertButton.setStyle("-fx-background-color: darkgreen; -fx-alignment: center-left;");
        viewButton.setStyle("-fx-background-color: darkgreen; -fx-alignment: center-left;");
        recipeButton.setStyle("-fx-background-color: darkgreen; -fx-alignment: center-left;");
        buyButton.setStyle("-fx-background-color: darkgreen; -fx-alignment: center-left;");
        settingsButton.setStyle("-fx-background-color: darkgreen; -fx-alignment: center-left;");
        ownRecipeButton.setStyle("-fx-background-color: darkgreen; -fx-alignment: center-left;");
        calculator.setStyle("-fx-background-color: darkgreen; -fx-alignment: center-left;");

        b.setStyle("-fx-background-color: green; -fx-alignment: center-left;");
    }

    //different states which shows several views in this program.
    public enum WindowState{
        DEFAULT,
        FRIDGE,
        FRIDGE_INPUT,
        LIST_TO_BUY,
        RECEIPES,
        OWN_RECIPES,
        LOGIN,
        CALCULATOR,
        RECIPE_DETAILES,
        SETTINGS;
    }
}
