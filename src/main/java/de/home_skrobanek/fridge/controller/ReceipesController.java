package de.home_skrobanek.fridge.controller;

import com.blogspot.debukkitsblog.net.Datapackage;
import de.extra.lib.Recipe;
import de.home_skrobanek.fridge.SmartFridge;
import de.home_skrobanek.fridge.console.management.Console;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ReceipesController {

    public static ArrayList<Recipe> list;

    @FXML
    ListView listOnlineRecipe, listOfRecipesWithNeeds;

    public void initialize(){
        //SmartFridge.client.sendMessage(new Datapackage("READ_ALL_RECIPES", SmartFridge.USERNAME, SmartFridge.TMP_KEY));

        listOnlineRecipe.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2){
                    SmartFridge.mainController.changeView(MainController.WindowState.RECIPE_DETAILES);
                    System.out.println(listOnlineRecipe.getSelectionModel().getSelectedItems().get(0).toString());

                    for(int i = 0; i < list.size(); i++){
                        String display = ReceipesController.list.get(i).getName() + " | " + ReceipesController.list.get(i).getNeeds();
                        if(display.equals(listOnlineRecipe.getSelectionModel().getSelectedItems().get(0).toString()))
                            RecipeDetailsController.recipe =list.get(i);
                    }
                }
            }
        });

        listOfRecipesWithNeeds.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2){
                    SmartFridge.mainController.changeView(MainController.WindowState.RECIPE_DETAILES);
                    System.out.println(listOfRecipesWithNeeds.getSelectionModel().getSelectedItems().get(0).toString());

                    for(int i = 0; i < list.size(); i++){
                        String display = ReceipesController.list.get(i).getName() + " | " + ReceipesController.list.get(i).getNeeds();
                        if(display.equals(listOfRecipesWithNeeds.getSelectionModel().getSelectedItems().get(0).toString()))
                            RecipeDetailsController.recipe =list.get(i);
                    }
                }
            }
        });
    }

    @FXML
    protected void readRecipes(){
        SmartFridge.client.sendMessage(new Datapackage("READ_ALL_RECIPES", SmartFridge.USERNAME, SmartFridge.TMP_KEY));
    }

    @FXML
    protected void readRecipesWithNeeds(){
        Console.debugMessage("recipe with all needs");
        SmartFridge.client.sendMessage(new Datapackage("READ_RECIPES_WITH_NEEDS", SmartFridge.USERNAME, SmartFridge.TMP_KEY));
    }

}
