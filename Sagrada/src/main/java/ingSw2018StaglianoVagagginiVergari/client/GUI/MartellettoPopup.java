package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class MartellettoPopup {

    private Pane root;
    private Button relaunchButton;
    private Stage stage;

    public MartellettoPopup(){
        try {
            root = FXMLLoader.load(getClass().getResource("/MartellettoPopup.fxml"));
            relaunchButton = (Button) root.lookup("#relaunchButton");
            relaunchButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (SagradaGUI.getRequestHandler().getDataGameObserver().isCurrentPlayer()){
                        SagradaGUI.getRequestHandler().usoCartaUtensileRequest(new ArrayList<>());
                        stage.close();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display(){
        stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
