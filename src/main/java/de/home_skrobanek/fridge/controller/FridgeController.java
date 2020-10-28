package de.home_skrobanek.fridge.controller;

import com.blogspot.debukkitsblog.net.Datapackage;
import de.home_skrobanek.fridge.SmartFridge;
import de.home_skrobanek.fridge.config.JSONManager;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.json.simple.JSONArray;


public class FridgeController {

    private JSONManager fileManager = new JSONManager();

    @FXML
    ListView list;

    @FXML
    Label isEmpty;

    @FXML
    StackedBarChart stackedBar;

    @FXML
    protected void loadFridge(){
  /*      JSONArray array = fileManager.readFromJSON();

        for(int i = 0; i < array.size(); i++) {
            list.getItems().add(array.get(i));
        }*/
        SmartFridge.client.sendMessage( new Datapackage("READ_FROM_FRIDGE", SmartFridge.USERNAME, SmartFridge.TMP_KEY));
    }

}
