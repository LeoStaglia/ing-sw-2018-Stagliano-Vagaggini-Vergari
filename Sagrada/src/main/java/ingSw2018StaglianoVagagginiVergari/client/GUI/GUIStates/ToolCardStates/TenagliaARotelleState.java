package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.DefaultState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.LoadingState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import ingSw2018StaglianoVagagginiVergari.client.GUI.TenagliaARotellePopup;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class TenagliaARotelleState implements GUIState{

    private GameScene gameScene;

    private ArrayList<Integer> parametriController;

    public TenagliaARotelleState(GameScene gameScene){
        this.gameScene=gameScene;
        parametriController=new ArrayList<>();
        gameScene.setState(new LoadingState());
        Platform.runLater(()->new TenagliaARotellePopup().display("Attenzione"));
    }

    @Override
    public void handle(MouseEvent event) {

    }

}
