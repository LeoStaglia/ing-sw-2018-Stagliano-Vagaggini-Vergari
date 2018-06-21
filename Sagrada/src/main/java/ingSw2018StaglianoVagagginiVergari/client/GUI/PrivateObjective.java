package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class PrivateObjective{

    StackPane root;
    ImageView privateObjectiveImage;

    public PrivateObjective() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Objective.fxml"));
        privateObjectiveImage = (ImageView) root.lookup("#objectiveImage");

    }

    public ImageView getPrivateObjectiveImage() {
        return privateObjectiveImage;
    }

    public StackPane getRoot() {
        return root;
    }
}
