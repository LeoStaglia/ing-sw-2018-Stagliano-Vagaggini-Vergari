package ingSw2018StaglianoVagagginiVergari.model;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.*;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PlanciaTest {

    Plancia p;
    Dado mockedDie1, mockedDie2;
    Schema schema;

    public void eccezionePiazzamento(Dado d, int i, int j){

        boolean thrown = false;
        try {
            p.piazzaDado(i,j, mockedDie2);
        } catch (MossaIllegaleException e) {
            thrown = true;
        }
        assertTrue(thrown);

    }





    @Before
    public void setUp() {
        mockedDie1 = mock(Dado.class);
        mockedDie2=mock(Dado.class);
        when(mockedDie1.getColore()).thenReturn("Giallo");
        when(mockedDie1.getNumero()).thenReturn(1);
        when(mockedDie2.getColore()).thenReturn("Viola");
        when(mockedDie2.getNumero()).thenReturn(2);
        schema = ViaLux.get();
        schema.scegliFaccia(true);
        schema.scegliFaccia(false);
        p=new Plancia("Rosso");
        p.inserisciCartaSchema(schema);



    }

    @Test
    public void inserisciCartaSchema() {
        assertTrue(p.getCartaSchema().equals(schema));
        //assertEquals(p.getCartaSchema(), schema);
    }

    @Test
    public void piazzaDado() throws MossaIllegaleException,RemoteException {
        try {
            p.calcolaMosse(mockedDie1, false, false);
            p.piazzaDado(0,0, mockedDie1);
        } catch (MossaIllegaleException e) {
            e.printStackTrace();
        }
        p.calcolaMosse(mockedDie2, false, false);


        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(0,3, mockedDie2));
        eccezionePiazzamento(mockedDie2,0,3);


        /*
        thrown = false;
        try {
            p.piazzaDado(0,3, mockedDie2);
        } catch (MossaIllegaleException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        */


    }

    @Test
    public void rimuoviDado() throws MossaIllegaleException,RemoteException{
        p.calcolaMosse(mockedDie1, false, false);
        p.piazzaDado(0,0, mockedDie1);
        assertNotNull(p.leggiPlancia(0,0));
        p.rimuoviDado(0,0);
        assertNull(p.leggiPlancia(0,0));
    }

    @Test
    public void leggiPlancia() throws MossaIllegaleException, RemoteException{
        p.calcolaMosse(mockedDie1, false, false);
        p.piazzaDado(0,0, mockedDie1);
        //assertEquals(p.leggiPlancia(0,0), mockedDie1);
        assertTrue(p.leggiPlancia(0,0).equals(mockedDie1));

    }

    @Test
    public void getCartaSchema() {
    }

    @Test
    public void calcolaMosse() throws RemoteException, MossaIllegaleException {
        Dado d0 = mock(Dado.class);
        when(d0.getNumero()).thenReturn(2);
        when(d0.getColore()).thenReturn("Giallo");
        p.calcolaMosse(d0, false, false);
        p.piazzaDado(3,0, d0);
        //assertEquals(p.leggiPlancia(3,0), d0);
        assertTrue(p.leggiPlancia(3,0).equals(d0));
        Dado d1 = mock(Dado.class);
        when(d1.getNumero()).thenReturn(5);
        when(d1.getColore()).thenReturn("Rosso");
        p.calcolaMosse(d1, false, false);
        p.piazzaDado(2,1, d1);
        //assertEquals(p.leggiPlancia(2,1), d1);
        assertTrue(p.leggiPlancia(2,1).equals(d1));

        Dado d2 = mock(Dado.class);
        when(d2.getNumero()).thenReturn(5);
        when(d2.getColore()).thenReturn("Verde");
        p.calcolaMosse(d2, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(3,1, d2));
        eccezionePiazzamento(d2,3,1);
        Dado d3 = mock(Dado.class);
        when(d3.getNumero()).thenReturn(2);
        when(d3.getColore()).thenReturn("Verde");
        p.calcolaMosse(d3, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(3,1, d3));
        eccezionePiazzamento(d3,3,1);
        Dado d4 = mock(Dado.class);
        when(d4.getNumero()).thenReturn(3);
        when(d4.getColore()).thenReturn("Verde");
        p.calcolaMosse(d4, false, false);
        p.piazzaDado(3,1, d4);
        //assertEquals(p.leggiPlancia(3,1), d4);
        assertTrue(p.leggiPlancia(3,1).equals(d4));
        Dado d5 = mock(Dado.class);
        when(d5.getNumero()).thenReturn(3);
        when(d5.getColore()).thenReturn("Rosso");
        p.calcolaMosse(d5, false, false);
        p.piazzaDado(1,2, d5);
        //assertEquals(p.leggiPlancia(1,2), d5);
        assertTrue(p.leggiPlancia(1,2).equals(d5));
        Dado d6 = mock(Dado.class);
        when(d6.getNumero()).thenReturn(3);
        when(d6.getColore()).thenReturn("Verde");
        p.calcolaMosse(d6, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(0,2, d6));
        eccezionePiazzamento(d6,0,2);
        Dado d7 = mock(Dado.class);
        when(d7.getNumero()).thenReturn(3);
        when(d7.getColore()).thenReturn("Verde");
        p.calcolaMosse(d7, false, false);
        p.piazzaDado(0,3, d7);
        //assertEquals(p.leggiPlancia(0,3), d7);
        assertTrue(p.leggiPlancia(0,3).equals(d7));
        Dado d8 = mock(Dado.class);
        when(d8.getNumero()).thenReturn(6);
        when(d8.getColore()).thenReturn("Rosso");
        p.calcolaMosse(d8, false, false);
        p.piazzaDado(0,4, d8);
        //assertEquals(p.leggiPlancia(0,4), d8);
        assertTrue(p.leggiPlancia(0,4).equals(d8));
        Dado d9 = mock(Dado.class);
        when(d9.getNumero()).thenReturn(6);
        when(d9.getColore()).thenReturn("Blu");
        p.calcolaMosse(d9, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(1,4, d9));
        eccezionePiazzamento(d9,1,4);
        Dado d10 = mock(Dado.class);
        when(d10.getNumero()).thenReturn(3);
        when(d10.getColore()).thenReturn("Blu");
        p.calcolaMosse(d10, false,false);
        p.piazzaDado(1,4, d10);
        //assertEquals(p.leggiPlancia(1,4), d10);
        assertTrue(p.leggiPlancia(1,4).equals(d10));
        Dado d12 = mock(Dado.class);
        when(d12.getNumero()).thenReturn(4);
        when(d12.getColore()).thenReturn("Rosso");
        p.calcolaMosse(d12, false, false);
        p.piazzaDado(0,1, d12);
        //assertEquals(p.leggiPlancia(0,1), d12);
        assertTrue(p.leggiPlancia(0,1).equals(d12));
        Dado d13 = mock(Dado.class);
        when(d13.getNumero()).thenReturn(3);
        when(d13.getColore()).thenReturn("Blu");
        p.calcolaMosse(d13, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(3,4, d13));
        eccezionePiazzamento(d13,3,4);
        Dado d11 = mock(Dado.class);
        when(d11.getNumero()).thenReturn(4);
        when(d11.getColore()).thenReturn("Rosso");
        p.calcolaMosse(d11, false, false);
        p.piazzaDado(2,3, d11);
        //assertEquals(p.leggiPlancia(2,3), d11);
        assertTrue(p.leggiPlancia(2,3).equals(d11));
        Dado d14 = mock(Dado.class);
        when(d14.getNumero()).thenReturn(2);
        when(d14.getColore()).thenReturn("Rosso");
        p.calcolaMosse(d14, false, false);
        p.piazzaDado(3,4, d14);
        //assertEquals(p.leggiPlancia(3,4), d14);
        assertTrue(p.leggiPlancia(3,4).equals(d14));
        Dado d15 = mock(Dado.class);
        when(d15.getNumero()).thenReturn(5);
        when(d15.getColore()).thenReturn("Blu");
        p.calcolaMosse(d15, false, false);
        p.piazzaDado(1,0, d15);
        //assertEquals(p.leggiPlancia(1,0), d15);
        assertTrue(p.leggiPlancia(1,0).equals(d15));
        Dado d16 = mock(Dado.class);
        when(d16.getNumero()).thenReturn(5);
        when(d16.getColore()).thenReturn("Blu");
        p.calcolaMosse(d16, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(1,0, d16));
        eccezionePiazzamento(d16,1,0);
        Dado d17 = mock(Dado.class);
        when(d17.getNumero()).thenReturn(2);
        when(d17.getColore()).thenReturn("Verde");
        p.calcolaMosse(d17, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(2,0, d17));
        eccezionePiazzamento(d17,2,0);
        Dado d18 = mock(Dado.class);
        when(d18.getNumero()).thenReturn(5);
        when(d18.getColore()).thenReturn("Viola");
        p.calcolaMosse(d18, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(2,0, d18));
        eccezionePiazzamento(d18,2,0);
        Dado d19 = mock(Dado.class);
        when(d19.getNumero()).thenReturn(3);
        when(d19.getColore()).thenReturn("Viola");
        p.calcolaMosse(d19, false, false);
        p.piazzaDado(2,0, d19);
        //assertEquals(p.leggiPlancia(2,0), d19);
        assertTrue(p.leggiPlancia(2,0).equals(d19));
        Dado d20 = mock(Dado.class);
        when(d20.getNumero()).thenReturn(3);
        when(d20.getColore()).thenReturn("Viola");
        p.calcolaMosse(d20, false, false);
        p.piazzaDado(3,3, d20);
        //assertEquals(p.leggiPlancia(3,3), d20);
        assertTrue(p.leggiPlancia(3,3).equals(d20));
        Dado d21 = mock(Dado.class);
        when(d21.getNumero()).thenReturn(3);
        when(d21.getColore()).thenReturn("Viola");
        p.calcolaMosse(d21, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(3,3, d21));
        eccezionePiazzamento(d21,3,3);
        Dado d22 = mock(Dado.class);
        when(d22.getNumero()).thenReturn(1);
        when(d22.getColore()).thenReturn("Verde");
        p.calcolaMosse(d22, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(3,2, d22));
        eccezionePiazzamento(d22,3,2);
        Dado d23 = mock(Dado.class);
        when(d23.getNumero()).thenReturn(1);
        when(d23.getColore()).thenReturn("Viola");
        p.calcolaMosse(d23, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(3,2, d23));
        eccezionePiazzamento(d23,3,2);
        Dado d24 = mock(Dado.class);
        when(d24.getNumero()).thenReturn(1);
        when(d24.getColore()).thenReturn("Rosso");
        p.calcolaMosse(d24, false, false);
        p.piazzaDado(3,2, d24);
        //assertEquals(p.leggiPlancia(3,2), d24);
        assertTrue(p.leggiPlancia(3,2).equals(d24));
        //--------------------------------------------------------------------------------------------------------------
        for (int i=0;i<4;i++){
            for (int j=0; j<5; j++){
                p.rimuoviDado(i,j);
            }
        }
        Schema schema1 = Virtus.get();
        schema1.scegliFaccia(true);
        p.inserisciCartaSchema(schema1);
        Dado da1 = mock(Dado.class);
        when(da1.getNumero()).thenReturn(6);
        when(da1.getColore()).thenReturn("Rosso");
        p.calcolaMosse(da1, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(1,2, da1));
        eccezionePiazzamento(da1,1,2);
        Dado da2 = mock(Dado.class);
        when(da2.getNumero()).thenReturn(3);
        when(da2.getColore()).thenReturn("Viola");
        p.calcolaMosse(da2, false, false);
        p.piazzaDado(1,0, da2);
        //assertEquals(p.leggiPlancia(1,0), da2);
        assertTrue(p.leggiPlancia(1,0).equals(da2));
        Dado da3 = mock(Dado.class);
        when(da3.getNumero()).thenReturn(4);
        when(da3.getColore()).thenReturn("Viola");
        p.calcolaMosse(da3, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(0,0, da3));
        eccezionePiazzamento(da3,0,0);
        Dado da4= mock(Dado.class);
        when(da4.getNumero()).thenReturn(4);
        when(da4.getColore()).thenReturn("Rosso");
        p.calcolaMosse(da4, false, false);
        p.piazzaDado(0,0, da4);
        //assertEquals(p.leggiPlancia(0,0), da4);
        assertTrue(p.leggiPlancia(0,0).equals(da4));
        Dado da5= mock(Dado.class);
        when(da5.getNumero()).thenReturn(3);
        when(da5.getColore()).thenReturn("Rosso");
        p.calcolaMosse(da5, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(0,1, da5));
        eccezionePiazzamento(da5,0,1);
        Dado da6= mock(Dado.class);
        when(da6.getNumero()).thenReturn(3);
        when(da6.getColore()).thenReturn("Viola");
        p.calcolaMosse(da6, false, false);
        p.piazzaDado(2,1, da6);
        //assertEquals(p.leggiPlancia(2,1), da6);
        assertTrue(p.leggiPlancia(2,1).equals(da6));
        Dado da7= mock(Dado.class);
        when(da7.getNumero()).thenReturn(1);
        when(da7.getColore()).thenReturn("Rosso");
        p.calcolaMosse(da7, false, false);
        p.piazzaDado(3,2, da7);
        //assertEquals(p.leggiPlancia(3,2), da7);
        assertTrue(p.leggiPlancia(3,2).equals(da7));
        Dado da8= mock(Dado.class);
        when(da8.getNumero()).thenReturn(5);
        when(da8.getColore()).thenReturn("Verde");
        p.calcolaMosse(da8, false, false);
        p.piazzaDado(2,2, da8);
        //assertEquals(p.leggiPlancia(2,2), da8);
        assertTrue(p.leggiPlancia(2,2).equals(da8));
        Dado da9= mock(Dado.class);
        when(da9.getNumero()).thenReturn(6);
        when(da9.getColore()).thenReturn("Giallo");
        p.calcolaMosse(da9, false, false);
        p.piazzaDado(3,3, da9);
        //assertEquals(p.leggiPlancia(3,3), da9);
        assertTrue(p.leggiPlancia(3,3).equals(da9));
        Dado da10= mock(Dado.class);
        when(da10.getNumero()).thenReturn(5);
        when(da10.getColore()).thenReturn("Blu");
        p.calcolaMosse(da10, false, false);
        p.piazzaDado(3,4, da10);
        //assertEquals(p.leggiPlancia(3,4), da10);
        assertTrue(p.leggiPlancia(3,4).equals(da10));
        Dado da11= mock(Dado.class);
        when(da11.getNumero()).thenReturn(1);
        when(da11.getColore()).thenReturn("Rosso");
        p.calcolaMosse(da11, false, false);
        p.piazzaDado(2,4, da11);
        //assertEquals(p.leggiPlancia(2,4), da11);
        assertTrue(p.leggiPlancia(2,4).equals(da11));
        Dado da12= mock(Dado.class);
        when(da12.getNumero()).thenReturn(3);
        when(da12.getColore()).thenReturn("Verde");
        p.calcolaMosse(da12, false, false);
        p.piazzaDado(1,3, da12);
        //assertEquals(p.leggiPlancia(1,3), da12);
        assertTrue(p.leggiPlancia(1,3).equals(da12));
        Dado da13= mock(Dado.class);
        when(da13.getNumero()).thenReturn(2);
        when(da13.getColore()).thenReturn("Rosso");
        p.calcolaMosse(da13, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(2,3, da13));
        eccezionePiazzamento(da13,2,3);
        //--------------------------------------------------------------------------------------------------------------
        for (int i=0;i<4;i++){
            for (int j=0; j<5; j++){
                p.rimuoviDado(i,j);
            }
        }
        Dado db1 = mock(Dado.class);
        when(db1.getNumero()).thenReturn(3);
        when(db1.getColore()).thenReturn("Viola");
        p.calcolaMosse(db1, false, false);
        p.piazzaDado(3,4, db1);
        //assertEquals(p.leggiPlancia(3,4), db1);
        assertTrue(p.leggiPlancia(3,4).equals(db1));
        Dado db2 = mock(Dado.class);
        when(db2.getNumero()).thenReturn(4);
        when(db2.getColore()).thenReturn("Giallo");
        p.calcolaMosse(db2, false, false);
        p.piazzaDado(2,3, db2);
        //assertEquals(p.leggiPlancia(2,3), db2);
        assertTrue(p.leggiPlancia(2,3).equals(db2));
        Dado db3 = mock(Dado.class);
        when(db3.getNumero()).thenReturn(1);
        when(db3.getColore()).thenReturn("Rosso");
        p.calcolaMosse(db3, false, false);
        p.piazzaDado(3,2, db3);
        //assertEquals(p.leggiPlancia(3,2), db3);
        assertTrue(p.leggiPlancia(3,2).equals(db3));
        Dado db4 = mock(Dado.class);
        when(db4.getNumero()).thenReturn(3);
        when(db4.getColore()).thenReturn("Blu");
        p.calcolaMosse(db4, false, false);
        p.piazzaDado(2,1, db4);
        //assertEquals(p.leggiPlancia(2,1), db4);
        assertTrue(p.leggiPlancia(2,1).equals(db4));
        Dado db5 = mock(Dado.class);
        when(db5.getNumero()).thenReturn(1);
        when(db5.getColore()).thenReturn("Giallo");
        p.calcolaMosse(db5, false, false);
        //assertThrows(MossaIllegaleException.class, ()->p.piazzaDado(2,2, db5));
        eccezionePiazzamento(db5,2,2);
        //--------------------------------------------------------------------------------------------------------------
        for (int i=0;i<4;i++){
            for (int j=0; j<5; j++){
                p.rimuoviDado(i,j);
            }
        }
        Schema schema2 = AuroraeMagnificus.get();
        schema2.scegliFaccia(true);
        p.inserisciCartaSchema(schema2);
        Dado dx1 = mock(Dado.class);
        when(dx1.getNumero()).thenReturn(4);
        when(dx1.getColore()).thenReturn("Blu");
        p.calcolaMosse(dx1, false, false);
        p.piazzaDado(3,4, dx1);
        //assertEquals(p.leggiPlancia(3,4), dx1);
        assertTrue(p.leggiPlancia(3,4).equals(dx1));
        Dado dx2 = mock(Dado.class);
        when(dx2.getNumero()).thenReturn(1);
        when(dx2.getColore()).thenReturn("Blu");
        p.calcolaMosse(dx2, false, false);
        p.piazzaDado(2,3, dx2);
        //assertEquals(p.leggiPlancia(2,3), dx2);
        assertTrue(p.leggiPlancia(2,3).equals(dx2));
        //--------------------------------------------------------------------------------------------------------------
        for (int i=0;i<4;i++){
            for (int j=0; j<5; j++){
                p.rimuoviDado(i,j);
            }
        }
        Schema schema3 = Firelight.get();
        p.inserisciCartaSchema(schema3);
        schema3.scegliFaccia(true);
        Dado x1 = mock(Dado.class);
        when(x1.getNumero()).thenReturn(5);
        when(x1.getColore()).thenReturn("Giallo");
        Boolean[][] matrix = p.calcolaMosse(x1, false, false);
        for (int i=0; i<4; i++){
            System.out.println("");
            for (int j=0; j<5; j++){
                System.out.print(matrix[i][j]);
                System.out.print(" ");
            }
        }
        p.piazzaDado(0,3, x1);
        //assertEquals(p.leggiPlancia(0,3), x1);
        assertTrue(p.leggiPlancia(0,3).equals(x1));




    }

}