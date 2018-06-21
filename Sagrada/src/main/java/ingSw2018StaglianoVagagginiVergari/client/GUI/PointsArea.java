package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PointsArea {

    private Pane root;
    private Label nomeGiocatore;
    private Label puntiGiocatore;
    private Label labelVincitore;

    public PointsArea(String username, int punti, boolean vincitore){
        try {
            root = FXMLLoader.load(getClass().getResource("/punteggioGiocatore.fxml"));
            nomeGiocatore=(Label)root.lookup("#nomeGiocatore");
            puntiGiocatore=(Label)root.lookup("#puntiGiocatore");
            labelVincitore=(Label)root.lookup("#labelVincitore");
            if (!vincitore){
                labelVincitore.setVisible(false);
            }
            nomeGiocatore.setText(username);
            puntiGiocatore.setText(Integer.toString(punti));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pane getRoot() {
        return root;
    }
}
