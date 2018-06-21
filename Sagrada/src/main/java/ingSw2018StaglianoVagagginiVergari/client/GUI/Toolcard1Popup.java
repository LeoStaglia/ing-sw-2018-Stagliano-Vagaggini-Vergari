package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Stack;

public class Toolcard1Popup {

    Pane root;
    StackPane plusButton;
    StackPane minusButton;
    StackPane dieArea;
    Stage stage;

    public Toolcard1Popup(){
        stage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("/toolcard1Popup.fxml"));
            plusButton = (StackPane) root.lookup("#plus");
            plusButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    plusButton.setStyle("-fx-border-color: blue");
                }
            });
            plusButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    plusButton.setStyle("-fx-border-color: none");
                }
            });
            minusButton = (StackPane)root.lookup("#minus");
            minusButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    minusButton.setStyle("-fx-border-color: blue");
                }
            });
            minusButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    minusButton.setStyle("-fx-border-color: none");
                }
            });
            dieArea=(StackPane)root.lookup("#dieArea");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDie(ImageView die){
        dieArea.getChildren().add(die);
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
    public StackPane getPlusButton(){
        return plusButton;
    }

    public StackPane getMinusButton() {
        return minusButton;
    }

    public Stage getStage() {
        return stage;
    }
}
