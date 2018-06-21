package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class OtherBoards {

    private Pane root;
    private GridPane gridPlayerGrids;
    private HashMap<String, String[][]> planceGiocatori;
    private Button backButton;

    public OtherBoards() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/otherBoards.fxml"));
        gridPlayerGrids = (GridPane) root.lookup("#gridPlayerGrids");
        backButton = (Button) root.lookup("#backButton");
        backButton.setOnAction((ActionEvent event)->TransitionHandler.toGameScene());
    }

    public void setPlanceGiocatori(HashMap<String, String[][]> planceGiocatori) {
        this.planceGiocatori = planceGiocatori;
    }

    public void render(){
        int i=0, j=0;
       for (String player:planceGiocatori.keySet()){
           try {
               PlayerGrid playerGrid = new PlayerGrid();
               playerGrid.setPlayerName(player);
               GraphicRenderer.renderPlayerGrid(planceGiocatori.get(player), playerGrid.getPlayableGrid());
               GraphicRenderer.scale(playerGrid, 0.45);
               gridPlayerGrids.add(playerGrid.getRoot(), j, i);
               if (j==1){
                   j=0;
                   i++;
               }else{
                   j++;
               }
           } catch (IOException e) {
               e.printStackTrace();
           }

       }
    }

    public Pane getRoot() {
        return root;
    }
}
