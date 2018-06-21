package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.LoadingState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import ingSw2018StaglianoVagagginiVergari.client.GUI.Tool12Popup;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class TaglierinaManualeState implements GUIState{

    private GameScene gameScene;
    private boolean fase1;
    private Integer indiceRoundTrack;
    private ArrayList<Integer> parametriController;
    private Integer numeroSpostamenti;
    private Integer numeroRoundTrack;
    private ArrayList<Integer> coordinbateDado;
    private ArrayList<Integer> coordinateSpostamento;
    private Tool12Popup popup;

    public TaglierinaManualeState(GameScene gameScene, boolean fase1, int numeroRoundTrack){
        this.gameScene=gameScene;
        this.fase1=fase1;
        parametriController=new ArrayList<>();
        this.numeroRoundTrack=numeroRoundTrack;
    }


    @Override
    public void handle(MouseEvent event) {
        if (fase1){
            if (!provenienzaRoundTrack(event) && numeroSpostamenti==null && indiceRoundTrack==null){
                new AlertPopup().display("Attenzione", "Seleziona un dado dal tracciato del round.");
            }else if (provenienzaRoundTrack(event) && numeroSpostamenti==null && indiceRoundTrack==null){
                indiceRoundTrack=indexRoundTrackDice(event);
                popup = new Tool12Popup();
                popup.display();
            }else{
                if (!provenienzaGriglia(event)){
                    new AlertPopup().display("Attenzione", "Sposta il dado!");
                }else{
                    if (coordinbateDado==null){
                        coordinbateDado=indexGrid(event);
                    }else{
                        coordinateSpostamento=indexGrid(event);
                    }
                }
            }
            if (indiceRoundTrack!=null && coordinbateDado!=null && coordinateSpostamento!=null) {
                parametriController.clear();
                parametriController.add(indiceRoundTrack+1);
                parametriController.add(popup.getNumeroSpostamenti());
                parametriController.addAll(coordinbateDado);
                parametriController.addAll(coordinateSpostamento);
                gameScene.setState(new LoadingState());
                new TaglierinaManualePhaseHandler(gameScene).start();
                SagradaGUI.getRequestHandler().usoCartaUtensileRequest(parametriController);
            }
        }else{
            if (!provenienzaGriglia(event)){
                new AlertPopup().display("Attenzione", "Sposta il dado!");
            }else{
                if (coordinbateDado==null){
                    coordinbateDado=indexGrid(event);
                }else{
                    coordinateSpostamento=indexGrid(event);
                }
            }
            if (coordinateSpostamento!=null && coordinbateDado!=null) {
                gameScene.setState(new LoadingState());
                parametriController.add(numeroRoundTrack);
                parametriController.add(1);
                parametriController.addAll(coordinbateDado);
                parametriController.addAll(coordinateSpostamento);
                SagradaGUI.getRequestHandler().usoCartaUtensileRequest(parametriController);
            }

        }
    }

    private boolean provenienzaRoundTrack(MouseEvent event){
        for (Node n:gameScene.getRoundTrack().getChildren()){
            if (n==event.getSource()){
                return true;
            }
        }
        return false;
    }

    private int indexRoundTrackDice(MouseEvent event){
        int result = 0;
        for (Node n:gameScene.getRoundTrack().getChildren()){
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
}
