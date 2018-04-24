package ingSw2018StaglianoVagagginiVergari.model;

import java.Exceptions.*;
import ingSw2018StaglianoVagagginiVergari.model.carteSchema.CartaSchema;
import ingSw2018StaglianoVagagginiVergari.model.carteSchema.Schema;

import java.io.Serializable;

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
            grigliaGioco[i][j] = d;         //alternative implementations: assign the actual object or create a copy of the object

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

    /*helper method for calcolaMosse(), that verifies for each non-null position of the game grid if its specific near cell are available, excluding the elaboration of
    the first move on the grid.
     */

    private void setCelleAdiacenti(int i, int j, Dado dadoSelezionato) {
        if (grigliaGioco[i][j] != null) {
            piazzamentiPermessi[i][j] = false;

            if (i == 0 && j == 0) {
                if (grigliaGioco[i][j + 1] == null && (cartaSchema.getRestrizione(i, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                if (grigliaGioco[i + 1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                if (grigliaGioco[i + 1][j + 1] == null && (cartaSchema.getRestrizione(i + 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                    if (piazzamentiPermessi[i + 1][j + 1] == null) {

                        piazzamentiPermessi[i + 1][j + 1] = true;
                    }
                }else{
                    piazzamentiPermessi[i+1][j+1]=false;
                }
            } else if (i == 0 && j < 4 && j > 0) {
                if (grigliaGioco[i][j - 1] == null && (cartaSchema.getRestrizione(i, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                if (grigliaGioco[i][j + 1] == null && (cartaSchema.getRestrizione(i, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                if (grigliaGioco[i + 1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                if (grigliaGioco[i + 1][j - 1] == null && (cartaSchema.getRestrizione(i + 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                    if (piazzamentiPermessi[i + 1][j - 1] == null) {

                        piazzamentiPermessi[i + 1][j - 1] = true;

                    }
                } else {
                    piazzamentiPermessi[i + 1][j - 1] = false;
                }
                if (grigliaGioco[i + 1][j + 1] == null && (cartaSchema.getRestrizione(i + 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                    if (piazzamentiPermessi[i + 1][j + 1] == null) {

                        piazzamentiPermessi[i + 1][j + 1] = true;
                    }
                } else {
                    piazzamentiPermessi[i + 1][j + 1] = false;
                }

            } else if (i == 0 && j == 4) {
                if (grigliaGioco[i][j - 1] == null && (cartaSchema.getRestrizione(i, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                if (grigliaGioco[i + 1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                if (grigliaGioco[i + 1][j - 1] == null && (cartaSchema.getRestrizione(i + 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                    if (piazzamentiPermessi[i + 1][j - 1] == null) {

                        piazzamentiPermessi[i + 1][j - 1] = true;
                    }
                } else {
                    piazzamentiPermessi[i + 1][j - 1] = false;

                }
            } else if (i >= 1 && i < 3 && j == 0) {
                if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                if (grigliaGioco[i][j + 1] == null && (cartaSchema.getRestrizione(i, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i][j + 1] == null) {

                            piazzamentiPermessi[i][j + 1] = true;
                        }
                    }else{
                        piazzamentiPermessi[i][j + 1] = false;

                    }
                } else {
                    piazzamentiPermessi[i][j + 1] = false;

                }if (grigliaGioco[i + 1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                if (grigliaGioco[i - 1][j + 1] == null && (cartaSchema.getRestrizione(i - 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                    if (piazzamentiPermessi[i - 1][j + 1] == null) {

                        piazzamentiPermessi[i - 1][j + 1] = true;
                    }
                } else {
                    piazzamentiPermessi[i - 1][j + 1] = false;

                }
                if (grigliaGioco[i + 1][j + 1] == null && (cartaSchema.getRestrizione(i + 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                    if (piazzamentiPermessi[i + 1][j + 1] == null) {

                        piazzamentiPermessi[i + 1][j + 1] = true;
                    }
                } else {
                    piazzamentiPermessi[i + 1][j + 1] = false;

                }
            } else if (i > 0 && i < 3 && j > 0 && j < 4) {
                if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i - 1][j] == null) {

                            piazzamentiPermessi[i - 1][j] = true;
                        }
                    }else{
                        piazzamentiPermessi[i - 1][j] = false;

                    }
                } else {
                    piazzamentiPermessi[i - 1][j] = false;

                }if(grigliaGioco[i+1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))){
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i+1][j] == null) {

                            piazzamentiPermessi[i+1][j] = true;
                        }
                    }else{
                        piazzamentiPermessi[i+1][j] = false;
                    }
                }else{
                    piazzamentiPermessi[i+1][j] = false;
                }if (grigliaGioco[i][j+1] == null && (cartaSchema.getRestrizione(i, j+1) == Constraint.NONE || cartaSchema.getRestrizione(i, j+1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j+1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))){
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i][j+1] == null) {

                            piazzamentiPermessi[i][j+1] = true;
                        }
                    }else{
                        piazzamentiPermessi[i][j+1] = false;

                    }
                }else{
                    piazzamentiPermessi[i][j+1] = false;
                }if (grigliaGioco[i][j-1] == null && (cartaSchema.getRestrizione(i, j-1) == Constraint.NONE || cartaSchema.getRestrizione(i, j-1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j-1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))){
                    if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                        if (piazzamentiPermessi[i][j-1] == null) {

                            piazzamentiPermessi[i][j-1] = true;
                        }
                    }else{
                        piazzamentiPermessi[i][j-1] = false;
                    }
                }else{
                    piazzamentiPermessi[i][j-1] = false;
                }if (grigliaGioco[i+1][j+1] == null && (cartaSchema.getRestrizione(i+1, j+1) == Constraint.NONE || cartaSchema.getRestrizione(i+1, j+1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i+1, j+1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))){
                    if (piazzamentiPermessi[i+1][j+1] == null){
                        piazzamentiPermessi[i+1][j+1]=true;
                    }
                }else{
                    piazzamentiPermessi[i+1][j+1]=false;

                }if (grigliaGioco[i+1][j-1] == null && (cartaSchema.getRestrizione(i+1, j-1) == Constraint.NONE || cartaSchema.getRestrizione(i+1, j-1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i+1, j-1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))){
                    if (piazzamentiPermessi[i+1][j-1] == null){
                        piazzamentiPermessi[i+1][j-1]=true;
                    }
                }else{
                    piazzamentiPermessi[i+1][j-1]=false;

                }if (grigliaGioco[i-1][j-1] == null && (cartaSchema.getRestrizione(i-1, j-1) == Constraint.NONE || cartaSchema.getRestrizione(i-1, j-1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i-1, j-1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))){
                    if (piazzamentiPermessi[i-1][j-1] == null){
                        piazzamentiPermessi[i-1][j-1]=true;
                    }
                }else{
                    piazzamentiPermessi[i-1][j-1]=false;

                }if (grigliaGioco[i-1][j+1] == null && (cartaSchema.getRestrizione(i-1, j+1) == Constraint.NONE || cartaSchema.getRestrizione(i-1, j+1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i-1, j+1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))){
                    if (piazzamentiPermessi[i-1][j+1] == null){
                        piazzamentiPermessi[i-1][j+1]=true;
                    }
                }else{
                    piazzamentiPermessi[i-1][j+1]=false;

                }

            } else if (i == 3 && j == 0) {
                    if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                    if (grigliaGioco[i][j + 1] == null && (cartaSchema.getRestrizione(i, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                    if (grigliaGioco[i - 1][j + 1] == null && (cartaSchema.getRestrizione(i - 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                        if (piazzamentiPermessi[i - 1][j + 1] == null) {

                            piazzamentiPermessi[i - 1][j + 1] = true;
                        }

                    } else {
                        piazzamentiPermessi[i - 1][j + 1] = false;

                    }

                } else if (i == 3 && j >= 1 && j < 4) {
                    if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                    if (grigliaGioco[i][j + 1] == null && (cartaSchema.getRestrizione(i, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                    if (grigliaGioco[i][j - 1] == null && (cartaSchema.getRestrizione(i, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                    if (grigliaGioco[i - 1][j - 1] == null && (cartaSchema.getRestrizione(i - 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                        if (piazzamentiPermessi[i - 1][j - 1] == null) {

                            piazzamentiPermessi[i - 1][j - 1] = true;
                        }
                    } else {
                        piazzamentiPermessi[i - 1][j - 1] = false;

                    }
                    if (grigliaGioco[i - 1][j + 1] == null && (cartaSchema.getRestrizione(i - 1, j + 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j + 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                        if (piazzamentiPermessi[i - 1][j + 1] == null) {

                            piazzamentiPermessi[i - 1][j + 1] = true;
                        }
                    } else {
                        piazzamentiPermessi[i - 1][j + 1] = false;

                    }
                } else if (i == 3 && j == 4) {
                    if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                    if (grigliaGioco[i][j - 1] == null && (cartaSchema.getRestrizione(i, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                    if (grigliaGioco[i - 1][j - 1] == null && (cartaSchema.getRestrizione(i - 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                        if (!(grigliaGioco[i][j].getNumero() == dadoSelezionato.getNumero() || grigliaGioco[i][j].getColore().equals(dadoSelezionato.getColore()))) {
                            if (piazzamentiPermessi[i - 1][j - 1] == null) {

                                piazzamentiPermessi[i - 1][j - 1] = true;
                            }
                        }else{
                            piazzamentiPermessi[i - 1][j - 1] = false;

                        }
                    } else {
                        piazzamentiPermessi[i - 1][j - 1] = false;
                    }
                } else if (i > 0 && i < 3 && j == 4) {
                    if (grigliaGioco[i - 1][j] == null && (cartaSchema.getRestrizione(i - 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                    if (grigliaGioco[i + 1][j] == null && (cartaSchema.getRestrizione(i + 1, j) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                    if (grigliaGioco[i][j - 1] == null && (cartaSchema.getRestrizione(i, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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
                    if (grigliaGioco[i - 1][j - 1] == null && (cartaSchema.getRestrizione(i - 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i - 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
                        if (piazzamentiPermessi[i - 1][j - 1] == null) {

                            piazzamentiPermessi[i - 1][j - 1] = true;
                        }
                    } else {
                        piazzamentiPermessi[i - 1][j - 1] = false;

                    }
                    if (grigliaGioco[i + 1][j - 1] == null && (cartaSchema.getRestrizione(i + 1, j - 1) == Constraint.NONE || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(dadoSelezionato.getColore()) || cartaSchema.getRestrizione(i + 1, j - 1).getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))) {
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

        public Boolean[][] calcolaMosse (Dado dadoSelezionato){
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
                    if (restrizione == Constraint.NONE || restrizione.getDescrizione().equals(dadoSelezionato.getColore()) || restrizione.getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))
                        piazzamentiPermessi[i][j] = true;
                    else
                        piazzamentiPermessi[i][j] = false;

                }
                j = 0;
                for (i = 0; i < 4; i++) {
                    Constraint restrizione = cartaSchema.getRestrizione(i, j);
                    if (restrizione == Constraint.NONE || restrizione.getDescrizione().equals(dadoSelezionato.getColore()) || restrizione.getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))
                        piazzamentiPermessi[i][j] = true;
                    else
                        piazzamentiPermessi[i][j] = false;

                }
                j = 4;
                for (i = 0; i < 4; i++) {
                    Constraint restrizione = cartaSchema.getRestrizione(i, j);
                    if (restrizione == Constraint.NONE || restrizione.getDescrizione().equals(dadoSelezionato.getColore()) || restrizione.getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))
                        piazzamentiPermessi[i][j] = true;
                    else
                        piazzamentiPermessi[i][j] = false;

                }
                i = 3;
                for (j = 0; j < 5; j++) {
                    Constraint restrizione = cartaSchema.getRestrizione(i, j);
                    if (restrizione == Constraint.NONE || restrizione.getDescrizione().equals(dadoSelezionato.getColore()) || restrizione.getDescrizione().equals(new Integer(dadoSelezionato.getNumero()).toString()))
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
                        setCelleAdiacenti(i, j, dadoSelezionato);
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
}