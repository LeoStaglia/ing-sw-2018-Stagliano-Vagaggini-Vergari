package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class NotCurrentState implements EventHandler<MouseEvent>, GUIState {

    GameScene gameScene;

    public NotCurrentState(GameScene gameScene){
        this.gameScene=gameScene;
    }

    @Override
    public void handle(MouseEvent event) {
        new AlertPopup().display("Attenzione!", "Non sei il giocatore corrente");
    }
}
