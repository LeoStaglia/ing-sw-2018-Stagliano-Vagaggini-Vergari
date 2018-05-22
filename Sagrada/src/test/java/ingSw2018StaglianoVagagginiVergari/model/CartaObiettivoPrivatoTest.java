package ingSw2018StaglianoVagagginiVergari.model;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPrivato;
import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartaObiettivoPrivatoTest {
    @Test
    void getColore() {
        CartaObiettivoPrivato c = new CartaObiettivoPrivato(Constraint.GIALLO);
        assertTrue("Giallo".equals(c.getColore().getDescrizione()));

    }

}