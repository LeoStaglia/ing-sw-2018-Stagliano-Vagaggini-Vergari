package ingSw2018StaglianoVagagginiVergari.client;

import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;

import java.util.*;

public class Printer {


    String ANSI_RESET = "\u001B[0m";
    String ANSI_BLACK = "\u001B[30m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_YELLOW = "\u001B[33m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_PURPLE = "\u001B[35m";
    String ANSI_CYAN = "\u001B[36m";
    String ANSI_WHITE = "\u001B[37m";

    String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    String ANSI_RED_BACKGROUND = "\u001B[41m";
    String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    public void printTurnoRound(int turno, int round) {

        System.out.println("\nTurno Corrente: " + turno + "    Round Corrente: " + round );

    }

    public void printCarteObiettivoPubblico(ArrayList<String> listaCartaObiettivoPubblico) {

        System.out.print("\nCarte Obiettivo Pubblico:   ");

        int i = 0;

        for (String carta : listaCartaObiettivoPubblico) {

                System.out.print("(" + i + ")" + carta.substring(0, carta.indexOf('*')));

            System.out.print("  ");
            i++;
        }
        System.out.println();

    }

    public void printCartaObiettivoPrivato(HashMap<String, String> ob, String id) {

        System.out.print("\nCarta Obiettivo Privato:   " + ob.get(id));
        System.out.println();


    }

    public void printCarteUtensile(ArrayList<String> listaCartaUtensile) {

        System.out.print("\nCarte Utensile:   ");


        int i = 0;
        for (String carta : listaCartaUtensile) {


            if (carta.toLowerCase().startsWith("f")) {
                System.out.print("(" + i + ")" + "C=1 " + carta.substring(1, carta.indexOf('*')));

            } else if (carta.toLowerCase().startsWith("t")) {
                System.out.print("(" + i + ")" + "C=2 " + carta.substring(1, carta.indexOf('*')));

            }


            System.out.print("  ");
            i++;

        }
        System.out.println();


    }

    public void printDadiRiserva(List<String> riserva) {

        System.out.print("\nDadi in Riserva:   ");


        int i = 0;
        for (String dado : riserva) {
            System.out.print("(" + i + ") ");
            printDado(dado);

            i++;


        }
        System.out.println();


    }

    public void printDado(String dado) {




        if (dado.contains(Constraint.ROSSO.getDescrizione().toLowerCase()))
            System.out.print(ANSI_RED_BACKGROUND + ANSI_BLACK + dado.charAt(0) + ANSI_RESET);

        else if (dado.contains(Constraint.GIALLO.getDescrizione().toLowerCase()))
            System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + dado.charAt(0) + ANSI_RESET);

        else if (dado.contains(Constraint.VERDE.getDescrizione().toLowerCase()))
            System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + dado.charAt(0) + ANSI_RESET);

        else if (dado.contains(Constraint.BLU.getDescrizione().toLowerCase()))
            System.out.print(ANSI_BLUE_BACKGROUND + ANSI_BLACK + dado.charAt(0) + ANSI_RESET);

        else if (dado.contains(Constraint.VIOLA.getDescrizione().toLowerCase()))
            System.out.print(ANSI_PURPLE_BACKGROUND + ANSI_BLACK + dado.charAt(0) + ANSI_RESET);
        else
            System.out.print("N");


    }

    public void printTracciatoDelRound(ArrayList<String> tracciatoDelRound) {

        System.out.print("\nTracciato del Round: ");

        for (int i = 1; i <= tracciatoDelRound.size(); i++)
            System.out.print(i + " ");

        System.out.print("\n                     ");


        for (String Dado : tracciatoDelRound) {
            printDado(Dado);
            System.out.print(" ");
        }
        System.out.println();


    }

    public void printPlanceGiocatori(Map<String, String[][]> planceGiocatori, int a, int b, String giocatoreCorrente, String tuoId, HashMap<String, Integer> segnaliniGiocatori) {

        String[][][] matriciDaStampare = new String[planceGiocatori.size() + 1][a][b];
        Object[] idGiocatori = new Object[planceGiocatori.size() + 1];
        Integer[] segnaliniGioc = new Integer[planceGiocatori.size()+1];

        int cursoreGiocatoreCorrente = 0;


        //----------- metto la plancia corrispondente al giocatore in prima posizione, in modo da avere una stampa migliore

        Iterator it1 = planceGiocatori.entrySet().iterator();

        while (it1.hasNext()) {

            Map.Entry en1 = (Map.Entry) it1.next();

            if (en1.getKey().equals(tuoId)) {//<<--
                idGiocatori[0] = en1.getKey();

                matriciDaStampare[0] = (String[][]) en1.getValue();
                segnaliniGioc[0] = segnaliniGiocatori.get(en1.getKey());
            } //<<---
        }


        //---------- alloco tutti gli altri giocatori
        int i = 1;
        Iterator it2 = planceGiocatori.entrySet().iterator();

        while (it2.hasNext()) {

            Map.Entry en2 = (Map.Entry) it2.next();

            if (!en2.getKey().equals(tuoId)) {
                idGiocatori[i] = en2.getKey();
                matriciDaStampare[i] = (String[][]) en2.getValue();
                segnaliniGioc[i] = segnaliniGiocatori.get(en2.getKey());

                if (en2.getKey().equals(giocatoreCorrente))
                    cursoreGiocatoreCorrente = i;

                i++;
            }

        }


        System.out.println();


        for (int righe = 0; righe < a; righe++) {

            if (righe != 0)
                System.out.println("|");

            for (int giocatore = 0; giocatore < planceGiocatori.size(); giocatore++) {

                System.out.print("|");

                if (righe == 0) {
                    if (giocatore == 0)
                        System.out.print(" Tua Plancia    ");
                    else
                        System.out.print(" Giocatore " + idGiocatori[giocatore] + "    ");

                } else if(righe == a-2){

                    System.out.print(" Segnalini: "+ segnaliniGioc[giocatore]);
                    if (giocatore == 0)
                        System.out.print("   ");
                    else {
                        for (int x = 0; x < idGiocatori[giocatore].toString().length() + 2; x++)
                            System.out.print(" ");
                    }

                }
                else if ((righe == a - 1) && (giocatore == cursoreGiocatoreCorrente)) {
                    System.out.print(" Corrente");

                    if (giocatore == 0)
                        System.out.print("       ");
                    else {
                        for (int x = 0; x < idGiocatori[giocatore].toString().length() + 6; x++)
                            System.out.print(" ");

                    }


                } else if (giocatore != 0) {
                    for (int x = 0; x < idGiocatori[giocatore].toString().length() + 15; x++)
                        System.out.print(" ");
                } else
                    System.out.print("                ");


                for (int col = 0; col < b; col++) {

                    //===================== QUESTO è IL CORPO DELLA STAMPA


                    //questa prima parte è la stampa della cella in cui non è stato posizionato il dado

                    if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.ROSSO.getDescrizione()))
                        System.out.print(ANSI_RED + "R" + ANSI_RESET);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.GIALLO.getDescrizione()))
                        System.out.print(ANSI_YELLOW + "G" + ANSI_RESET);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.VERDE.getDescrizione()))
                        System.out.print(ANSI_GREEN + "V" + ANSI_RESET);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.BLU.getDescrizione()))
                        System.out.print(ANSI_BLUE + "B" + ANSI_RESET);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.VIOLA.getDescrizione()))
                        System.out.print(ANSI_PURPLE + "V" + ANSI_RESET);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.UNO.getDescrizione()))
                        System.out.print(1);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.DUE.getDescrizione()))
                        System.out.print(2);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.TRE.getDescrizione()))
                        System.out.print(3);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.QUATTRO.getDescrizione()))
                        System.out.print(4);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.CINQUE.getDescrizione()))
                        System.out.print(5);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.SEI.getDescrizione()))
                        System.out.print(6);

                    else if (matriciDaStampare[giocatore][righe][col].equalsIgnoreCase(Constraint.NONE.getDescrizione()))
                        System.out.print("N");

                    else {

                        //questa parte stampa in maniera diversa le celle in cui è stato piazzato un dado

                        if (matriciDaStampare[giocatore][righe][col].contains(Constraint.ROSSO.getDescrizione().toLowerCase()))
                            System.out.print(ANSI_RED_BACKGROUND + ANSI_BLACK + matriciDaStampare[giocatore][righe][col].charAt(0) + ANSI_RESET);

                        else if (matriciDaStampare[giocatore][righe][col].contains(Constraint.GIALLO.getDescrizione().toLowerCase()))
                            System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + matriciDaStampare[giocatore][righe][col].charAt(0) + ANSI_RESET);

                        else if (matriciDaStampare[giocatore][righe][col].contains(Constraint.VERDE.getDescrizione().toLowerCase()))
                            System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + matriciDaStampare[giocatore][righe][col].charAt(0) + ANSI_RESET);

                        else if (matriciDaStampare[giocatore][righe][col].contains(Constraint.BLU.getDescrizione().toLowerCase()))
                            System.out.print(ANSI_BLUE_BACKGROUND + ANSI_BLACK + matriciDaStampare[giocatore][righe][col].charAt(0) + ANSI_RESET);

                        else if (matriciDaStampare[giocatore][righe][col].contains(Constraint.VIOLA.getDescrizione().toLowerCase()))
                            System.out.print(ANSI_PURPLE_BACKGROUND + ANSI_BLACK + matriciDaStampare[giocatore][righe][col].charAt(0) + ANSI_RESET);


                    }


                    System.out.print(" ");


                    //=====================FINE DEL CORPO DELLA STAMPA

                }
                System.out.print("    ");
            }

        }
        System.out.print("|");


        System.out.println();


    }

    public void printPunteggi(HashMap<String, Integer> punteggi, String id, String vincitore) {


        //il punteggio del giocatore corrente

        System.out.println("Il tuo punteggio è: " + punteggi.get(id));

        Iterator it1 = punteggi.entrySet().iterator();

        while (it1.hasNext()) {

            Map.Entry e = (Map.Entry) it1.next();

            //il punteggio degli altri giocatori
            if (!((String) e.getKey()).equalsIgnoreCase(id))
                System.out.println("il punteggio di " + (String) e.getKey() + " é " + e.getValue());

        }
        if (id.equals(vincitore)) {
            System.out.println("HAI VINTO !!!!");
        } else System.out.println("HAI PERSO :(\nil vincitore è: " + vincitore);


    }

    public void printDescrizioneCartaUtensile(ArrayList<String> listaCartaUtensile, int scelta) {


        int i = 0;
        for (String carta : listaCartaUtensile) {

            if (scelta == i)
                System.out.println("Descrizione:  " + carta.substring(carta.indexOf('*') + 1, carta.length()));

            i++;

        }
        System.out.println();


    }

    public void printDescrizioneCartaObiettivoPubblico(ArrayList<String> listaCartaObiettivoPubblico, int scelta) {


        int i = 0;
        for (String carta :listaCartaObiettivoPubblico) {

            if (scelta == i)
                System.out.println("Descrizione:  " + carta.substring(carta.indexOf('*') + 1, carta.length()));

            i++;

        }
        System.out.println();


    }









    public void printTavoloDiGioco( int turno, int round,HashMap<String, Integer> segnaliniGiocatori, ArrayList<String> tracciatoDelRound, HashMap<String, String[][]> planceGiocatori, int a, int b, String giocatoreCorrente, String id, ArrayList<String> carteObiettivoPubblico, HashMap<String, String> carteObiettivoPrivato, ArrayList<String> carteUtensile, ArrayList<String> dadiRiserva, String dadoSelezionato  ){




        System.out.println("\n<===========================================================================================================================================>");

        printTurnoRound(turno, round);

        printTracciatoDelRound(tracciatoDelRound);

        printPlanceGiocatori(planceGiocatori, 4, 5, giocatoreCorrente, id,segnaliniGiocatori);

        printCarteObiettivoPubblico(carteObiettivoPubblico);

        printCartaObiettivoPrivato(carteObiettivoPrivato, id);

        printCarteUtensile(carteUtensile);

        printDadiRiserva(dadiRiserva);

        //System.out.print("\nDado selezionato:   ");

        //printDado(dadoSelezionato);


        System.out.println("\n\n<===========================================================================================================================================>");


    }

    public void printCarteSchema(String[][] schemaFronte1, String[][] schemaRetro1, String[][] schemaFronte2, String[][] schemaRetro2, int a, int b, Integer[] difficoltàCarteSchema, String[] nomeCarteSchema) {

        String[][][] matriciDaStampare = new String[4 + 1][a][b];  //numero di plance +1
        Object[] idDiff= new Object[4 + 1];  //numero di plance +1


        matriciDaStampare[0] = schemaFronte1;
        matriciDaStampare[1] = schemaRetro1;
        matriciDaStampare[2] = schemaFronte2;
        matriciDaStampare[3] = schemaRetro2;


        idDiff[0] = nomeCarteSchema[0] + "  Diff: " + difficoltàCarteSchema[0];
        idDiff[1] = nomeCarteSchema[1] + "  Diff: " + difficoltàCarteSchema[1];
        idDiff[2] = nomeCarteSchema[2] + "  Diff: " + difficoltàCarteSchema[2];
        idDiff[3] = nomeCarteSchema[3] + "  Diff: " + difficoltàCarteSchema[3];


        for (int righe = 0; righe < a; righe++) {

            if (righe != 0)
                System.out.println("|");

            for (int carta = 0; carta < 4; carta++) {

                System.out.print("|");


                if (righe == 0)
                    System.out.print(nomeCarteSchema[carta] + "    ");
                else if (righe == 3){
                    System.out.print("Diff: " + difficoltàCarteSchema[carta]);
                    for (int x = 0; x < nomeCarteSchema[carta].toString().length() -3; x++)
                        System.out.print(" ");
                }
                else {
                    for (int x = 0; x < nomeCarteSchema[carta].toString().length() + 4; x++)
                        System.out.print(" ");
                }










                for (int col = 0; col < b; col++) {

                    //===================== QUESTO è IL CORPO DELLA STAMPA


                    //questa prima parte è la stampa della cella in cui non è stato posizionato il dado

                    if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.ROSSO.getDescrizione()))
                        System.out.print(ANSI_RED + "R" + ANSI_RESET);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.GIALLO.getDescrizione()))
                        System.out.print(ANSI_YELLOW + "G" + ANSI_RESET);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.VERDE.getDescrizione()))
                        System.out.print(ANSI_GREEN + "V" + ANSI_RESET);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.BLU.getDescrizione()))
                        System.out.print(ANSI_BLUE + "B" + ANSI_RESET);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.VIOLA.getDescrizione()))
                        System.out.print(ANSI_PURPLE + "V" + ANSI_RESET);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.UNO.getDescrizione()))
                        System.out.print(1);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.DUE.getDescrizione()))
                        System.out.print(2);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.TRE.getDescrizione()))
                        System.out.print(3);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.QUATTRO.getDescrizione()))
                        System.out.print(4);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.CINQUE.getDescrizione()))
                        System.out.print(5);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.SEI.getDescrizione()))
                        System.out.print(6);

                    else if (matriciDaStampare[carta][righe][col].equalsIgnoreCase(Constraint.NONE.getDescrizione()))
                        System.out.print("N");


                    System.out.print(" ");


                    //=====================FINE DEL CORPO DELLA STAMPA

                }
                System.out.print("    ");
            }

        }

        System.out.print("|\n");


    }

    public void printScritta(String scritta, String colore){

        if(colore.equalsIgnoreCase("rosso")){
            System.out.println(ANSI_RED+scritta+ANSI_RESET);
        }
        if(colore.equalsIgnoreCase("verde")){
            System.out.println(ANSI_GREEN+scritta+ANSI_RESET);
        }
        if(colore.equalsIgnoreCase("giallo")){
            System.out.println(ANSI_YELLOW+scritta+ANSI_RESET);
        }
        if(colore.equalsIgnoreCase("blu")){
            System.out.println(ANSI_BLUE+scritta+ANSI_RESET);
        }
        if(colore.equalsIgnoreCase("viola")){
            System.out.println(ANSI_PURPLE+scritta+ANSI_RESET);
        }



    }

    public void printPlancia(String[][] plancia){

        int a = 4;
        int b = 5;

        for (int r = 0; r < a ; r++){

            System.out.println();
            for (int c = 0; c < b; c++) {

                //===================== QUESTO è IL CORPO DELLA STAMPA


                //questa prima parte è la stampa della cella in cui non è stato posizionato il dado

                String stringa = plancia[r][c];

                if (stringa.equalsIgnoreCase(Constraint.ROSSO.getDescrizione()))
                    System.out.print(ANSI_RED + "R" + ANSI_RESET);

                else if (stringa.equalsIgnoreCase(Constraint.GIALLO.getDescrizione()))
                    System.out.print(ANSI_YELLOW + "G" + ANSI_RESET);

                else if (stringa.equalsIgnoreCase(Constraint.VERDE.getDescrizione()))
                    System.out.print(ANSI_GREEN + "V" + ANSI_RESET);

                else if (stringa.equalsIgnoreCase(Constraint.BLU.getDescrizione()))
                    System.out.print(ANSI_BLUE + "B" + ANSI_RESET);

                else if (stringa.equalsIgnoreCase(Constraint.VIOLA.getDescrizione()))
                    System.out.print(ANSI_PURPLE + "V" + ANSI_RESET);

                else if (stringa.equalsIgnoreCase(Constraint.UNO.getDescrizione()))
                    System.out.print(1);

                else if (stringa.equalsIgnoreCase(Constraint.DUE.getDescrizione()))
                    System.out.print(2);

                else if (stringa.equalsIgnoreCase(Constraint.TRE.getDescrizione()))
                    System.out.print(3);

                else if (stringa.equalsIgnoreCase(Constraint.QUATTRO.getDescrizione()))
                    System.out.print(4);

                else if (stringa.equalsIgnoreCase(Constraint.CINQUE.getDescrizione()))
                    System.out.print(5);

                else if (stringa.equalsIgnoreCase(Constraint.SEI.getDescrizione()))
                    System.out.print(6);

                else if (stringa.equalsIgnoreCase(Constraint.NONE.getDescrizione()))
                    System.out.print("N");

                else {

                    //questa parte stampa in maniera diversa le celle in cui è stato piazzato un dado

                    if (stringa.contains(Constraint.ROSSO.getDescrizione().toLowerCase()))
                        System.out.print(ANSI_RED_BACKGROUND + ANSI_BLACK + stringa.charAt(0) + ANSI_RESET);

                    else if (stringa.contains(Constraint.GIALLO.getDescrizione().toLowerCase()))
                        System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + stringa.charAt(0) + ANSI_RESET);

                    else if (stringa.contains(Constraint.VERDE.getDescrizione().toLowerCase()))
                        System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + stringa.charAt(0) + ANSI_RESET);

                    else if (stringa.contains(Constraint.BLU.getDescrizione().toLowerCase()))
                        System.out.print(ANSI_BLUE_BACKGROUND + ANSI_BLACK + stringa.charAt(0) + ANSI_RESET);

                    else if (stringa.contains(Constraint.VIOLA.getDescrizione().toLowerCase()))
                        System.out.print(ANSI_PURPLE_BACKGROUND + ANSI_BLACK + stringa.charAt(0) + ANSI_RESET);

                }


                System.out.print(" ");


                //=====================FINE DEL CORPO DELLA STAMPA

            }

        }

        System.out.println();










    }


}
