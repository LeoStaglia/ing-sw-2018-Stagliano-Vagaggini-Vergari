package ingSw2018StaglianoVagagginiVergari.model;

public class Plancia {
    private String colore;
    private CartaSchema cartaSchema;
    private Dado[][] grigliaGioco;

    public Plancia(String colore){
        grigliaGioco = new Dado[4][5];
        this.colore=colore;
    }

    /*public String getColore() {
        return colore;
    }*/

    public void inserisciCartaSchema(CartaSchema cartaSchema){
        this.cartaSchema=cartaSchema;
    }
    public void piazzaDado(int i, int j, Dado d){
        grigliaGioco[i][j] = d; //alternative implementation: assign the actual object instead of creating a copy
    }
    public void rimuoviDado(int i, int j){
        grigliaGioco[i][j] = null;
    }
    public Dado leggiPlancia(int i, int j){
        return grigliaGioco[i][j];
    }

}
