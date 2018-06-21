package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class RoundTrack implements GUIElement{
    private Pane root;
    private HBox roundTrack;

    public RoundTrack() throws IOException {
        root= FXMLLoader.load(getClass().getResource("/roundTrack.fxml"));
        roundTrack = (HBox) root.lookup("#roundTrack");

    }

    @Override
    public Pane getRoot() {
        return root;
    }

    public HBox getRoundTrack() {
        return roundTrack;
    }
}
