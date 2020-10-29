package de.home_skrobanek.fridge.controller;

import de.home_skrobanek.fridge.utils.Animation;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.UnsupportedEncodingException;

/*
    Woman: 655,1 + (9,6 x Körpergewicht in kg) + (1,8 x Körpergröße in cm) – (4,7 x Alter in Jahren)
    Men: 66,47 + (13,7 x Körpergewicht in kg) + (5 x Körpergröße in cm) – (6,8 x Alter in Jahren)
    https://www.bildderfrau.de/diaet-ernaehrung/article212392331/Kalorienbedarf-berechnen-Formel-fuer-den-Diaet-Erfolg.html#:~:text=Kalorienbedarf%20berechnen%3A%20So%20ermitteln%20Sie%20Ihren%20Grundumsatz&text=Bis%20heute%20wird%20die%20sogenannte,7%20x%20Alter%20in%20Jahren)
 */
public class CalculatorController {

    @FXML
    TextField weight, height, old;

    @FXML
    Label bmi, normal, value, correctinput;

    @FXML
    ChoiceBox gender;

    @FXML
    CheckBox doSport;

    float normalValue;

    public void initialize() throws UnsupportedEncodingException {
        gender.getItems().add(new String("Männlich".getBytes(),"UTF-8"));
        gender.getItems().add("Weiblich");
    }

    @FXML
    protected void calculate(){
        try {
            if (gender.getSelectionModel().getSelectedItem().toString().equals(new String("Männlich".getBytes(),"UTF-8"))) {
                normalValue = 66.47f + (13.7f * Integer.parseInt(weight.getText())) +
                        (5f * Integer.parseInt(height.getText())) -
                        (6.8f * Integer.parseInt(old.getText()));
            } else if (gender.getSelectionModel().getSelectedItem().toString().equals("Weiblich")) {
                normalValue = 655.1f + (9.6f * Integer.parseInt(weight.getText())) +
                        (1.8f * Integer.parseInt(height.getText())) -
                        (4.7f * Integer.parseInt(old.getText()));
            }
            float heightValue = (Float.parseFloat(height.getText())) / 100;
            float bmiValue = Integer.parseInt(weight.getText()) / (heightValue * heightValue);

            normal.setText("Dein Grundbedarf ist: " + normalValue + "Kcal");
            bmi.setText("Dein BMI ist: " + bmiValue);

            if (doSport.isSelected())
                value.setText("Dein Tagesbedarf is: " + (normalValue * 1.7f));//Average with perl factor
            else
                value.setText("Dein Tagesbedarf is: " + (normalValue * 1.5f));//Average with perl factor
        }catch(NumberFormatException e){
            startAnimation();
        }catch(NullPointerException ex){
            startAnimation();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void startAnimation(){
        Animation.startAnimation(new Runnable() {
            @Override
            public void run() {

                try {
                    correctinput.setVisible(true);
                    Thread.sleep(3000);
                    correctinput.setVisible(false);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

    }
}
