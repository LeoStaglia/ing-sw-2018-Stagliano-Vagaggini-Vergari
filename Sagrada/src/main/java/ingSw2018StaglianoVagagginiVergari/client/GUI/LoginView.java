package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginView {
    private Pane root;
    private Button loginButton;
    private TextField usernameTextField;
    private Button backButton;

    public LoginView(){
        try {
            root = FXMLLoader.load(getClass().getResource("/loginView.fxml"));
            loginButton = (Button) root.lookup("#loginButton");
            usernameTextField = (TextField) root.lookup("#usernameTextField");
            backButton = (Button) root.lookup("#backButton");
            backButton.setOnAction((ActionEvent e)->TransitionHandler.toWelcomeWindow());
            loginButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    TransitionHandler.toLoadingScene();
                    SagradaGUI.getRequestHandler().login(usernameTextField.getText());
                    usernameTextField.setText("");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pane getRoot() {
        return root;
    }
}
