package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import javafx.scene.input.MouseEvent;

public class LoadingState implements GUIState {

    @Override
    public void handle(MouseEvent event) {
        new AlertPopup().display("Attendi..", "Attendi che la richiesta venga processata");
    }
}
