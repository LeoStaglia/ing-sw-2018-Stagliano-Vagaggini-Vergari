package ingSw2018StaglianoVagagginiVergari.model;

import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DadoTest {

    @Test
    void getNumero() {
        Dado d=new Dado("Rosso");
        assertTrue(d.getNumero()>=1 && d.getNumero()<=6);
    }

    @Test
    void getColore() {

        Dado d=new Dado("Rosso");
        assertTrue(d.getColore()=="Rosso");
    }

    @Test
    void lanciaDado() {
        Dado d=new Dado("Rosso");
        assertTrue(d.getNumero()>=1 && d.getNumero()<=6);
    }

}