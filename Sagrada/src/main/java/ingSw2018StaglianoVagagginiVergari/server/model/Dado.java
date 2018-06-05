package ingSw2018StaglianoVagagginiVergari.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dado implements Serializable{
    private int numero;  // value of the die
    private String colore; // color of the die
    private Random random= new Random();



    public Dado(String colore){
        numero=random.nextInt(6)+1;
        this.colore=colore;
    }

    public Dado(String colore, int numero){
        this.numero=numero;
        this.colore=colore;
    }

    public int getNumero() {
        return numero;
    }

    public String getColore() {

        return colore;
    }

    public void lanciaDado(){                        //
       this.numero=random.nextInt(6)+1;
    }

    public boolean incrementa(){

        if(this.numero!=6)  {
            this.numero = this.numero + 1 ;
            return true;
        }
        return false;
    }

    public boolean decrementa(){
        if(this.numero!=1) {
            this.numero = this.numero - 1;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.getNumero());
        result.append(this.getColore().toLowerCase());
        return result.toString();
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }
}
