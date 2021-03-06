package de.home_skrobanek.fridge.client;

import com.blogspot.debukkitsblog.net.Client;
import com.blogspot.debukkitsblog.net.Datapackage;
import com.blogspot.debukkitsblog.net.Executable;
import de.extra.lib.Recipe;
import de.home_skrobanek.fridge.SmartFridge;
import de.home_skrobanek.fridge.console.management.Console;
import de.extra.lib.Food;
import de.home_skrobanek.fridge.controller.MainController;
import de.home_skrobanek.fridge.controller.ReceipesController;
import de.home_skrobanek.fridge.utils.Animation;
import de.home_skrobanek.fridge.utils.WindowsMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class SocketClient extends Client {
    public SocketClient(String hostname, int port) {
        super(hostname, port);

        registerMethod("KEY_REPLY", new Executable() {
            @Override
            public void run(Datapackage datapackage, Socket socket) {
                SmartFridge.TMP_KEY = datapackage.get(1).toString();
                Console.debugMessage("Set key");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        SmartFridge.mainController.setLastState();
                    }
                });
            }
        });

        registerMethod("REGISTERED", new Executable() {
            @Override
            public void run(Datapackage datapackage, Socket socket) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        SmartFridge.mainController.changeView(MainController.WindowState.LOGIN);

                    }
                });
            }
        });

        registerMethod("READ_FROM_FRIDGE", new Executable() {
            @Override
            public void run(Datapackage datapackage, Socket socket) {
                //getting food list from server
                ListView list = (ListView) SmartFridge.loginPane.lookup("#list");
                ArrayList<Food> foodList = (ArrayList<Food>)datapackage.get(1);

                list.getItems().clear();
                //checking if fridge has food
                if(!foodList.isEmpty()) {

                    loadStackedBar(foodList);

                    for (int i = 0; i < foodList.size(); i++) {
                        try {
                            list.getItems().add(new String(foodList.get(i).getName().getBytes(), "UTF-8") + " | " + foodList.get(i).getAmount() + " " +
                                    new String(foodList.get(i).getType().getBytes(),"UTF-8") + " | " +
                                    new String(foodList.get(i).getCategory().getBytes(), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    //case: Empty fridge
                    Label isEmpty = (Label) SmartFridge.mainController.contentPane.lookup("#isEmpty");
                    Animation.startAnimation(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                isEmpty.setVisible(true);
                                Thread.sleep(3*1000);
                                isEmpty.setVisible(false);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        registerMethod("READ_ALL_RECIPES", new Executable() {
            @Override
            public void run(Datapackage datapackage, Socket socket) {
                ReceipesController.list = (ArrayList<Recipe>)datapackage.get(1);
                System.out.println("test");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(ReceipesController.list.size() > 0) {
                            ListView list = (ListView) SmartFridge.mainController.contentPane.lookup("#listOnlineRecipe");

                            list.getItems().clear();

                            for (int i = 0; i < ReceipesController.list.size(); i++) {
                                    list.getItems().add(ReceipesController.list.get(i).getName()
                                            + " | " + ReceipesController.list.get(i).getNeeds());

                            }
                        }
                        else{
                            Label noneFound = (Label) SmartFridge.mainController.contentPane.lookup("#noneFound");
                            Animation.startAnimation(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        noneFound.setVisible(true);
                                        Thread.sleep(3*1000);
                                        noneFound.setVisible(false);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        registerMethod("READ_RECIPES_WITH_NEEDS", new Executable() {
            @Override
            public void run(Datapackage datapackage, Socket socket) {
                ReceipesController.list = (ArrayList<Recipe>)datapackage.get(1);
                System.out.println("test2");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(ReceipesController.list.size() > 0) {
                            ListView list = (ListView) SmartFridge.mainController.contentPane.lookup("#listOfRecipesWithNeeds");

                            list.getItems().clear();

                            for (int i = 0; i < ReceipesController.list.size(); i++) {
                                try {
                                    list.getItems().add(new String(ReceipesController.list.get(i).getName().getBytes(),"UTF-8")
                                            + " | " + new String(ReceipesController.list.get(i).getNeeds().getBytes(), "UTF-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else{
                            Label noneFound = (Label) SmartFridge.mainController.contentPane.lookup("#noneFound");
                            Animation.startAnimation(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        noneFound.setVisible(true);
                                        Thread.sleep(3*1000);
                                        noneFound.setVisible(false);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        registerMethod("ERROR", new Executable() {
            @Override
            public void run(Datapackage datapackage, Socket socket) {
                SmartFridge.loggedIn = false;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        SmartFridge.mainController.changeView(MainController.WindowState.DEFAULT);
                        try {
                            WindowsMessage.displayError("SmartFridge Fehler! ",datapackage.get(1).toString());
                        } catch (AWTException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        start();
    }

    /**
     *
     * @param food
     *      the list containing the food from the user fridge.
     */
    private void loadStackedBar(ArrayList<Food> food){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //get amount of category
                int drinks = 0, fruit = 0, grain = 0, milk = 0, fish = 0 , fat =0 , sweets= 0;
                for(int i = 0; i < food.size(); i++){
                    switch(food.get(i).getCategory()){
                        case "Getränke":
                            drinks +=1;
                            break;
                        case "Obst/Gemüse":
                            fruit +=1;
                            break;
                        case "Getreideprodukte":
                            grain +=1;
                            break;
                        case "Milchprodukte":
                            milk +=1;
                            break;
                        case "Fleich/Fisch":
                            fish +=1;
                            break;
                        case "Fette/Öle":
                            fat +=1;
                            break;
                        case "Süßwaren":
                            sweets +=1;
                            break;
                    }
                }

                StackedBarChart stack = (StackedBarChart) SmartFridge.mainController.contentPane.lookup("#stackedBar");
                try {
                    ((CategoryAxis)stack.getXAxis()).setCategories(FXCollections.<String>observableArrayList(Arrays.asList(
                            new String("Getränke".getBytes(),"UTF-8"),
                            new String("Obst/Gemüse".getBytes(), "UTF-8"),
                            new String("Getreideprodukte".getBytes(), "UTF-8"),
                            new String("Milchprodukte".getBytes(),"UTF-8"),
                            new String("Fleisch/Fisch".getBytes(),"UTF-8"),
                            new String("Fette".getBytes(), "UTF-8"),new String("Süßwaren".getBytes(),"UTF-8"))));

                    System.out.println((float)((fruit*100)/ food.size()));
                    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
                    series1.setName("Prozentualer Anteil");
                    series1.getData().add(new XYChart.Data<>(new String("Getränke".getBytes(),"UTF-8"), (float)(drinks*100)/ food.size()));
                    series1.getData().add(new XYChart.Data<>(new String("Obst/Gemüse".getBytes(), "UTF-8"), (float)(fruit*100)/ food.size()));
                    series1.getData().add(new XYChart.Data<>(new String("Getreideprodukte".getBytes(), "UTF-8"), (grain*100)/ food.size()));
                    series1.getData().add(new XYChart.Data<>(new String("Milchprodukte".getBytes(),"UTF-8"), (milk*100)/ food.size()));
                    series1.getData().add(new XYChart.Data<>(new String("Fleisch/Fisch".getBytes(),"UTF-8"), (fish*100)/ food.size()));
                    series1.getData().add(new XYChart.Data<>(new String("Fette/Öle".getBytes(), "UTF-8"), (fat*100)/ food.size()));
                    series1.getData().add(new XYChart.Data<>(new String("Süßwaren".getBytes(),"UTF-8"), (sweets*100)/ food.size()));
                    stack.getData().addAll(series1);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
