package ingSw2018StaglianoVagagginiVergari.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartaObiettivoPrivatoTest {
    @Test
    void getColore() {
        CartaObiettivoPrivato c = new CartaObiettivoPrivato(Constraint.GIALLO);
        assertTrue("Giallo".equals(c.getColore().getDescrizione()));

    }

}