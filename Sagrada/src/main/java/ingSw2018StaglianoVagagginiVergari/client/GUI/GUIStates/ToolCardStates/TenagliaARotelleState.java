package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.DefaultState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.LoadingState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class TenagliaARotelleState implements GUIState{

    private GameScene gameScene;

    private Integer indiceRiserva;

    private ArrayList<Integer> indiciGriglia;

    private ArrayList<Integer> parametriController;

    public TenagliaARotelleState(GameScene gameScene){
        this.gameScene=gameScene;
        parametriController=new ArrayList<>();
        SagradaGUI.getRequestHandler().usoCartaUtensileRequest(null);
        gameScene.setState(new DefaultState(gameScene, SagradaGUI.getRequestHandler().getDataGameObserver().getAzioniGiocatore()));

    }

    @Override
    public void handle(MouseEvent event) {

    }

}
