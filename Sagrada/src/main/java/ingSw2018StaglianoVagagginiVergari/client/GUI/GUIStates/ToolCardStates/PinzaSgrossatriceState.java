package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.*;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.LoadingState;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.PinzaSgrossatrice;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class PinzaSgrossatriceState implements GUIState{

    GameScene gameScene;
    Integer dadoRiserva;
    ArrayList<Integer> parametriController;

    public PinzaSgrossatriceState(GameScene gameScene){
        this.gameScene=gameScene;
        parametriController=new ArrayList<>();
    }

    @Override
    public void handle(MouseEvent event) {
        if (!provenienzaRiserva(event)){
            new AlertPopup().display("Attenzione", "Scegli un dado dalla riserva.");
        }else{
            if (dadoRiserva==null){
                dadoRiserva=indexReserveDice(event);
                parametriController.add(dadoRiserva);
            }
            Toolcard1Popup popup = new Toolcard1Popup();
            popup.setDie(GraphicRenderer.renderDie(SagradaGUI.getRequestHandler().getDataGameObserver().getDadiRiserva().get(dadoRiserva)));
            popup.getPlusButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (gameScene.getState()==PinzaSgrossatriceState.this) {
                        parametriController.add(1);
                        popup.getStage().close();
                        SagradaGUI.getRequestHandler().usoCartaUtensileRequest(parametriController);
                    }else{
                        new AlertPopup().display("Attenzione", "Non sei più il giocatore corrente!");
                        popup.getStage().close();
                    }
                }
            });
            popup.getMinusButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (gameScene.getState() == PinzaSgrossatriceState.this) {
                        parametriController.add(-1);
                        popup.getStage().close();
                        SagradaGUI.getRequestHandler().usoCartaUtensileRequest(parametriController);
                    }else{
                        new AlertPopup().display("Attenzione", "Non sei più il giocatore corrente!");
                        popup.getStage().close();
                    }

                }
            });
            popup.display();

        }
    }

    private boolean provenienzaRiserva(MouseEvent event){
        for (Node n:gameScene.getRiserva().getChildren()){
            if (n==event.getSource()){
                return true;
            }
        }
        return false;
    }

    private int indexReserveDice(MouseEvent event){
        int result=0;
        for (Node n:gameScene.getRiserva().getChildren()){
            if (n==event.getSource()){
                return result;
            }
            result++;
        }
        return result;
    }

}
