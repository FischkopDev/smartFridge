/**
 * @author
 *      Timo Skrobanek
 *
 * @date
 *      14.10.2020
 *
 * @website
 *      www.home-skrobanek.de
 *
 * @description
 *      A little software to manage the fridge and your food in a modern way.
 */
package de.home_skrobanek.fridge;

import de.home_skrobanek.fridge.client.SocketClient;
import de.home_skrobanek.fridge.config.ConfigManager;
import de.home_skrobanek.fridge.console.management.Console;
import de.home_skrobanek.fridge.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SmartFridge extends Application {

    public static Scene loginScene;
    public static AnchorPane loginPane;
    public static SocketClient client;
    public static MainController mainController;
    public static ConfigManager configManager;
    public static Stage stage;

    //saves the temporary key from the server to perform next actions
    public static String TMP_KEY = "";
    public static String USERNAME = "";

    //checks if you're logged into the network
    public static boolean loggedIn = false;

    //another thread for separated traffic handling
    private static Thread network = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                client = new SocketClient(configManager.readFromConfig("host"),
                        Integer.parseInt(configManager.readFromConfig("port")));

            }catch(Exception e){
                Console.warnInConsole("Not able to connect to the network");
            }
        }
    });

    @Override
    public void start(Stage stage) throws Exception {
        SmartFridge.stage = stage;
        //activate debug mode
        Console.setDebugging(true);

        FXMLLoader fxmlLoader = new FXMLLoader();
        loginPane = fxmlLoader.load(getClass().getResource("/FXML/main.fxml").openStream());
        mainController = (MainController) fxmlLoader.getController();

        loginScene = new Scene(loginPane, 900, 600);

        stage.setOnCloseRequest(event -> {
            Console.debugMessage("Saving data...");
            client.stop();
            Console.debugMessage("Closing program.");
            System.exit(0);
        });
        stage.setScene(loginScene);
        stage.setTitle("Smart Fridge | Timo Skrobanek");
        stage.setResizable(false);
        stage.show();

        network.start();

        mainController.changeView(MainController.WindowState.DEFAULT);
    }

    public static void main(String[]args){
        configManager = new ConfigManager();
        launch(args);
    }
}
