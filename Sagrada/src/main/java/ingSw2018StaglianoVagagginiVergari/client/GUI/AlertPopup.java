package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class AlertPopup {


    private Pane root;
    private Label messageLabel;
    private Button closeButton;

    public AlertPopup() {
        try {
            root=FXMLLoader.load(getClass().getResource("/AlertPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        messageLabel = (Label)root.lookup("#messageLabel");
        closeButton = (Button)root.lookup("#closeButton");

    }

    /** Displays the graphical user interface */
    public void display(String title, String message){
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        messageLabel.setText(message);
        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

}
