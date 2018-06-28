package ingSw2018StaglianoVagagginiVergari.model;

import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class DadoTest {

    @Test
    public void getNumero() {
        Dado d=new Dado("Rosso");
        assertTrue(d.getNumero()>=1 && d.getNumero()<=6);
    }

    @Test
    public void getColore() {

        Dado d=new Dado("Rosso");
        assertTrue(d.getColore()=="Rosso");
    }

    @Test
    public void lanciaDado() {
        Dado d=new Dado("Rosso");
        assertTrue(d.getNumero()>=1 && d.getNumero()<=6);
    }

    @Test
    public void setNumero(){
        Dado d=new Dado("Rosso",1);
        d.setNumero(3);
        assertTrue(d.getNumero()==3);
    }

    @Test
    public void setColore(){
        Dado d=new Dado("Rosso",1);
        d.setColore("Blu");
        assertTrue(d.getColore().equals("Blu"));

    }

}