package ingSw2018StaglianoVagagginiVergari.model;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPrivato;
import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class CartaObiettivoPrivatoTest {
    @Test
    public void getColore() {
        CartaObiettivoPrivato c = new CartaObiettivoPrivato(Constraint.GIALLO);
        assertTrue("Giallo".equals(c.getColore().getDescrizione()));
        System.out.println("toString: "+c.toString()
        );

    }

}