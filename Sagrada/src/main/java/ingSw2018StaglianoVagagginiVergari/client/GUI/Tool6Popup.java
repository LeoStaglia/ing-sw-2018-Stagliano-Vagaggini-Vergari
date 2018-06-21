package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Tool6Popup {

    private Pane root;

    private StackPane dieArea;


    public Tool6Popup(){
        try {
            root = FXMLLoader.load(getClass().getResource("/tool6Popup.fxml"));
            dieArea = (StackPane) root.lookup("#dieArea");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void display(){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    public void setDie(ImageView die){
        dieArea.getChildren().add(die);
    }
}
