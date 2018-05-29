package ingSw2018StaglianoVagagginiVergari.model;

import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;
import ingSw2018StaglianoVagagginiVergari.server.model.Utente;
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
        Utente singleplayer = new Utente(p, Constraint.GIALLO, Constraint.ROSSO);
        assertTrue(singleplayer.getObiettivoPrivato().get(0).getColore()==Constraint.GIALLO);
        assertTrue(singleplayer.getObiettivoPrivato().get(1).getColore()==Constraint.ROSSO);


    }

    @Test
    void inizializzaIdSetTest() {
        Plancia p = mock(Plancia.class);
        //Utente.inizializzaIdSet();
        Utente u1 = new Utente(p, Constraint.BLU);
        Utente u2 = new Utente(p, Constraint.ROSSO);
        Utente u3 = new Utente(p, Constraint.GIALLO);
        Utente u4 = new Utente(p, Constraint.VIOLA);
        assertFalse(u1.getId().equals(u2.getId()));
        assertFalse(u1.getId().equals(u3.getId()));
        assertFalse(u1.getId().equals(u4.getId()));
        assertFalse(u2.getId().equals(u3.getId()));
        assertFalse(u2.getId().equals(u4.getId()));
        assertFalse(u4.getId().equals(u3.getId()));
    }
}