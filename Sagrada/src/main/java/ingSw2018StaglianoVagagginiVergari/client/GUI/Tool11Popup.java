package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class Tool11Popup {

    private Pane root;
    private StackPane dieArea;
    private ChoiceBox<Integer> sceltaNumero;
    private Button tastoScegli;
    private Stage stage;
    private Integer valoreDado;


    public Tool11Popup(){

        try {
            root= FXMLLoader.load(getClass().getResource("/tool11Popup.fxml"));
            dieArea = (StackPane) root.lookup("#dieArea");
            sceltaNumero = (ChoiceBox)root.lookup("#sceltaNumero");
            sceltaNumero.getItems().addAll(1, 2, 3, 4, 5, 6);
            sceltaNumero.setValue(1);
            tastoScegli=(Button)root.lookup("#tastoScegli");
            tastoScegli.setOnAction((ActionEvent event)->tastoScegliHandler(event));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setDie(ImageView die){
        dieArea.getChildren().add(die);
    }

    public void tastoScegliHandler(ActionEvent event){
        synchronized (SagradaGUI.getRequestHandler().getDataGameObserver()) {
            valoreDado =  sceltaNumero.getValue();
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

    public Integer getValoreDado() {
        return valoreDado;
    }
}
