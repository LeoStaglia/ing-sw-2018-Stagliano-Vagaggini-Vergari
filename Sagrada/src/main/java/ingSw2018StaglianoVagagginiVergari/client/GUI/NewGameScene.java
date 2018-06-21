package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class NewGameScene {

    private Pane root;
    private Button newGameButton;
    private TextField usernameTextField;
    private Button backButton;

    public NewGameScene() throws IOException{
        root = FXMLLoader.load(getClass().getResource("/newGameScene.fxml"));
        newGameButton = (Button)root.lookup("#newGameButton");
        usernameTextField = (TextField)root.lookup("#usernametextField");
        backButton = (Button)root.lookup("#backButton");
        backButton.setOnAction((ActionEvent event)->TransitionHandler.toWelcomeWindow());
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (usernameTextField.getText().length()>0){
                    SagradaGUI.getRequestHandler().partecipaPartita(usernameTextField.getText());
                }else{
                    new AlertPopup().display("Attenzione", "Inserisci un nome valido");
                }

            }
        });
    }

    public Parent getRoot() {
        return root;
    }
}
