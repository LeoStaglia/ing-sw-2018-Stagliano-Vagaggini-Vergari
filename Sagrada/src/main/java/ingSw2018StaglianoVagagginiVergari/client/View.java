package ingSw2018StaglianoVagagginiVergari.client;

import Eccezioni.FullGameException;
import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;
import ingSw2018StaglianoVagagginiVergari.server.model.FactoryCartaObiettivoPubblico;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


/** Convenzioni usate nei commenti:
 *
 * i metodi setGame, timer1, timer1, scorriCartaUtensile non servono ai fini della partita, servono solo per avere una versione già impostata
 *
 *
 * TODO COMANDI linee di codice che chiamano metodi del controller e devono ancora essere inseriti
 *
 * TODO RIMUOVERE  cancellare le righe di codice per avere il corretto comportamento
 *
 * TODO RIVEDERE scelte implementative da ripensare eventualmente
 *
 *
 * */



public class View extends UnicastRemoteObject implements GameObserver, Remote {



    private boolean simulazione = false; //TODO questo attributo permette di switchare velocemante tra una partita già impostata ed una che richiede parametri dal model


    private HashMap<String, Integer> punteggi;

    private boolean mossaCorretta = false;



    private ArrayList<Integer> parametri = new ArrayList<Integer>();

    private int punteggio;
    private boolean printPunteggio = false;



    //private String id;
    private RemoteController controller;
    private Scanner command;
    //private ViewStatus status;
    private String[][] schemaFronte1;
    private String[][] schemaRetro1;
    private String[][] schemaFronte2;
    private String[][] schemaRetro2;
    private String obiettivoPrivato;
    private HashMap<String,String> carteObiettivoPrivato=new HashMap<>();
    //private boolean updateView;
    private boolean fronteScelto;
    private boolean carta1;
    private boolean tool6piazzabile;
    private boolean usernameOK=false;
    private String tool6Dado;

    //
    private boolean startGame=false;
    //

    //==================

    private ArrayList<String> carteUtensile= new ArrayList<>();

    private HashMap<String, String[][]> planceGiocatori = new HashMap<String, String[][]>();

    private String giocatoreCorrente;

    private String id ;

    private ArrayList<String> carteObiettivoPubblico = new ArrayList<>();

    private ArrayList<String> dadiRiserva = new ArrayList<>();

    private String dadoSelezionato ;

    private Integer numeroDadoSelezionato;

    private String cartaInUso;

    private boolean updateView = false;

    private ViewStatus status;

    private Scanner inputCommand = new Scanner(System.in);

    private boolean passaturno = false;

    private int flagSceltaDado = 0;  // 0 -> il dado non è ancora stato selezionato   1 -> il dado selezioanto viene posizionato

    private int flagSceltaCartaUtensile = 0;  // 0 -> non è stato usato l'effetto di nessuna carta  1 -> ho già usato l'effetto di una carta

    private  int turno;

    private int round;

    private ArrayList<String> tracciatoDelRound;

    private HashSet<Integer> azioniGiocatore;

    private String vincitore;

    //==================t


    public View(RemoteController controller) throws RemoteException {
        this.controller = controller;
        setStatus(ViewStatus.Preparazione);
        command = new Scanner(System.in);
        updateView = false;
    }

    public void run() throws IOException, InterruptedException {
        int cmd = 0;
        String username=null;
        boolean carta1 = false;
        while (cmd != 1 && cmd != 2) {
            System.out.println("(1) Per partecipare alla partita (2) per uscire");
            cmd = command.nextInt();
        }
        if (cmd == 2) {
            System.exit(0);
        } else if (cmd == 1) {
            while(!usernameOK) {
                System.out.println("Inserisci uno username da usare in partita: ");

                username = command.next();
                try {
                    controller.partecipaPartita(this, username);
                    while(!updateView){
                        System.out.println("");
                    }
                    updateView=false;
                } catch (FullGameException e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
            }
            System.out.println("Preparazione partita... Attendi. (0) per uscire");
            while (status == ViewStatus.Preparazione) {
                if (System.in.available() > 0) {
                    if (command.nextInt() == 0) {
                        controller.abbandonaPartita(this, username);
                        System.exit(0);
                    }
                }
            }
            System.out.println(status);
            while (!updateView) {
                System.out.print("");
            }
            updateView = false;
            if (getStatus() == ViewStatus.SelezioneSchema) {

                //qui avviene la stampa delle 4 carte schema, una delle quali verrà scelta dall'utente

                // prototipo della funzione:  private void stampaSchema(boolean carta1, boolean fronte);


                System.out.println();
                printCarteSchema(schemaFronte1,schemaRetro1,schemaFronte2,schemaRetro2, 4 ,5);
                System.out.println();



                cmd = 0;
                while (cmd != 1 && cmd != 2) {
                    System.out.println("(1) per scegliere la prima carta (2) per scegliere la seconda carta");
                    cmd = command.nextInt();
                }
                if (cmd == 1) {
                    carta1 = true;
                } else {
                    carta1 = false;
                }
                cmd = 0;
                while (cmd != 1 && cmd != 2) {
                    System.out.println("(1) per scegliere il fronte (2) per scegliere il retro");
                    cmd = command.nextInt();
                }
                if (cmd == 1) {
                    controller.scegliSchema(this, id, carta1, true);
                } else {
                    controller.scegliSchema(this, id, carta1, false);
                }
                while (!updateView) {

                }
                updateView = false;


            }




            while(!startGame){
                System.out.print("");
            }



            while (getStatus() == ViewStatus.SvolgimentoPartita) {



                //anzichè fare una pulizia dello schermo, scorro molto in giù   --- NB: dovrà essere sostituito con "clearscreen"

                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");



                printTavoloDiGioco();

                updateView = false;

                passaturno=false;

                if (id.equalsIgnoreCase(giocatoreCorrente) && passaturno == false) { //TODO RIVEDERE non serve il parametro passaturno

                    System.out.println("\nSEI IL GIOCATORE CORRENTE!\n");


                    //stampe differite in base a quali comandi abbia già dato l'utente in input (se l'utente sceglie inizialmente di selezionare un dado, sarà settato il flagSceltaDado , cosi che si stampi una scelta di input diversa)

                    if (flagSceltaDado == 0)
                        System.out.println("(1) seleziona un dado dalla riserva");
                    else if (flagSceltaDado == 1)
                        System.out.println("(1) posiziona il dado scelto sulla tua plancia");
                    else
                        System.out.println("(-) hai già posizionato il dado");

                    if (flagSceltaCartaUtensile == 0)
                        System.out.println("(2) utilizza una carta utensile");
                    else
                        System.out.println("(-) hai già usato l'effetto di una carta");

                    System.out.println("(3) passa il turno\n(4) descrizione carta utensile\n\nSCEGLI:");


                    cmd = inputCommand.nextInt();


                    while (cmd != 1 && cmd != 2 && cmd != 3 && cmd != 4) {
                        cmd = inputCommand.nextInt();
                    }

                    if (cmd == 1 && flagSceltaDado == 0) {

                        cmd = 0;
                        System.out.println("inserisci il numero del dado che vuoi scegliere");//dovrei  verificare che la scelta sia corretta
                        cmd = inputCommand.nextInt(); //dentro a cmd è contenuto il dado selezionato

                        numeroDadoSelezionato = cmd;  // dato che la selezione del dado avviene in un istante diverso da quello dell'effettivo utilizzo, devo tenere traccia dellla posizione del dado selezionato all'interno della riserva;



                        if(simulazione)
                            dadoSelezionato = dadiRiserva.get(cmd); //TODO RIMUOVERE solo per prova... lo inserisco solo per far vedere che effettivamente l'aggiornamento funziona


                        mossaCorretta= false;
                        while(mossaCorretta == false){


                            System.out.println("inserisci le coordinate in cui vuoi posizionare il dado: ");

                            System.out.print("i ( 0 <= x <= 3):");

                            int i = inputCommand.nextInt();

                            System.out.print("j ( 0 <= x <= 4):");

                            int j = inputCommand.nextInt();


                            //chiamo il metodo del controller che mi permette di passare i parametri per la mossa scelta.
                            try {
                                parametri.add(0,1); // ho scelto di posizionare il dado
                                parametri.add(1,numeroDadoSelezionato); // passo la posizione del dado selezionato all'interno della riserva
                                parametri.add(2,i);
                                parametri.add(3,j);
                                controller.svolgimentoPartita(this,parametri);  //TODO RIVEDERE il fatto che debba essere passata anche la vista deve essere ripensato
                                System.out.println("\nverifico convalida mossa\n");
                                parametri = new ArrayList<Integer>(); // TODO RIVEDERE non è detto che sia necessario new dato che viene fatto solo per precauzione. potrebbe essere sufficiente lasciarlo nelle condizioni attuali senza reinizializzare, in modo da sapere quale è l'ultima chiamata che è stata fatta al model.

                            }catch(MossaIllegaleException e){
                                System.out.print("mossa non consentita");  //TODO RIVEDERE da rivedere se funziona
                            }



                        }






                      //  flagSceltaDado = 2;

                    } else if (cmd == 2 && flagSceltaCartaUtensile == 0) {
                        updateView=false;
                        System.out.println("inserisci il numero della carta che vuoi scegliere");
                        cmd = inputCommand.nextInt(); //dentro a cmd è contenuta la carta selezionata
                        //chiamo il metodo del controller che mi permette di passare i parametri per la mossa scelta.
                        parametri=new ArrayList<>();
                        try {
                            parametri.add(0,2); // ho scelto di selezionare la carta utensile
                            parametri.add(1,cmd);   // questo è il numero della carta utensile che ha selezionato


                            //TODO devono essere aggiunti i parametri necessari per le varie carte utensile

                            controller.svolgimentoPartita(this,parametri);  //TODO RIVEDERE il fatto che debba essere passata anche la vista deve essere ripensato
                            parametri.clear(); // TODO RIVEDERE non è detto che sia necessario new dato che viene fatto solo per precauzione. potrebbe essere sufficiente lasciarlo nelle condizioni attuali senza reinizializzare, in modo da sapere quale è l'ultima chiamata che è stata fatta al model.
                        }catch(MossaIllegaleException e){
                            System.out.print("carta non consentita");  //TODO RIVEDERE da rivedere se funziona
                        }


                        System.out.println("controller.cartaselezionata(cmd);"); // TODO COMANDI questa chiamata è scorretta, gli passo la chiave quando il controller non sa di cosa si tratta





                        scorriCartaUtensile(cmd);  //TODO RIMUOVERE serve solo per avere la simulazione di una partita



                        //====================  QUI VERRANNO FATTE RICHIESTE DIVERSE IN BASE ALLA CARTA UTENSILE


                        System.out.println(" -- ... CARTA UTENSILE ... --");
                        parametri.clear();
                        if (cartaInUso.equalsIgnoreCase("Pinza Sgrossatrice")) {
                            System.out.println("Seleziona un dado dalla riserva, inserisci la posizione");
                            parametri.add(0, inputCommand.nextInt());
                            System.out.println("Inserisci 1 per incrementare, -1 per decrementare");
                            parametri.add(1,inputCommand.nextInt());
                            controller.usaCartaUtensile(this,parametri);

                        } else if (cartaInUso.equalsIgnoreCase("Pennello Per Eglomise")) {
                            parametri.clear();
                            System.out.println("Inserisci la riga del dado che vuoi spostare:(0 <= x <= 3)");
                            parametri.add(0,inputCommand.nextInt());
                            System.out.println("Inserisci la colonna del dado che vuoi spostare:(0 <= y <= 4)");
                            parametri.add(1,inputCommand.nextInt());
                            System.out.println("Inserisci la riga in cui vuoi spostare il dado:(0 <= x <= 3)");
                            parametri.add(2,inputCommand.nextInt());
                            System.out.println("Inserisci la colonna in cui vuoi spostare il dado:(0 <= y <= 3)");
                            parametri.add(3,inputCommand.nextInt());
                            controller.usaCartaUtensile(this,parametri);

                            System.out.println("controller.usaCartaUtensile(GameObserver view,ArrayList<Integer> parametri)");//TODO COMANDI devo chiamare i comandi del controller

                        } else if (cartaInUso.equalsIgnoreCase("Alesatore per lamina di rame")) {
                            parametri.clear();
                            System.out.println("Inserisci la riga del dado che vuoi spostare:(0 <= x <= 3)");
                            parametri.add(0,inputCommand.nextInt());
                            System.out.println("Inserisci la colonna del dado che vuoi spostare:(0 <= y <= 4)");
                            parametri.add(1,inputCommand.nextInt());
                            System.out.println("Inserisci la riga in cui vuoi spostare il dado:(0 <= x <= 3)");
                            parametri.add(2,inputCommand.nextInt());
                            System.out.println("Inserisci la colonna in cui vuoi spostare il dado:(0 <= y <= 3)");
                            parametri.add(3,inputCommand.nextInt());
                            controller.usaCartaUtensile(this,parametri);

                        } else if (cartaInUso.equalsIgnoreCase("Lathekin")) {
                            parametri.clear();


                        } else if (cartaInUso.equalsIgnoreCase("Taglierina Circolare")) {
                            parametri.clear();
                            System.out.println("inserisci il numero del dado della riserva che vuoi scambiare con uno sul Tracciato del Round");
                            parametri.add(0,inputCommand.nextInt());
                            System.out.println("inserisci il numero del dado del Tracciato del Round che vuoi scambiare con quello selezionato dalla riserva");
                            parametri.add(1,inputCommand.nextInt());
                            controller.usaCartaUtensile(this,parametri);

                        } else if (cartaInUso.equalsIgnoreCase("Pennello Per Pasta Salda")) {
                            parametri.clear();
                            System.out.println("Inserisci il numero del dado della riserva che vuoi lanciare nuovamente");
                            parametri.add(0, inputCommand.nextInt());
                            controller.usaCartaUtensile(this, parametri);
                            while(!updateView){
                                System.out.print("");
                            }
                            updateView=false;
                            if (tool6piazzabile){
                                parametri.clear();
                                printDado(tool6Dado);
                                System.out.println("");
                                System.out.println("Inserisci la riga in cui vuoi piazzare il dado lanciato (0<= x <= 3):");
                                parametri.add(0, inputCommand.nextInt());
                                System.out.println("Inserisci la colonna in cui vuoi piazzare il dado lanciato (0<= y <= 4):");
                                parametri.add(1, inputCommand.nextInt());
                                controller.usaCartaUtensile(this, parametri);

                            }else{
                                updateView=true;

                            }

                        } else if (cartaInUso.equalsIgnoreCase("Martelletto")) {
                            controller.usaCartaUtensile(this,parametri);

                        } else if (cartaInUso.equalsIgnoreCase("Tenaglia A Rotelle")) {
                            parametri.clear();
                            controller.usaCartaUtensile(this,parametri);

                        } else if (cartaInUso.equalsIgnoreCase("Riga In Sughero")) {
                            parametri.clear();
                            System.out.println("inserisci posizione dado in riserva da posizionare");
                            parametri.add(0,inputCommand.nextInt());
                            System.out.println("inserisci riga dove vuoi piazzare 0<=x<=3");
                            parametri.add(1,inputCommand.nextInt());
                            System.out.println("inserisci colonna dove vuoi piazzare 0<=y<=4");
                            parametri.add(2,inputCommand.nextInt());
                            controller.usaCartaUtensile(this,parametri);

                        } else if (cartaInUso.equalsIgnoreCase("Tampone Diamantato")) {
                            parametri.clear();
                            System.out.println("inserisci posizione dado riserva che vuoi girare(il dado sarà reinserito in coda alla riserva)");
                            parametri.add(0,inputCommand.nextInt());
                            controller.usaCartaUtensile(this,parametri);

                        } else if (cartaInUso.equalsIgnoreCase("Diluente Per Pasta Salda")) {
                            parametri.add(0,inputCommand.nextInt());
                            parametri.add(1,inputCommand.nextInt());
                            parametri.add(2,inputCommand.nextInt());
                            parametri.add(3,inputCommand.nextInt());

                            controller.usaCartaUtensile(this,parametri);
                        } else if (cartaInUso.equalsIgnoreCase("Taglierina Manuale")) {
                            parametri.add(0,inputCommand.nextInt());
                            parametri.add(1,inputCommand.nextInt());
                            parametri.add(2,inputCommand.nextInt());
                            parametri.add(3,inputCommand.nextInt());
                            parametri.add(4,inputCommand.nextInt());
                            controller.usaCartaUtensile(this,parametri);
                        }

                        //====================

                        //flagSceltaCartaUtensile = 1;


                    } else if (cmd == 3) {
                        passaturno = true;
                        //flagSceltaCartaUtensile=0;
                       // flagSceltaDado=0;

                        //chiamo il metodo del controller che mi permette di passare i parametri per la mossa scelta.
                        try {
                            parametri.add(0,3); // ho scelto di passare il turno

                            controller.svolgimentoPartita(this,parametri);  //TODO RIVEDERE il fatto che debba essere passata anche la vista deve essere ripensato
                            parametri = new ArrayList<Integer>(); // TODO RIVEDERE non è detto che sia necessario new dato che viene fatto solo per precauzione. potrebbe essere sufficiente lasciarlo nelle condizioni attuali senza reinizializzare, in modo da sapere quale è l'ultima chiamata che è stata fatta al model.

                        }catch(MossaIllegaleException e){
                            System.out.print("mossa non consentita");  //TODO RIVEDERE da rivedere se funziona
                        }



                    } else if (cmd == 4) {

                        System.out.print("Inserisci il numero della carta della quale vuoi la descrizione:");
                        int c = inputCommand.nextInt();

                        System.out.println();
                        printDescrizioneCartaUtensile(carteUtensile, c);

                        System.out.println("\nINSERISCI UN CARATTERE PER CONTINUARE");
                        String s = inputCommand.next();

                    }


                    if(simulazione)
                        timer1();  //TODO RIMUOVERE serve solo per avere la simulazione di una partita - permette di settare updateView a true dopo la chiamata del metodo


                    System.out.println("\n\n\nattendi aggiornamento del tavolo di gioco...");


                } else {

                    System.out.println("\nNON SEI IL GIOCATORE CORRENTE!\n");

                    if(simulazione)
                        timer2(); //TODO RIMUOVERE serve solo per avere la simulazione di una partita - permette di settare updateView a true dopo la chiamata del metodo

                }


                // in attesa di un aggiornamento alla view

                while (updateView == false) {
                    System.out.print("");
                }






            }
            if (getStatus() == ViewStatus.CalcoloPunteggio){

                /**
                 * Devo fare un metodo di notifica che può essere chiamato dal model che permetta di variare l'attributo della View "punteggio", per poi stamparlo
                 * */


                System.out.println("CALCOLO DEL PUNTEGGIO FINALE...");

                System.out.println("\n\n\n\n");


                printPunteggi(punteggi);



            }



        }

    }

    @Override
    public synchronized void notifyUser(String id, String[][] schemaFronte1, String[][] schemaRetro1, String[][] schemaFronte2, String[][] schemaRetro2, String obiettivoPrivato) throws RemoteException {
        this.id = id;
        this.setStatus(ViewStatus.SelezioneSchema);
        this.schemaFronte1 = schemaFronte1;
        this.schemaRetro1 = schemaRetro1;
        this.schemaFronte2 = schemaFronte2;
        this.schemaRetro2 = schemaRetro2;
        this.obiettivoPrivato = obiettivoPrivato;
        updateView = true;
    }

    public synchronized void setStatus(ViewStatus status) {
        this.status = status;
    }

    public synchronized ViewStatus getStatus() {
        return this.status;
    }


    private void stampaSchema(boolean carta1, boolean fronte) {
        for (int i = 0; i < 4; i++) {
            System.out.println("");
            for (int j = 0; j < 5; j++) {
                if (carta1) {
                    if (fronte) {
                        System.out.print(schemaFronte1[i][j]);
                    } else {
                        System.out.print(schemaRetro1[i][j]);
                    }
                    if (j != 4) {
                        System.out.print(" ");
                    }
                } else {
                    if (fronte) {
                        System.out.print(schemaFronte2[i][j]);
                    } else {
                        System.out.print(schemaRetro2[i][j]);
                    }
                    if (j != 4) {
                        System.out.print(" ");
                    }
                }
            }
        }
        System.out.println("");
    }

    @Override
    public synchronized void notifyScheme(boolean carta1, boolean fronteScelto) {
        updateView = true;
        this.fronteScelto = fronteScelto;
        this.carta1 = carta1;
        setStatus(ViewStatus.SvolgimentoPartita);
    }


    //=========================


    public void printColoredMatrix(String[][] matrix, int a, int b) {


        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";


        for (int i = 0; i < a; i++) {
            System.out.println();
            for (int j = 0; j < b; j++) {

                if (matrix[i][j].equalsIgnoreCase(Constraint.ROSSO.getDescrizione()))
                    System.out.print(ANSI_RED + "R" + ANSI_RESET);

                else if (matrix[i][j].equalsIgnoreCase(Constraint.GIALLO.getDescrizione()))
                    System.out.print(ANSI_YELLOW + "G" + ANSI_RESET);

                else if (matrix[i][j].equalsIgnoreCase(Constraint.VERDE.getDescrizione()))
                    System.out.print(ANSI_GREEN + "V" + ANSI_RESET);

                else if (matrix[i][j].equalsIgnoreCase(Constraint.BLU.getDescrizione()))
                    System.out.print(ANSI_BLUE + "B" + ANSI_RESET);

                else if (matrix[i][j].equalsIgnoreCase(Constraint.VIOLA.getDescrizione()))
                    System.out.print(ANSI_PURPLE + "V" + ANSI_RESET);

                else
                    System.out.print(matrix[i][j].toUpperCase());  //per stampare i numeri

                System.out.print(" ");
            }
        }


    }

    public void printPlanceGiocatori(Map<String, String[][]> planceGiocatori, int a, int b, String giocatoreCorrente, String tuoId) {


        //dichiarazione di tutti i valori che permettono la stampa

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

        String[][][] matriciDaStampare = new String[planceGiocatori.size()+1][a][b];
        Object[] idGiocatori = new Object[planceGiocatori.size()+1];

        int cursoreGiocatoreCorrente = 0;


        //----------- metto la plancia corrispondente al giocatore in prima posizione, in modo da avere una stampa migliore

        Iterator it1 = planceGiocatori.entrySet().iterator();

        while (it1.hasNext()) {

            Map.Entry en1 = (Map.Entry) it1.next();

            if (en1.getKey().equals(tuoId)) {//<<--
                idGiocatori[0] = en1.getKey();

                matriciDaStampare[0] = (String[][]) en1.getValue();
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
                        System.out.print(" Tua Plancia  ");
                    else
                        System.out.print(" Giocatore " + idGiocatori[giocatore] + "    ");
                } else if ((righe == a - 1) && (giocatore == cursoreGiocatoreCorrente)) {
                    System.out.print(" Corrente");

                    if (giocatore == 0)
                        System.out.print("     ");
                    else {
                        for (int x = 0; x < idGiocatori[giocatore].toString().length() + 6; x++)
                            System.out.print(" ");

                    }


                } else if (giocatore != 0) {
                    for (int x = 0; x < idGiocatori[giocatore].toString().length() + 15; x++)
                        System.out.print(" ");
                } else
                    System.out.print("              ");


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

    public void printTurnoRound(int turno, int round) {

        System.out.println("\nTurno Corrente: " + turno + "    Round Corrente: " + round);

    }

    public void printCarteObiettivoPubblico(ArrayList<String> listaCartaObiettivoPubblico) {

        System.out.print("\nCarte Obiettivo Pubblico:   ");
        for (String carta : listaCartaObiettivoPubblico) {
            System.out.print(carta + "   ");

        }
        System.out.println();


    }

    public void printCarteSchema(String[][] schemaFronte1, String[][] schemaRetro1,String[][] schemaFronte2,String[][] schemaRetro2, int a, int b){

        //dichiarazione di tutti i valori che permettono la stampa

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


        String[][][] matriciDaStampare = new String[4+1][a][b];  //numero di plance +1
        Object[] idGiocatori = new Object[4+1];  //numero di plance +1


        matriciDaStampare[0] = schemaFronte1;
        matriciDaStampare[1] = schemaRetro1;
        matriciDaStampare[2] = schemaFronte2;
        matriciDaStampare[3] = schemaRetro2;

        idGiocatori[0] = "schemaFronte1";
        idGiocatori[1] = "schemaRetro1";
        idGiocatori[2] = "schemaFronte2";
        idGiocatori[3] = "schemaRetro2";




        for (int righe = 0; righe < a; righe++) {

            if (righe != 0)
                System.out.println("|");

            for (int carta = 0; carta < 4; carta++) {

                System.out.print("|");


                if (righe == 0)
                    System.out.print(idGiocatori[carta] + "    ");
                else {
                    for (int x = 0; x < idGiocatori[carta].toString().length() + 4; x++)
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

    public void printCartaObiettivoPrivato(HashMap<String,String > ob) {

        System.out.print("\nCarta Obiettivo Privato:   " + ob.get(id));
        System.out.println();


    }

    public void printCarteUtensile(ArrayList<String> listaCartaUtensile) {

        System.out.print("\nCarte Utensile:   ");


        int i = 0;
        for (String carta: listaCartaUtensile){


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

    public void printTavoloDiGioco() {


        System.out.println("\n<===========================================================================================================================================>");

        printTurnoRound(turno, round);

        printTracciatoDelRound(tracciatoDelRound);

        printPlanceGiocatori(planceGiocatori, 4, 5, giocatoreCorrente, id);

        printCarteObiettivoPubblico(carteObiettivoPubblico);

        printCartaObiettivoPrivato(carteObiettivoPrivato);

        printCarteUtensile(carteUtensile);

        printDadiRiserva(dadiRiserva);

        System.out.print("\nDado selezionato:   ");
        printDado(dadoSelezionato);


        System.out.println("\n\n<===========================================================================================================================================>");


    }

    public void printDescrizioneCartaUtensile(ArrayList<String> listaCartaUtensile, int scelta) {


        int i = 0;
        for(String carta: listaCartaUtensile){

            if (scelta == i)
                System.out.println("Descrizione:  " + carta.substring(carta.indexOf('*')+1, carta.length()));

            i++;

        }
        System.out.println();


    }

    public void printPunteggi(HashMap<String, Integer> punteggi){


        //il punteggio del giocatore corrente

        System.out.println("Il tuo punteggio è: "+ punteggi.get(id));

        Iterator it1 = punteggi.entrySet().iterator();

        int i = 0;
        while (it1.hasNext()) {

            Map.Entry e = (Map.Entry) it1.next();

            //il punteggio degli altri giocatori
            if( !( (String) e.getKey() ).equalsIgnoreCase(id) )
                System.out.println("il punteggio di "+ (String) e.getKey() + "é " + e.getValue());

        }
            if(id.equals(vincitore)){
                System.out.println("HAI VINTO !!!!");
            }
            else System.out.println("HAI PERSO :(\nil vincitore è: "+vincitore);




    }

    public void printPiazzamentoScorretto(String giocatoreCorrente) throws RemoteException{



        if(giocatoreCorrente.equalsIgnoreCase(this.id))
            System.out.println("\n\nerrore: ");

        mossaCorretta = false;




    }

    public void printTracciatoDelRound(ArrayList<String> tracciatoDelRound){

        System.out.print("\nTracciato del Round: ");

        for (int i=1; i<=tracciatoDelRound.size(); i++)
            System.out.print(i+" ");

        System.out.print("\n                     ");


        for (String Dado : tracciatoDelRound){
            printDado(Dado);
            System.out.print(" ");
        }
        System.out.println();


    }

    public void updateView(HashMap< String,String[][]> planceGiocatori , ArrayList<String> listaCartaUtensile, String giocatoreCorrente, int turno, int round, ArrayList<String > dadiRiserva, String dadoSelezionato,ArrayList<String> carteObiettivoPubblico,HashMap<String,String> carteObiettivoPrivato, ArrayList<String> tracciatoDelRound,HashSet<Integer> azioniGiocatore) throws RemoteException {


        //per la carta utensile    map <"TPennello Per Eglomise" "descrizione">

        this.startGame=true;
        this.planceGiocatori = planceGiocatori;
        this.carteUtensile = listaCartaUtensile;
        this.giocatoreCorrente= giocatoreCorrente;
        this.turno = turno;
        this.round = round;
        this.dadiRiserva = dadiRiserva;
        this.dadoSelezionato = dadoSelezionato;
        this.carteObiettivoPubblico=carteObiettivoPubblico;
        this.carteObiettivoPrivato=carteObiettivoPrivato;
        this.tracciatoDelRound = tracciatoDelRound;

        if(azioniGiocatore.contains(1)) flagSceltaDado=0;
        else flagSceltaDado=2;

        if(azioniGiocatore.contains(2)) flagSceltaCartaUtensile=0;
        else flagSceltaCartaUtensile=1;


        this.mossaCorretta = true;
        this.updateView = true;




    }

    //faccio altri metodi updateView in base alla situazione

    public void updateViewPlanciaGiocatoreCorrente(String[][] plancia) throws RemoteException{

        //non ho bisogno che mi venga passato il giocatore corrente, in quanto è già salvato nella View

        planceGiocatori.put(giocatoreCorrente, plancia);

        this.mossaCorretta = true;
        this.updateView = true;



    }

    public void updateViewTurno(int turno) throws RemoteException{

        this.turno = turno;

        this.mossaCorretta = true;
        this.updateView = true;






    }

    public void updateViewRound(int round) throws RemoteException{

        this.round = round;

        this.mossaCorretta = true;
        this.updateView = true;





    }

    public void updateViewGiocatoreCorrente(String giocatoreCorrente) throws RemoteException{

        this.giocatoreCorrente = giocatoreCorrente;

        this.mossaCorretta = true;
        this.updateView = true;



    }

    public void updateViewPlanceGiocatori(HashMap< String,String[][]> planceGiocatori) throws RemoteException{

        this.planceGiocatori = planceGiocatori;

        this.mossaCorretta = true;
        this.updateView = true;




    }

    public void updateViewCarteUtensile(ArrayList<String> listaCartaUtensile) throws RemoteException{

        this.carteUtensile = listaCartaUtensile;

        this.mossaCorretta = true;
        this.updateView = true;



    }

    public void updateViewDadiRiserva(ArrayList<String > dadiRiserva) throws RemoteException{

        this.dadiRiserva = dadiRiserva;

        this.mossaCorretta = true;
        this.updateView = true;



    }

    public void updateViewDadoSelezionato(String dadoSelezionato) throws RemoteException{

        this.dadoSelezionato = dadoSelezionato;

        this.mossaCorretta = true;
        this.updateView = true;



    }

    public void updateViewPunteggio(HashMap<String, Integer> punteggi,String vincitore) throws RemoteException {

        this.punteggi = punteggi;
        this.mossaCorretta = true;
        this.vincitore=vincitore;
        setStatus(ViewStatus.CalcoloPunteggio);
        this.updateView=true;



    }

    @Override
    public void updateViewTool6Bool(boolean piazzabile) throws RemoteException {
        tool6piazzabile=piazzabile;
        if(!piazzabile) updateView=true;
    }

    @Override
    public void updateViewTool6Die(String Dado) throws RemoteException {
        tool6Dado=Dado;
        updateView=true;
    }
    @Override
    public void updateUsername(boolean usernameOK){
        this.usernameOK=usernameOK;
        updateView=true;
    }

    //=========================



    public void setGame() {


        //String[][] CartaSchema = {{"rosso", "2", "verde", "blu", "giallo"}, {"n", "n", "n", "n", "n"}, {"n", "n", "n", "n", "n"}, {"n", "n", "n", "n", "n"}};




        String[][] Plancia1 = {{"2rosso", "4verde", "verde", "blu", "giallo"}, {"n", "n", "5giallo", "n", "n"}, {"n", "n", "verde", "n", "n"}, {"n", "n", "n", "n", "n"}};

        String[][] Plancia2 = {{"rosso", "2", "verde", "blu", "3giallo"}, {"n", "n", "n", "n", "n"}, {"n", "n", "n", "n", "n"}, {"n", "n", "n", "n", "n"}};

        String[][] Plancia3 = {{"6rosso", "2", "verde", "blu", "giallo"}, {"n", "n", "n", "n", "n"}, {"n", "n", "n", "n", "n"}, {"n", "n", "n", "n", "n"}};

        String[][] Plancia4 = {{"rosso", "2", "verde", "4blu", "giallo"}, {"n", "n", "n", "n", "n"}, {"n", "n", "n", "n", "n"}, {"n", "n", "n", "n", "n"}};






        planceGiocatori.put("1hhh", Plancia1);
        planceGiocatori.put("2b", Plancia2);
        planceGiocatori.put("marcello", Plancia3);
        planceGiocatori.put("pippo", Plancia3);

  /*
        carteObiettivoPubblico.add(FactoryCartaObiettivoPubblico.getCartaObiettivoPubblico(1));
        carteObiettivoPubblico.add(FactoryCartaObiettivoPubblico.getCartaObiettivoPubblico(2));
        carteObiettivoPubblico.add(FactoryCartaObiettivoPubblico.getCartaObiettivoPubblico(3));*/


        //carteUtensile.put("TPennello per Eglomise", "chi usa questa carta vince il gioco");
        //carteUtensile.put("FAlesatore per lamina di rame", "se usi questa carte, hai perso ");
        //carteUtensile.put("TMartelletto", "come dice il nome, è un martelletto. ");


        dadiRiserva.add("2verde");
        dadiRiserva.add("1rosso");
        dadiRiserva.add("6giallo");
        dadiRiserva.add("4viola");
        dadiRiserva.add("3blu");
        dadiRiserva.add("5verde");





        giocatoreCorrente = "pippo";
        id = "pippo";


        dadoSelezionato = "n";


        status = ViewStatus.SvolgimentoPartita;

        turno = 1;
        round = 4;

    }   //TODO RIMUOVERE non serve nel vero programma
    public void timer1(){

        String[][] PlanciaDiProva = {{"2rosso", "2rosso", "2rosso", "2rosso", "2rosso"}, {"n", "n", "n", "n", "n"}, {"n", "n", "n", "n", "n"}, {"n", "n", "n", "n", "n"}};




        //=====timer per simulare il delay dato dal server. verrà chiamato un metodo che permetterà di rendere true "updateView"

        /*Timer t = new Timer();

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    //updateView(planceGiocatori,carteUtensile,giocatoreCorrente,turno,round,dadiRiserva,dadoSelezionato);
                    updateViewPlanciaGiocatoreCorrente(PlanciaDiProva);
                }
                catch (RemoteException e){

                }

            }
        }, 2000);
        */

    }   //TODO RIMUOVERE non serve nel vero programma
    public void timer2(){

        //=====timer per simulare il delay dato dal server. verrà chiamato un metodo che permetterà di rendere true "updateView"

        Timer t = new Timer();

        /* t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    updateView(planceGiocatori,carteUtensile,"pippo",turno,round,dadiRiserva,dadoSelezionato);
                }
                catch (RemoteException e){

                }
            }
        }, 5000);

        da levare*/

        //=====

    }   //TODO RIMUOVERE non serve nel vero programma
    public void scorriCartaUtensile(int cmd){
        //=== da rimuovere, solo per prova... per testare le carte utensile
        int indiceScorrimento=0;
        boolean trovato = false;

        for(String carta: carteUtensile){

            if (indiceScorrimento==cmd) {

                cartaInUso = carta.substring(1, carta.indexOf('*'));
            }
            indiceScorrimento++;

        }


        //===   ----------------------------------- ===
    }   //TODO RIMUOVERE non serve nel vero programma





}