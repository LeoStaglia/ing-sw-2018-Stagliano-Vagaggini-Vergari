package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GrigliaSchema implements GUIElement{

    private Pane root;
    private Label schemeName;
    private Label difficoltaSchema;
    private GridPane schemeGrid;
    private VBox mouseEffectNode;


    public GrigliaSchema() throws IOException{
        root = FXMLLoader.load(getClass().getResource("/grigliaSchema.fxml"));
        schemeName = (Label)root.lookup("#schemeName");
        difficoltaSchema = (Label)root.lookup("#difficoltaSchema");
        schemeGrid = (GridPane)root.lookup("#schemeGrid");
        mouseEffectNode = (VBox)root.lookup("#mouseEffectNode");

    }
    public void render(String[][] constraints, int difficolta, String nome){
        GraphicRenderer.renderSchemeGrid(constraints, schemeGrid);
        difficoltaSchema.setText(Integer.toString(difficolta));
        schemeName.setText(nome);
        schemeGrid.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseEffectNode.setStyle("-fx-border-color: dodgerblue");
            }
        });
        schemeGrid.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseEffectNode.setStyle("-fx-border-color: none");
            }
        });
    }

    public Pane getRoot() {
        return root;
    }
}
