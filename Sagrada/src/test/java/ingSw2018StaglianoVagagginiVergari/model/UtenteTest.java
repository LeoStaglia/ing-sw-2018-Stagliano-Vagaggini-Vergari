package ingSw2018StaglianoVagagginiVergari.model;

import org.junit.jupiter.api.Test;
import  static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {

    @Test
    void getObiettivoPrivatoMultiplayer() {
        Plancia p = mock(Plancia.class);
        Utente uMultiplayer = new Utente(p, Constraint.GIALLO);
        assertTrue(uMultiplayer.getObiettivoPrivato().get(0).getColore()==Constraint.GIALLO);

    }
    @Test
    void getObiettivoPrivatoSingleiplayer() {
        Plancia p = mock(Plancia.class);
        Utente uMultiplayer = new Utente(p, Constraint.GIALLO, Constraint.ROSSO);
        assertTrue(uMultiplayer.getObiettivoPrivato().get(0).getColore()==Constraint.GIALLO);
        assertTrue(uMultiplayer.getObiettivoPrivato().get(1).getColore()==Constraint.ROSSO);


    }

    @Test
    void scegliFacciaSchema() {
    }


}