package ingSw2018StaglianoVagagginiVergari.client.GUI;

import ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests.RemoteRequestHandler;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.CartaSchema;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static ingSw2018StaglianoVagagginiVergari.client.GUI.GraphicRenderer.scale;
import static ingSw2018StaglianoVagagginiVergari.client.GUI.GraphicRenderer.translate;

public class SceltaSchema {

    private Pane root;
    private GridPane schemesGrid;
    private Pane privateObjectiveArea;
    private final double scaleFactor=0.6;
    public boolean schemaScelto;

    public SceltaSchema() throws IOException{
        root= FXMLLoader.load(getClass().getResource("/sceltaSchema.fxml"));
        schemesGrid = (GridPane)root.lookup("#schemesGrid");
        privateObjectiveArea = (Pane)root.lookup("#privateObjectiveArea");
        render();
    }

    public void render(){
        try {
            renderSchemesGrid(schemesGrid);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderSchemesGrid(GridPane schemesGrid) throws IOException {
        GrigliaSchema grigliaSchema1 = new GrigliaSchema();
        grigliaSchema1.render(SagradaGUI.getRequestHandler().getDataGameObserver().getSchemaFronte1(), SagradaGUI.getRequestHandler().getDataGameObserver().getDifficoltaCarteSchema()[0], SagradaGUI.getRequestHandler().getDataGameObserver().getNomeCarteSchema()[0]);
        grigliaSchema1.getRoot().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!schemaScelto){
                    SagradaGUI.getRequestHandler().scegliSchema(true, true);
                    schemaScelto=true;
                }else{
                    new AlertPopup().display("Attenzione", "Hai già scelto lo schema per la partita.");
                }
            }
        });
        scale(grigliaSchema1, scaleFactor);
        translate(grigliaSchema1, 0.5);
        schemesGrid.add(grigliaSchema1.getRoot(), 0,0 );


        GrigliaSchema grigliaSchema2 = new GrigliaSchema();
        grigliaSchema2.render(SagradaGUI.getRequestHandler().getDataGameObserver().getSchemaRetro1(), SagradaGUI.getRequestHandler().getDataGameObserver().getDifficoltaCarteSchema()[1], SagradaGUI.getRequestHandler().getDataGameObserver().getNomeCarteSchema()[1]);
        grigliaSchema2.getRoot().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!schemaScelto){
                    SagradaGUI.getRequestHandler().scegliSchema(true, false);
                    schemaScelto=true;
                }else{
                    new AlertPopup().display("Attenzione", "Hai già scelto lo schema per la partita.");
                }
            }
        });
        scale(grigliaSchema2, scaleFactor);
        translate(grigliaSchema2, 0.5);
        schemesGrid.add(grigliaSchema2.getRoot(), 1,0 );

        GrigliaSchema grigliaSchema3 = new GrigliaSchema();
        grigliaSchema3.render(SagradaGUI.getRequestHandler().getDataGameObserver().getSchemaFronte2(), SagradaGUI.getRequestHandler().getDataGameObserver().getDifficoltaCarteSchema()[2], SagradaGUI.getRequestHandler().getDataGameObserver().getNomeCarteSchema()[2]);
        grigliaSchema3.getRoot().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!schemaScelto){
                    SagradaGUI.getRequestHandler().scegliSchema(false, true);
                    schemaScelto=true;
                }else{
                    new AlertPopup().display("Attenzione", "Hai già scelto lo schema per la partita.");
                }
            }
        });
        scale(grigliaSchema3, scaleFactor);
        translate(grigliaSchema3, 0.5);
        schemesGrid.add(grigliaSchema3.getRoot(), 0,1);

        GrigliaSchema grigliaSchema4 = new GrigliaSchema();
        grigliaSchema4.render(SagradaGUI.getRequestHandler().getDataGameObserver().getSchemaRetro2(), SagradaGUI.getRequestHandler().getDataGameObserver().getDifficoltaCarteSchema()[3], SagradaGUI.getRequestHandler().getDataGameObserver().getNomeCarteSchema()[3]);
        grigliaSchema4.getRoot().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!schemaScelto){
                    SagradaGUI.getRequestHandler().scegliSchema(false, false);
                    schemaScelto=true;
                }else{
                    new AlertPopup().display("Attenzione", "Hai già scelto lo schema per questa partita");
                }
            }
        });
        scale(grigliaSchema4, scaleFactor);
        translate(grigliaSchema4, 0.5);
        schemesGrid.add(grigliaSchema4.getRoot(), 1,1 );
        PrivateObjective privateObjective = new PrivateObjective();
        GraphicRenderer.privateObjectiveRender(privateObjective);
        privateObjective.getRoot().setScaleX(0.6);
        privateObjective.getRoot().setScaleY(0.6);
        privateObjectiveArea.getChildren().add(privateObjective.getRoot());

    }


    public Pane getRoot() {
        return root;
    }

}
