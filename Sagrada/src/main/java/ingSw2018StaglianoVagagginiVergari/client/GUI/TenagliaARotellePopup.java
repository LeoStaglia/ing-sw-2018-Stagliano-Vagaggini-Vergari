package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class TenagliaARotellePopup {

    private Pane root;
    private Button relaunchButton;
    private Stage stage;

    public TenagliaARotellePopup(){
        try {
            root = FXMLLoader.load(getClass().getResource("/TenagliaARotellePopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display(String title){
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                SagradaGUI.getRequestHandler().usoCartaUtensileRequest(new ArrayList<>());
            }
        });
        stage.setScene(scene);
        stage.show();
    }
}
