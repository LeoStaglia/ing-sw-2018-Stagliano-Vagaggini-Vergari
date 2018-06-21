package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PointsScene {

    private Pane root;
    private ArrayList<Pane> areaPunteggi;

    public PointsScene(){
        try {
            root = FXMLLoader.load(getClass().getResource("/punteggio.fxml"));
            areaPunteggi = new ArrayList<>();
            areaPunteggi.add((Pane)root.lookup("#areaPunteggioGiocatore1"));
            areaPunteggi.add((Pane)root.lookup("#areaPunteggioGiocatore2"));
            areaPunteggi.add((Pane)root.lookup("#areaPunteggioGiocatore3"));
            areaPunteggi.add((Pane)root.lookup("#areaPunteggioGiocatore4"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderPunteggiGiocatore(HashMap<String, Integer> punteggiGiocatore, String vincitore){
        int i=0;
        for (String giocatore:punteggiGiocatore.keySet()){
            Pane area = areaPunteggi.get(i);
            PointsArea boxPunteggio = new PointsArea(giocatore, punteggiGiocatore.get(giocatore), giocatore.equals(vincitore));
            area.getChildren().add(boxPunteggio.getRoot());
            i++;
        }
    }

    public Pane getRoot() {
        return root;
    }
}
