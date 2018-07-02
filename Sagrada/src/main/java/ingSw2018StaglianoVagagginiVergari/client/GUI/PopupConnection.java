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

public class PopupConnection {

    private Pane root;

    private Button rmiButton;

    private Button socketButton;

    private Stage stage;

    public PopupConnection(){
        try {
            root = FXMLLoader.load(getClass().getResource("/TypeOfConnection.fxml"));

            rmiButton= (Button)root.lookup("#rmiButton");

            socketButton = (Button)root.lookup("#socketButton");

            rmiButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    SagradaGUI.getRequestHandler().setRMI(true);
                    SagradaGUI.getRequestHandler().setController();
                    stage.close();
                }
            });

            socketButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    SagradaGUI.getRequestHandler().setRMI(false);
                    SagradaGUI.getRequestHandler().setController();
                    stage.close();
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
