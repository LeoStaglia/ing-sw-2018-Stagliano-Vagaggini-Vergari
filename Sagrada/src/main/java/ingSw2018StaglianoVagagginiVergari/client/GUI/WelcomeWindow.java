package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class WelcomeWindow{

    private Pane root;
    private Button newGameButton;
    private Button loginButton;

    public WelcomeWindow() throws IOException{
        root = FXMLLoader.load(getClass().getResource("/welcomeScene.fxml"));
        newGameButton = (Button)root.lookup("#newGameButton");
        newGameButton.setOnAction((ActionEvent e)->TransitionHandler.toNewGameScene());
        loginButton = (Button)root.lookup("#loginButton");
        loginButton.setOnAction((ActionEvent e)->TransitionHandler.toLoginScene());

    }
    public Parent getRoot(){
        return root;
    }
}
