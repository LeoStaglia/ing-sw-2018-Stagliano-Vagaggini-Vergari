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
        numero=random.nextInt(6)+1;  //
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

    public void incrementa(){
        if(this.numero!=6)  this.numero = this.numero + 1;
    }

    public void decrementa(){
        if(this.numero!=1)  this.numero=this.numero-1;
    }
}