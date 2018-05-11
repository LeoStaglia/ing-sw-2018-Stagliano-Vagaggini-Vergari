package ingSw2018StaglianoVagagginiVergari.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.SortedSet;

public class Utente implements Serializable{

    //here we use an array for a future implementation of single player mode;
    private ArrayList<CartaObiettivoPrivato> obiettivoPrivato;
    private Plancia plancia;
    private int segnalini;
    private String id;
    private static HashSet<String> idSet = new HashSet<>();

    public String getId() {
        return id;
    }

    //multiplayer constructor
    public Utente(Plancia plancia, Constraint coloreObiettivoPrivato){
        for (String id: idSet){
           this.id =id;
           idSet.remove(id);
           break;
        }
        this.plancia=plancia;
        obiettivoPrivato= new ArrayList<>();
        obiettivoPrivato.add(new CartaObiettivoPrivato(coloreObiettivoPrivato));

    }
    //singleplayer constructor
    public Utente(Plancia plancia, Constraint coloreObiettivoPrivato1, Constraint coloreObiettivoPrivato2){
        for (String id: idSet){
            this.id =id;
            idSet.remove(id);
            break;
        }
        this.plancia=plancia;
        obiettivoPrivato= new ArrayList<>();
        obiettivoPrivato.add(new CartaObiettivoPrivato(coloreObiettivoPrivato1));
        obiettivoPrivato.add(new CartaObiettivoPrivato(coloreObiettivoPrivato2));


    }

    public ArrayList<CartaObiettivoPrivato> getObiettivoPrivato() {
        ArrayList<CartaObiettivoPrivato> risultato=new ArrayList<>();
        risultato.addAll(obiettivoPrivato);
        return risultato;
    }
   public void scegliFacciaSchema(boolean fronteScelto){

        plancia.getCartaSchema().scegliFaccia(fronteScelto);
        if (fronteScelto){
            setSegnalini(plancia.getCartaSchema().getDifficoltaFronte());
        }else{
            setSegnalini(plancia.getCartaSchema().getDifficoltaRetro());
        }


    }
    public Plancia getPlancia(){
        return plancia;
    }

    public int getSegnalini() {
        return segnalini;
    }

    private void setSegnalini(int difficolta){
        this.segnalini=difficolta;
    }

    public static void inizializzaIdSet(){
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random ran = new Random();
        while(idSet.size()<=4){
            StringBuilder builder = new StringBuilder();
            for (int j=0; j<13; j++){
                builder.append(alfabeto.charAt(ran.nextInt(alfabeto.length())));
            }
            idSet.add(builder.toString());
        }

    }

}
