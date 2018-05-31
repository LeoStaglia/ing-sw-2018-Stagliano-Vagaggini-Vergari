package ingSw2018StaglianoVagagginiVergari.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Utente implements Serializable{

    //here we use an array for a future implementation of single player mode;
    private ArrayList<CartaObiettivoPrivato> obiettivoPrivato;
    private Plancia plancia;
    private int segnalini;
    private String id;
    private String token;
    private static HashSet<String> tokenSet = new HashSet<>();

    public String getId() {
        return id;
    }

    //multiplayer constructor
    public Utente(Plancia plancia, Constraint coloreObiettivoPrivato){
        this.plancia=plancia;
        for (String token: tokenSet){
            this.token =token;
            tokenSet.remove(token);
            break;
        }
        obiettivoPrivato= new ArrayList<>();
        obiettivoPrivato.add(new CartaObiettivoPrivato(coloreObiettivoPrivato));

    }
    //singleplayer constructor
    public Utente(Plancia plancia, Constraint coloreObiettivoPrivato1, Constraint coloreObiettivoPrivato2){
        for (String token: tokenSet){
            this.token =token;
            tokenSet.remove(token);
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

    public void setSegnalini(int difficolta){
        this.segnalini=difficolta;
    }

    public static void inizializzaTokenSet(){
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random ran = new Random();
        while(tokenSet.size()<=4){
            StringBuilder builder = new StringBuilder();
            for (int j=0; j<4; j++){
                builder.append(alfabeto.charAt(ran.nextInt(alfabeto.length())));
            }
            tokenSet.add(builder.toString());
        }

    }


    public void setId(String username) {
        this.id = username;
    }

    public String getToken() {
        return token;
    }
}
