package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.DefaultState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.LoadingState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.MartellettoPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class MartellettoState implements GUIState{

    private GameScene gameScene;


    public MartellettoState(GameScene gameScene){
        this.gameScene=gameScene;
        Platform.runLater(()->new MartellettoPopup().display());
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
