package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PlayerGrid implements GUIElement{

    private Pane root;
    private GridPane playableGrid;
    private Label playerName;
    private Label isCurrentPlayer;
    private Label numeroSegnalini;

    public PlayerGrid() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/grigliaGioco.fxml"));
        playerName = (Label)root.lookup("#playerName");
        playableGrid = (GridPane)root.lookup("#playableGrid");
        isCurrentPlayer = (Label)root.lookup("#isCurrentPlayer");
        numeroSegnalini = (Label)root.lookup("#numeroSegnalini");

    }


    @Override
    public Pane getRoot() {
        return root;
    }

    public GridPane getPlayableGrid() {
        return playableGrid;
    }
    public void setPlayerName(String playerName){
        this.playerName.setText(playerName);
    }
    public void setCurrent(){
        isCurrentPlayer.setText("Corrente");
    }
    public void setNotCurrent(){
        isCurrentPlayer.setText("");
    }
    public void setNumeroSegnalini(int numeroSegnalini){
        this.numeroSegnalini.setText(Integer.toString(numeroSegnalini));
    }
}
