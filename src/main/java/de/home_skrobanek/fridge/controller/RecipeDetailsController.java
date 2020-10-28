package de.home_skrobanek.fridge.controller;

import de.extra.lib.Recipe;
import de.home_skrobanek.fridge.SmartFridge;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class RecipeDetailsController {

    public static Recipe recipe;
    private int currentStep = 0;

    @FXML
    Label title, steps;

    @FXML
    TextArea description;

    @FXML
    Button lastStep, nextStep, back;

    public void initialize(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        title.setText(recipe.getName());
                        description.setText(recipe.getStepList().get(currentStep).getDescription());
                        lastStep.setDisable(true);
                        steps.setText((currentStep+1) + "/"+recipe.getStepList().size());
                    }
                });
            }
        });
        t.start();
    }

    @FXML
    protected void setNextStep(){
        if(currentStep < recipe.getStepList().size())
            currentStep++;
        if(currentStep+1 == recipe.getStepList().size())
            nextStep.setDisable(true);

        lastStep.setDisable(false);
        description.setText(recipe.getStepList().get(currentStep).getDescription());
        steps.setText((currentStep+1) + "/"+recipe.getStepList().size());
    }

    @FXML
    protected void setLastStep(){
        if(currentStep > 0)
            currentStep--;
        if(currentStep == 0)
            lastStep.setDisable(true);

        nextStep.setDisable(false);
        description.setText(recipe.getStepList().get(currentStep).getDescription());
        steps.setText((currentStep+1) + "/"+recipe.getStepList().size());
    }

    @FXML
    protected void goBack(){
        SmartFridge.mainController.changeView(MainController.WindowState.RECEIPES);
    }
}
