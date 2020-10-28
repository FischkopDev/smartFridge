package de.home_skrobanek.fridge.controller;

import com.blogspot.debukkitsblog.net.Datapackage;
import de.extra.lib.Food;
import de.extra.lib.Recipe;
import de.extra.lib.SerialImage;
import de.extra.lib.Step;
import de.home_skrobanek.fridge.SmartFridge;
import de.home_skrobanek.fridge.console.management.Console;
import de.home_skrobanek.fridge.utils.FoodDisplay;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;

public class OwnRecipeController {

    @FXML
    TextArea description1;

    @FXML
    TextField title, food, foodAmount, foodType;

    @FXML
    TableView foodTable;

    @FXML
    Button uploadImage1, nextStep, finish;

    private Recipe recipe;
    private SerialImage img;

    public void initialize(){
        recipe = new Recipe(new ArrayList<Food>(), new ArrayList<Step>());

        TableColumn<String, FoodDisplay> column1 = new TableColumn<>("Zutat");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, FoodDisplay> column2 = new TableColumn<>("Menge");
        column2.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<String, FoodDisplay> column3 = new TableColumn<>("Einheit");
        column3.setCellValueFactory(new PropertyValueFactory<>("type"));

        foodTable.getColumns().add(column1);
        foodTable.getColumns().add(column2);
        foodTable.getColumns().add(column3);
    }

    @FXML
    protected void nextStep(){
        try {
            recipe.setName(title.getText());
            recipe.getStepList().add(new Step(description1.getText(), "Step", null));

            description1.setText("");
        }catch(NullPointerException e){
            Console.warnInConsole("Missing parameter in Recipe");
        }
    }

    @FXML
    protected void uploadRecipe(){
        if(!title.getText().isEmpty() || !title.getText().equals(" ")) {
            try {
                recipe.setName(title.getText());
                recipe.getStepList().add(new Step(description1.getText(), "Step", img));

            } catch (NullPointerException e) {
                Console.warnInConsole("Missing parameter in Recipe");
            }

            //send recipe
            SmartFridge.client.sendMessage(new Datapackage("UPLOAD_RECIPE", SmartFridge.USERNAME, SmartFridge.TMP_KEY, recipe));

            //empty fields
            description1.setText("");
            food.setText("");
            foodAmount.setText("");
            foodType.setText("");
            title.setText("");

            //change view
            SmartFridge.mainController.changeView(MainController.WindowState.DEFAULT);
        }
    }

    @FXML
    protected void addFood(){
        Food foodItem = new Food(food.getText(), foodAmount.getText(),foodType.getText(), null);
        recipe.getFoodList().add(foodItem);

        foodTable.getItems().add(new FoodDisplay(food.getText(), foodAmount.getText(),foodType.getText()));

        //empty fields
        food.setText("");
        foodAmount.setText("");
        foodType.setText("");
    }

    @FXML
    protected void uploadImage(){
        try {
            File file = openFileChooser("Bild auswählen");
            ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file);
            // Image img = new Image(file.getAbsolutePath());
            img = (SerialImage) icon.getImage();
        }catch(NullPointerException e){

        }
    }

    private File openFileChooser(String name) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(name);
        return fileChooser.showOpenDialog(SmartFridge.stage);
    }
}
