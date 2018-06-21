package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Tool12Popup {

    private Stage stage;
    private Pane root;
    private Button sceltaButton;
    private ChoiceBox<Integer> numeroDadi;
    private int numeroSpostamenti;

    public Tool12Popup(){
        try {
            root = FXMLLoader.load(getClass().getResource("/tool12Popup.fxml"));
            sceltaButton = (Button) root.lookup("#sceltaButton");
            numeroDadi= (ChoiceBox<Integer>) root.lookup("#numeroDadi");
            numeroDadi.getItems().addAll(1,2);
            numeroDadi.setValue(1);
            sceltaButton.setOnAction(this::tastoScegliHandler);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tastoScegliHandler(ActionEvent event){
        synchronized (SagradaGUI.getRequestHandler().getDataGameObserver()) {
            numeroSpostamenti =  numeroDadi.getValue();
            stage.close();
            SagradaGUI.getRequestHandler().getDataGameObserver().notifyAll();
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

    public int getNumeroSpostamenti() {
        return numeroSpostamenti;
    }
}
