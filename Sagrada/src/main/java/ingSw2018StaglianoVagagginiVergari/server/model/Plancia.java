package ingSw2018StaglianoVagagginiVergari.server.model;


import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.Schema;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Plancia implements Serializable {
    private String colore;
    private Schema cartaSchema;
    private Dado[][] grigliaGioco;
    private Boolean[][] piazzamentiPermessi;

    public Plancia(String colore) {
        grigliaGioco = new Dado[4][5];
        this.colore = colore;
        piazzamentiPermessi = new Boolean[4][5];
    }

    /*public String getColore() {
        return colore;
    }*/

    public void inserisciCartaSchema(Schema cartaSchema) {
        this.cartaSchema = cartaSchema;
    }

    public void piazzaDado(int i, int j, Dado d) throws MossaIllegaleException {

        if (piazzamentiPermessi[i][j]) {
            grigliaGioco[i][j] = d; //alternative implementations: assign the actual object or create a copy of the object

        } else {
            throw new MossaIllegaleException();
        }

    }

    public void rimuoviDado(int i, int j) {
        grigliaGioco[i][j] = null;
    }

    public Dado leggiPlancia(int i, int j) {

        return grigliaGioco[i][j];
    }

    public Schema getCartaSchema() {

        return cartaSchema;
    }
    private boolean checkUtensile2(int i, int j, Dado dadoSelezionato){
        if (cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("1") || cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("2") || cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("3") ||cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("4") || cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("5") || cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("6")){
            if (cartaSchema.getRestrizione(i,j).getDescrizione().equals(dadoSelezionato.getNumero())){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
    private boolean checkUtensile3(int i, int j, Dado dadoSelezionato){
        if (!(cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("1") || cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("2") || cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("3") ||cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("4") || cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("5") || cartaSchema.getRestrizione(i,j).getDescrizione().startsWith("6"))){
            if (cartaSchema.getRestrizione(i,j).getDescrizione().equals(dadoSelezionato.getColore())){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }




    /*helper method for calcolaMosse(), that verifies for each non-null position of the game grid if its specific near cell are available, excluding the elaboration of
    the first move on the grid.
     */

    private void setCelleAdiacenti(int i, int j, Dado dadoSelezionato, boolean attivaUtensile2, boolean attivaUtensile3) {
        if (grigliaGioco[i][j] != null) {
            piazzamentiPermessi[i][j] = false;

            if (i == 0 && j == 0) {
                if (grigliaGioco[i][j + 1] == null && (cartaSchema.getRestrizione(i, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j+1, dadoSelezionato)))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i][j + 1] == null) {
                            piazzamentiPermessi[i][j + 1] = true;
                        }
                    }else{
                        piazzamentiPermessi[i][j + 1] = false;
                    }
                } else {
                    piazzamentiPermessi[i][j + 1] = false;
                }
                if (grigliaGioco[i + 1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) ||  cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i+1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j, dadoSelezionato)))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i + 1][j] == null) {
                            piazzamentiPermessi[i + 1][j] = true;
                        }
                    }else{
                        piazzamentiPermessi[i + 1][j] = false;
                    }
                } else {
                    piazzamentiPermessi[i + 1][j] = false;

                }
                if (grigliaGioco[i + 1][j + 1] == null && (cartaSchema.getRestrizione(i + 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i+1, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j+1, dadoSelezionato)))) {
                    if (piazzamentiPermessi[i + 1][j + 1] == null) {

                        piazzamentiPermessi[i + 1][j + 1] = true;
                    }
                }else{
                    piazzamentiPermessi[i+1][j+1]=false;
                }
            } else if (i == 0 && j < 4 && j > 0) {
                if (grigliaGioco[i][j - 1] == null && (cartaSchema.getRestrizione(i, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i, j-11, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j-1, dadoSelezionato)))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i][j - 1] == null) {

                            piazzamentiPermessi[i][j - 1] = true;
                        }

                    }else{
                        piazzamentiPermessi[i][j - 1] = false;
                    }
                } else {
                    piazzamentiPermessi[i][j - 1] = false;
                }
                if (grigliaGioco[i][j + 1] == null && (cartaSchema.getRestrizione(i, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j+1, dadoSelezionato)))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i][j + 1] == null) {

                            piazzamentiPermessi[i][j + 1] = true;
                        }
                    }else{
                        piazzamentiPermessi[i][j + 1] = false;
                    }
                } else {
                    piazzamentiPermessi[i][j + 1] = false;
                }
                if (grigliaGioco[i + 1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i+1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j, dadoSelezionato)))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i + 1][j] == null) {
                            piazzamentiPermessi[i + 1][j] = true;
                        }

                    }else{
                        piazzamentiPermessi[i + 1][j] = false;

                    }
                } else {
                    piazzamentiPermessi[i + 1][j] = false;
                }
                if (grigliaGioco[i + 1][j - 1] == null && (cartaSchema.getRestrizione(i + 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i+1, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j-1, dadoSelezionato)))) {
                    if (piazzamentiPermessi[i + 1][j - 1] == null) {

                        piazzamentiPermessi[i + 1][j - 1] = true;

                    }
                } else {
                    piazzamentiPermessi[i + 1][j - 1] = false;
                }
                if (grigliaGioco[i + 1][j + 1] == null && (cartaSchema.getRestrizione(i + 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i+1, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j+1, dadoSelezionato)))) {
                    if (piazzamentiPermessi[i + 1][j + 1] == null) {

                        piazzamentiPermessi[i + 1][j + 1] = true;
                    }
                } else {
                    piazzamentiPermessi[i + 1][j + 1] = false;
                }

            } else if (i == 0 && j == 4) {
                if (grigliaGioco[i][j - 1] == null && (cartaSchema.getRestrizione(i, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j-1, dadoSelezionato)))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i][j - 1] == null) {

                            piazzamentiPermessi[i][j - 1] = true;
                        }
                    }else{
                        piazzamentiPermessi[i][j - 1] = false;
                    }
                } else {
                    piazzamentiPermessi[i][j - 1] = false;
                }
                if (grigliaGioco[i + 1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i+1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j, dadoSelezionato)) )) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i + 1][j] == null) {

                            piazzamentiPermessi[i + 1][j] = true;
                        }

                    }else{
                        piazzamentiPermessi[i + 1][j] = false;

                    }
                } else {
                    piazzamentiPermessi[i + 1][j] = false;
                }
                if (grigliaGioco[i + 1][j - 1] == null && (cartaSchema.getRestrizione(i + 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i+1, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j-1, dadoSelezionato)) )) {
                    if (piazzamentiPermessi[i + 1][j - 1] == null) {

                        piazzamentiPermessi[i + 1][j - 1] = true;
                    }
                } else {
                    piazzamentiPermessi[i + 1][j - 1] = false;

                }
            } else if (i >= 1 && i < 3 && j == 0) {
                if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i-1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j, dadoSelezionato)))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i - 1][j] == null) {

                            piazzamentiPermessi[i - 1][j] = true;
                        }
                    }else{
                        piazzamentiPermessi[i - 1][j] = false;

                    }
                } else {
                    piazzamentiPermessi[i - 1][j] = false;
                }
                if (grigliaGioco[i][j + 1] == null && (cartaSchema.getRestrizione(i, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j+1, dadoSelezionato)))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i][j + 1] == null) {

                            piazzamentiPermessi[i][j + 1] = true;
                        }
                    }else{
                        piazzamentiPermessi[i][j + 1] = false;

                    }
                } else {
                    piazzamentiPermessi[i][j + 1] = false;

                }if (grigliaGioco[i + 1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i+1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j, dadoSelezionato)))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {

                        if (piazzamentiPermessi[i + 1][j] == null) {

                            piazzamentiPermessi[i + 1][j] = true;
                        }
                    }else{
                        piazzamentiPermessi[i + 1][j] = false;

                    }
                } else {
                    piazzamentiPermessi[i + 1][j] = false;
                }
                if (grigliaGioco[i - 1][j + 1] == null && (cartaSchema.getRestrizione(i - 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j+1, dadoSelezionato)))) {
                    if (piazzamentiPermessi[i - 1][j + 1] == null) {

                        piazzamentiPermessi[i - 1][j + 1] = true;
                    }
                } else {
                    piazzamentiPermessi[i - 1][j + 1] = false;

                }
                if (grigliaGioco[i + 1][j + 1] == null && (cartaSchema.getRestrizione(i + 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i+1, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j+1, dadoSelezionato)))) {
                    if (piazzamentiPermessi[i + 1][j + 1] == null) {

                        piazzamentiPermessi[i + 1][j + 1] = true;
                    }
                } else {
                    piazzamentiPermessi[i + 1][j + 1] = false;

                }
            } else if (i > 0 && i < 3 && j > 0 && j < 4) {
                if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j, dadoSelezionato)))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i - 1][j] == null) {

                            piazzamentiPermessi[i - 1][j] = true;
                        }
                    }else{
                        piazzamentiPermessi[i - 1][j] = false;

                    }
                } else {
                    piazzamentiPermessi[i - 1][j] = false;

                }if(grigliaGioco[i+1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i+1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j, dadoSelezionato)))){
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i+1][j] == null) {

                            piazzamentiPermessi[i+1][j] = true;
                        }
                    }else{
                        piazzamentiPermessi[i+1][j] = false;
                    }
                }else{
                    piazzamentiPermessi[i+1][j] = false;
                }if (grigliaGioco[i][j+1] == null && (cartaSchema.getRestrizione(i, j+1) == Constraint.NONE || cartaSchema.getRestrizione(i, j+1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j+1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j+1, dadoSelezionato)) )){
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i][j+1] == null) {

                            piazzamentiPermessi[i][j+1] = true;
                        }
                    }else{
                        piazzamentiPermessi[i][j+1] = false;

                    }
                }else{
                    piazzamentiPermessi[i][j+1] = false;
                }if (grigliaGioco[i][j-1] == null && (cartaSchema.getRestrizione(i, j-1) == Constraint.NONE || cartaSchema.getRestrizione(i, j-1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j-1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i, j-11, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j-11, dadoSelezionato)))){
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i][j-1] == null) {

                            piazzamentiPermessi[i][j-1] = true;
                        }
                    }else{
                        piazzamentiPermessi[i][j-1] = false;
                    }
                }else{
                    piazzamentiPermessi[i][j-1] = false;
                }if (grigliaGioco[i+1][j+1] == null && (cartaSchema.getRestrizione(i+1, j+1) == Constraint.NONE || cartaSchema.getRestrizione(i+1, j+1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i+1, j+1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i+1, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j+1, dadoSelezionato)))){
                    if (piazzamentiPermessi[i+1][j+1] == null){
                        piazzamentiPermessi[i+1][j+1]=true;
                    }
                }else{
                    piazzamentiPermessi[i+1][j+1]=false;

                }if (grigliaGioco[i+1][j-1] == null && (cartaSchema.getRestrizione(i+1, j-1) == Constraint.NONE || cartaSchema.getRestrizione(i+1, j-1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i+1, j-1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i+1, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j-1, dadoSelezionato)))){
                    if (piazzamentiPermessi[i+1][j-1] == null){
                        piazzamentiPermessi[i+1][j-1]=true;
                    }
                }else{
                    piazzamentiPermessi[i+1][j-1]=false;

                }if (grigliaGioco[i-1][j-1] == null && (cartaSchema.getRestrizione(i-1, j-1) == Constraint.NONE || cartaSchema.getRestrizione(i-1, j-1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i-1, j-1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j-1, dadoSelezionato)))){
                    if (piazzamentiPermessi[i-1][j-1] == null){
                        piazzamentiPermessi[i-1][j-1]=true;
                    }
                }else{
                    piazzamentiPermessi[i-1][j-1]=false;

                }if (grigliaGioco[i-1][j+1] == null && (cartaSchema.getRestrizione(i-1, j+1) == Constraint.NONE || cartaSchema.getRestrizione(i-1, j+1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i-1, j+1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i-1, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j+1, dadoSelezionato)))){
                    if (piazzamentiPermessi[i-1][j+1] == null){
                        piazzamentiPermessi[i-1][j+1]=true;
                    }
                }else{
                    piazzamentiPermessi[i-1][j+1]=false;

                }

            } else if (i == 3 && j == 0) {
                    if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j, dadoSelezionato)))) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i - 1][j] == null) {

                                piazzamentiPermessi[i - 1][j] = true;
                            }
                        }else{
                            piazzamentiPermessi[i - 1][j] = false;
                        }
                    } else {
                        piazzamentiPermessi[i - 1][j] = false;
                    }
                    if (grigliaGioco[i][j + 1] == null && (cartaSchema.getRestrizione(i, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j+1, dadoSelezionato)))) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i][j + 1] == null) {

                                piazzamentiPermessi[i][j + 1] = true;
                            }
                        }else{
                            piazzamentiPermessi[i][j + 1] = false;

                        }
                    } else {
                        piazzamentiPermessi[i][j + 1] = false;

                    }if (grigliaGioco[i - 1][j + 1] == null && (cartaSchema.getRestrizione(i - 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j+1, dadoSelezionato)))) {
                        if (piazzamentiPermessi[i - 1][j + 1] == null) {

                            piazzamentiPermessi[i - 1][j + 1] = true;
                        }

                    } else {
                        piazzamentiPermessi[i - 1][j + 1] = false;

                    }

                } else if (i == 3 && j >= 1 && j < 4) {
                    if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j, dadoSelezionato)))) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i - 1][j] == null) {

                                piazzamentiPermessi[i - 1][j] = true;
                            }
                        }else{
                            piazzamentiPermessi[i - 1][j] = false;

                        }
                    } else {
                        piazzamentiPermessi[i - 1][j] = false;

                    }
                    if (grigliaGioco[i][j + 1] == null && (cartaSchema.getRestrizione(i, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j+1, dadoSelezionato)))) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i][j + 1] == null) {

                                piazzamentiPermessi[i][j + 1] = true;
                            }
                        }else{
                            piazzamentiPermessi[i][j + 1] = false;

                        }
                    } else {
                        piazzamentiPermessi[i][j + 1] = false;

                    }
                    if (grigliaGioco[i][j - 1] == null && (cartaSchema.getRestrizione(i, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j-1, dadoSelezionato)))) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i][j - 1] == null) {

                                piazzamentiPermessi[i][j - 1] = true;
                            }
                        }else{
                            piazzamentiPermessi[i][j - 1] = false;
                        }
                    } else {
                        piazzamentiPermessi[i][j - 1] = false;

                    }
                    if (grigliaGioco[i - 1][j - 1] == null && (cartaSchema.getRestrizione(i - 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j-1, dadoSelezionato)))) {
                        if (piazzamentiPermessi[i - 1][j - 1] == null) {

                            piazzamentiPermessi[i - 1][j - 1] = true;
                        }
                    } else {
                        piazzamentiPermessi[i - 1][j - 1] = false;

                    }
                    if (grigliaGioco[i - 1][j + 1] == null && (cartaSchema.getRestrizione(i - 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j+1, dadoSelezionato)))) {
                        if (piazzamentiPermessi[i - 1][j + 1] == null) {

                            piazzamentiPermessi[i - 1][j + 1] = true;
                        }
                    } else {
                        piazzamentiPermessi[i - 1][j + 1] = false;

                    }
                } else if (i == 3 && j == 4) {
                    if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j, dadoSelezionato)))) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i - 1][j] == null) {

                                piazzamentiPermessi[i - 1][j] = true;
                            }
                        }else{
                            piazzamentiPermessi[i - 1][j] = false;

                        }
                    } else {
                        piazzamentiPermessi[i - 1][j] = false;

                    }
                    if (grigliaGioco[i][j - 1] == null && (cartaSchema.getRestrizione(i, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j-1, dadoSelezionato)) )) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i][j - 1] == null) {

                                piazzamentiPermessi[i][j - 1] = true;
                            }
                        }else{
                            piazzamentiPermessi[i][j - 1] = false;

                        }
                    } else {
                        piazzamentiPermessi[i][j - 1] = false;

                    }
                    if (grigliaGioco[i - 1][j - 1] == null && (cartaSchema.getRestrizione(i - 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j-1, dadoSelezionato)) )) {
                        if (piazzamentiPermessi[i - 1][j - 1] == null) {

                            piazzamentiPermessi[i - 1][j - 1] = true;
                        }
                    } else {
                        piazzamentiPermessi[i - 1][j - 1] = false;

                    }
                } else if (i > 0 && i < 3 && j == 4) {
                    if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i-1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i-1, j, dadoSelezionato)) )) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i - 1][j] == null) {

                                piazzamentiPermessi[i - 1][j] = true;
                            }
                        }else{
                            piazzamentiPermessi[i - 1][j] = false;

                        }
                    } else {
                        piazzamentiPermessi[i - 1][j] = false;
                    }
                    if (grigliaGioco[i + 1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i+1, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j, dadoSelezionato)))) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i + 1][j] == null) {

                                piazzamentiPermessi[i + 1][j] = true;
                            }
                        }else{
                            piazzamentiPermessi[i + 1][j] = false;

                        }
                    } else {
                        piazzamentiPermessi[i + 1][j] = false;

                    }
                    if (grigliaGioco[i][j - 1] == null && (cartaSchema.getRestrizione(i, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j-1, dadoSelezionato)))) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i][j - 1] == null) {

                                piazzamentiPermessi[i][j - 1] = true;
                            }
                        }else{
                            piazzamentiPermessi[i][j - 1] = false;

                        }
                    } else {
                        piazzamentiPermessi[i][j - 1] = false;
                    }
                    if (grigliaGioco[i - 1][j - 1] == null && (cartaSchema.getRestrizione(i - 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i, j+1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j+1, dadoSelezionato)))) {
                        if (piazzamentiPermessi[i - 1][j - 1] == null) {

                            piazzamentiPermessi[i - 1][j - 1] = true;
                        }
                    } else {
                        piazzamentiPermessi[i - 1][j - 1] = false;

                    }
                    if (grigliaGioco[i + 1][j - 1] == null && (cartaSchema.getRestrizione(i + 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i+1, j-1, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i+1, j-1, dadoSelezionato)))) {
                        if (piazzamentiPermessi[i + 1][j - 1] == null) {

                            piazzamentiPermessi[i + 1][j - 1] = true;
                        }
                    } else {
                        piazzamentiPermessi[i + 1][j - 1] = false;

                    }
                }
            }

        }

        //run-time calculus of available moves based on a selected dice (parameter).

    public Boolean[][] calcolaMosse (Dado dadoSelezionato, boolean attivaUtensile2, boolean attivaUtensile3){
            boolean primoPiazzamanto = true;
            int i, j;

            for (i = 0; i < 4; i++) {
                for (j = 0; j < 5; j++) {
                    piazzamentiPermessi[i][j] = null;
                }
            }

            for (i = 0; i < 4; i++) {
                for (j = 0; j < 5; j++) {
                    if (grigliaGioco[i][j] != null)
                        primoPiazzamanto = false;
                }
            }

            if (primoPiazzamanto) {
                i = 0;
                for (j = 0; j < 5; j++) {
                    Constraint restrizione = cartaSchema.getRestrizione(i, j);
                    if (restrizione == Constraint.NONE || restrizione.getDescrizione().equals(dadoSelezionato.getColore()) || restrizione.getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j, dadoSelezionato)) )
                        piazzamentiPermessi[i][j] = true;
                    else
                        piazzamentiPermessi[i][j] = false;

                }
                j = 0;
                for (i = 0; i < 4; i++) {
                    Constraint restrizione = cartaSchema.getRestrizione(i, j);
                    if (restrizione == Constraint.NONE || restrizione.getDescrizione().equals(dadoSelezionato.getColore()) || restrizione.getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j, dadoSelezionato)))
                        piazzamentiPermessi[i][j] = true;
                    else
                        piazzamentiPermessi[i][j] = false;

                }
                j = 4;
                for (i = 0; i < 4; i++) {
                    Constraint restrizione = cartaSchema.getRestrizione(i, j);
                    if (restrizione == Constraint.NONE || restrizione.getDescrizione().equals(dadoSelezionato.getColore()) || restrizione.getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()) || (attivaUtensile2 && checkUtensile2(i, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j, dadoSelezionato)))
                        piazzamentiPermessi[i][j] = true;
                    else
                        piazzamentiPermessi[i][j] = false;

                }
                i = 3;
                for (j = 0; j < 5; j++) {
                    Constraint restrizione = cartaSchema.getRestrizione(i, j);
                    if (restrizione == Constraint.NONE || restrizione.getDescrizione().equals(dadoSelezionato.getColore()) || restrizione.getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString())|| (attivaUtensile2 && checkUtensile2(i, j, dadoSelezionato)) || (attivaUtensile3 && checkUtensile3(i, j, dadoSelezionato)) )
                        piazzamentiPermessi[i][j] = true;
                    else
                        piazzamentiPermessi[i][j] = false;

                }
                for (i = 1; i <= 2; i++) {
                    for (j = 1; j <= 3; j++) {
                        piazzamentiPermessi[i][j] = false;
                    }
                }
                return piazzamentiPermessi;

            } else {
                for (i = 0; i < 4; i++) {
                    for (j = 0; j < 5; j++) {
                        setCelleAdiacenti(i, j, dadoSelezionato, attivaUtensile2, attivaUtensile3);
                    }
                }
                for (i=0; i<4; i++){
                    for (j=0; j<5; j++){
                        if (piazzamentiPermessi[i][j]==null){
                            piazzamentiPermessi[i][j]=false;
                        }

                    }
                }

            }
            return piazzamentiPermessi;
        }


    public void Tool9( Dado dadoSelezionato){




        /*
        *  - scegli un dado dalla riserva
        *
        *  piazza il dado in una posizione che non sia adiacente a un altro dado:
        *
        *  - calcolo le posizioni corrette del dado tramite il metodo calcolaMosse( dado  selezionato)
        *  - a questo punto ho la matrice dei possibili piazzamenti
        *  - modifico la matrice mettendo a false tutti i dadi che hanno un altro dado adiacente
        *
        *
        * */




        Boolean[][] NuoviPiazzamentiPermessi = new Boolean[4][5];


        Boolean[][] IsDadoInPlanciaCorrente = new Boolean[4][5];





        //imposto tutta la matrice NuoviPiazzamentiPermessi :  se nella carta schema, nella posizone [i][j] c'è un colore o un numero diverso, metti la matrice  NuoviPiazzamentiPermessi[i][j]=false

        // imposto true nella matrice IsDadoInPlanciaCorrente ogni volta che è presente un dado, false altrmenti



        for (int i=0; i<4;i++){

            //System.out.println();
            for (int j=0;j<5;j++){

                if (leggiPlancia(i,j) != null)
                    IsDadoInPlanciaCorrente[i][j] = true;

                else
                    IsDadoInPlanciaCorrente[i][j] = false;


                if( !  ( (getCartaSchema().getRestrizione(i,j).getDescrizione().equalsIgnoreCase(dadoSelezionato.getColore()))     ||       (getCartaSchema().getRestrizione(i,j).getDescrizione().equalsIgnoreCase(new Integer(dadoSelezionato.getNumero()).toString() )  )  ||  (getCartaSchema().getRestrizione(i,j).getDescrizione().equalsIgnoreCase("N")   )))
                    NuoviPiazzamentiPermessi[i][j]=false;
                else
                    NuoviPiazzamentiPermessi[i][j]=true;


                //System.out.print(IsDadoInPlanciaCorrente[i][j] + " ");




            }
        }




        //System.out.println("\nquesta è la matrice definitiva di posizionamento");







        //scorro la matrice IsDadoInPlanciaCorrente e in base al fatto che una posizione abbia un dado adiacente o meno, setto false

        for (int i=0;i<4;i++){
            //System.out.println();
            for(int j=0;j<5;j++){




                if (i==0 && j==0){
                    if ( IsDadoInPlanciaCorrente[i][j+1] || IsDadoInPlanciaCorrente[i+1][j] )
                        NuoviPiazzamentiPermessi[i][j] = false;

                }

                else if (i==0 && j==4){
                    if ( IsDadoInPlanciaCorrente[i][j-1] || IsDadoInPlanciaCorrente[i+1][j] )
                        NuoviPiazzamentiPermessi[i][j] = false;

                }

                else if (i==3 && j==0){
                    if ( IsDadoInPlanciaCorrente[i][j+1] || IsDadoInPlanciaCorrente[i-1][j] )
                        NuoviPiazzamentiPermessi[i][j] = false;

                }

                else if (i==3 && j==4){
                    if ( IsDadoInPlanciaCorrente[i][j-1] || IsDadoInPlanciaCorrente[i-1][j] )
                        NuoviPiazzamentiPermessi[i][j] = false;

                }

                else if (  i==0   &&  (  j==1 ||  j==2 ||  j==3 )) {
                    if ( IsDadoInPlanciaCorrente[i][j+1] || IsDadoInPlanciaCorrente[i][j-1] || IsDadoInPlanciaCorrente[i+1][j] )
                        NuoviPiazzamentiPermessi[i][j] = false;

                }

                else if (  ( i==1 || i==2  )   && j==4  ) {
                    if ( IsDadoInPlanciaCorrente[i+1][j] || IsDadoInPlanciaCorrente[i-1][j] || IsDadoInPlanciaCorrente[i][j-1] )
                        NuoviPiazzamentiPermessi[i][j] = false;

                }

                else if (  i==3   &&  ( j==1 || j==2 || j==3 )) {
                    if ( IsDadoInPlanciaCorrente[i][j+1] || IsDadoInPlanciaCorrente[i][j-1] || IsDadoInPlanciaCorrente[i-1][j] )
                        NuoviPiazzamentiPermessi[i][j] = false;

                }

                else if (  ( i==1 || i==2  )   && j==0  ) {
                    if ( IsDadoInPlanciaCorrente[i+1][j] || IsDadoInPlanciaCorrente[i-1][j] || IsDadoInPlanciaCorrente[i][j+1] )
                        NuoviPiazzamentiPermessi[i][j] = false;

                }

                else{
                    if ( IsDadoInPlanciaCorrente[i+1][j] || IsDadoInPlanciaCorrente[i-1][j] || IsDadoInPlanciaCorrente[i][j+1] || IsDadoInPlanciaCorrente[i][j-1] )
                        NuoviPiazzamentiPermessi[i][j] = false;

                }


                // devo mettere a false tutte le posizioni in cui ci sono i dadi
                if (IsDadoInPlanciaCorrente[i][j])
                    NuoviPiazzamentiPermessi[i][j]=false;





                //System.out.print(NuoviPiazzamentiPermessi[i][j]+" ");



            }
        }



        piazzamentiPermessi = NuoviPiazzamentiPermessi;   //i cambiamenti vengono salvati dentro alla matrice piazzamentiPermessi



    }

    public String[][] rappresentazionePlancia(){
        String[][] result = new String[4][5];
        for (int i =0; i<4; i++){
            for (int j=0; j<5; j++){
                if (grigliaGioco[i][j]!=null){
                    result[i][j] = grigliaGioco[i][j].toString();
                }else{
                    result[i][j]=cartaSchema.getRestrizione(i,j).getDescrizione().toLowerCase();
                }
            }
        }
        return result;

    }
    public Dado[][] getGrigliaGioco() {
        return grigliaGioco;
    }

    public void setGrigliaGioco(Dado[][] grigliaGioco) {
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
               this.grigliaGioco[i][j]=grigliaGioco[i][j];
            }
        }
    }
}
