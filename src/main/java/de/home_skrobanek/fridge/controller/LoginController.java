package de.home_skrobanek.fridge.controller;

import com.blogspot.debukkitsblog.net.Datapackage;
import de.home_skrobanek.fridge.SmartFridge;
import de.home_skrobanek.fridge.utils.Animation;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Random;

public class LoginController {

    //register tab
    @FXML
    CheckBox accept;

    @FXML
    Button registerButton;

    @FXML
    TextField username, email;

    @FXML
    PasswordField pw, pwAgain;

    @FXML
    Label wrongPW, serverOnline;

    //login tab

    @FXML
    TextField usernameLogin;

    @FXML
    PasswordField passwordLogin;

    public void initialize(){
        registerButton.setDisable(!accept.isSelected());
    }

    @FXML
    protected void acceptConditions(){
        //some conditions for registration
        if(!pw.getText().isEmpty() && !pwAgain.getText().isEmpty() &&
                !username.getText().isEmpty() && !email.getText().isEmpty()
                && email.getText().contains("@")) {
            registerButton.setDisable(!accept.isSelected());
        }
    }

    @FXML
    protected void registerUser(){
        if(SmartFridge.client.isConnected()) {
            if (pw.getText().equals(pwAgain.getText())) {
                SmartFridge.client.sendMessage(new Datapackage("REGISTER", username.getText(), email.getText(),
                        pw.getText(), (username.getText() + "_" + new Random().nextInt(5))));

                SmartFridge.mainController.changeView(MainController.WindowState.LOGIN);
            } else {
                //TODO error message
                Animation.startAnimation(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            wrongPW.setVisible(true);
                            Thread.sleep(3 * 1000);
                            wrongPW.setVisible(false);

                            pw.setText("");
                            pwAgain.setText("");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
        else{
            Animation.startAnimation(new Runnable() {
                @Override
                public void run() {
                    try {
                        serverOnline.setVisible(true);
                        Thread.sleep(3 * 1000);
                        serverOnline.setVisible(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    protected void loginUser(){
        if(SmartFridge.client.isConnected()) {
            SmartFridge.client.sendMessage(new Datapackage("LOGIN", usernameLogin.getText(), passwordLogin.getText()));
            SmartFridge.loggedIn = true;
            SmartFridge.USERNAME = usernameLogin.getText();
        }
        else{
            Animation.startAnimation(new Runnable() {
                @Override
                public void run() {
                    try {
                        serverOnline.setVisible(true);
                        Thread.sleep(3 * 1000);
                        serverOnline.setVisible(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
