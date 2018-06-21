package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.LoadingState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class DiluentePerPastaSaldaState implements GUIState{

    private GameScene gameScene;
    private boolean fase1;
    private Integer indiceDado;
    private ArrayList<Integer> listaParametri;
    private Integer valoreDado;

    public DiluentePerPastaSaldaState(GameScene gameScene, boolean fase1){
        this.gameScene=gameScene;
        listaParametri=new ArrayList<>();
        this.fase1=fase1;
        if (!fase1){
            listaParametri.clear();
            listaParametri.add(2);
            int i;
            for (i=0; i<3; i++) {
                if (SagradaGUI.getRequestHandler().getDataGameObserver().cartaUtensile(i).equals("Diluente Per Pasta Salda")) {
                    break;
                }
            }
            listaParametri.add(i);
            SagradaGUI.getRequestHandler().genericGameRequest(listaParametri);
        }
    }

    @Override
    public void handle(MouseEvent event) {
        if (fase1){
            if (!provenienzaRiserva(event)) {
                new AlertPopup().display("Attenzione", "Seleziona un dado dalla riserva!");
            }else{
                indiceDado = indexReserveDice(event);
                listaParametri.add(indiceDado);
                SagradaGUI.getRequestHandler().usoCartaUtensileRequest(listaParametri);
                gameScene.setState(new LoadingState());
            }
        }else{
            if (!provenienzaGriglia(event)){
                new AlertPopup().display("Attenzione", "Piazza il dado che hai pescato!");
            }else{
                listaParametri.clear();
                listaParametri.add(valoreDado);
                listaParametri.addAll(indexGrid(event));
                SagradaGUI.getRequestHandler().usoCartaUtensileRequest(listaParametri);
                gameScene.setState(new LoadingState());
            }
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

    private boolean provenienzaGriglia(MouseEvent event){

        for(Node n:gameScene.getPlayerGrid().getChildren()){
            if (n==event.getSource()){
                return true;
            }
        }
        return false;
    }

    private ArrayList<Integer> indexGrid(MouseEvent event){
        ArrayList<Integer> result = new ArrayList<>();
        for (Node n:gameScene.getPlayerGrid().getChildren()){
            if (n==event.getSource()){
                result.add(GridPane.getRowIndex(n));
                result.add(GridPane.getColumnIndex(n));
                break;
            }

        }

        return result;
    }

    public void setValoreDado(int valore){
        valoreDado=valore;
    }
}
