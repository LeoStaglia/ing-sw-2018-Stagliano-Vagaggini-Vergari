package ingSw2018StaglianoVagagginiVergari.client;

import Eccezioni.FullGameException;
import Eccezioni.MossaIllegaleException;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;
import ingSw2018StaglianoVagagginiVergari.server.model.FactoryCartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.TaglierinaManuale;

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

    private Printer stampante = new Printer();


    private boolean simulazione = false; //TODO questo attributo permette di switchare velocemante tra una partita già impostata ed una che richiede parametri dal model


    private HashMap<String, Integer> punteggi;

    private boolean mossaCorretta = false;

    private CurrentPlayerTurn t;


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
    private HashMap<String, String> carteObiettivoPrivato = new HashMap<>();
    //private boolean updateView;
    private boolean fronteScelto;
    private boolean carta1;
    private boolean tool6piazzabile;
    private boolean usernameOK = false;
    private String tool6Dado;
    private boolean lathekinPhase2 = false;
    private boolean diluentePastaSaldaPhase2 = false;
    private String tool11Dado;

    private boolean piazzato=false;

    private int numeroDadiTool12 = 0;
    private int tRound = -1;
    private String token;
    private int login = 0;

    //
    private boolean startGame = false;
    //

    //==================

    private ArrayList<String> carteUtensile = new ArrayList<>();

    private HashMap<String, String[][]> planceGiocatori = new HashMap<String, String[][]>();

    private String giocatoreCorrente;

    private String id;

    private ArrayList<String> carteObiettivoPubblico = new ArrayList<>();

    private ArrayList<String> dadiRiserva = new ArrayList<>();

    private String dadoSelezionato;

    private Integer numeroDadoSelezionato;

    private String cartaInUso = "NESSUNA";

    private boolean updateView = false;

    private ViewStatus status;

    private Scanner inputCommand = new Scanner(System.in);

    private IntegerNonBlockingScanner input = new IntegerNonBlockingScanner();

    private boolean passaturno = false;

    private int flagSceltaDado = 0;  // 0 -> il dado non è ancora stato selezionato   1 -> il dado selezioanto viene posizionato

    private int flagSceltaCartaUtensile = 0;  // 0 -> non è stato usato l'effetto di nessuna carta  1 -> ho già usato l'effetto di una carta

    private int turno;

    private int round;

    private ArrayList<String> tracciatoDelRound;

    private HashSet<Integer> azioniGiocatore;

    private String vincitore;

    private int cmd;

    private String username;

    //==================t


    public View(RemoteController controller) throws RemoteException {
        this.controller = controller;
        setStatus(ViewStatus.Preparazione);
        command = new Scanner(System.in);
        updateView = false;
    }

    public void run() throws IOException, InterruptedException{
        cmd = 0;
        username = null;
        boolean carta1 = false;
        while (cmd != 1 && cmd != 2) {
            System.out.println("(1) Per giocare (2) per uscire");
            try {
                cmd = input.call();
            } catch (InputMismatchException e) {
                cmd = -1;
                inputCommand.nextLine();// flush the buffer
            }
        }
        if (cmd == 2) {
            System.exit(0);
        } else if (cmd == 1) {
            while (login == 0) {
                cmd=-1;
                while(cmd!=1 && cmd!=2) {
                    System.out.println("(1) Per partecipare a una nuova partita, (2) per login.");
                    try {
                        cmd = input.call();
                    } catch (InputMismatchException e) {
                        cmd = -1;
                        inputCommand.nextLine();// flush the buffer
                    }
                }
                if (cmd == 1) {
                    login = 1;
                    while (!usernameOK) {
                        System.out.println("Inserisci un nuovo username");
                        username = command.next();
                        try {
                            controller.partecipaPartita(this, username);
                            while (!updateView) {
                                System.out.println("");
                            }
                            updateView = false;
                        } catch (FullGameException e) {
                            System.out.println(e.getMessage());
                            System.exit(0);
                        }
                    }
                } else if (cmd == 2) {
                    int tentativi = 0;
                    while (login == 0 && tentativi < 3) {
                        System.out.println("Login (3 tentativi):");
                        System.out.println("Username:");
                        username = command.next();
                        String token;
                        System.out.println("Token:");
                        token = command.next();
                        login = controller.login(this, username, token);
                        tentativi++;
                    }
                    if (login == 1) {
                        id = username;
                        while (!updateView) {
                            System.out.print("");
                        }
                        updateView = false;
                        setStatus(ViewStatus.SvolgimentoPartita);

                    } else if (login == 2) {
                        id = username;
                        while (!updateView) {
                            System.out.print("");
                        }
                        updateView = false;

                    }

                }
            }



            if (getStatus() == ViewStatus.Preparazione)
                preparazionePartita();

            if (getStatus() == ViewStatus.SelezioneSchema)
                selezioneSchema();

            while (!startGame) {
                System.out.print("");
            }

            while (getStatus() == ViewStatus.SvolgimentoPartita)
                svolgimentoPartita();

            if (getStatus() == ViewStatus.CalcoloPunteggio)
                calcoloPunteggi();


            }

        }



        @Override
        public synchronized void notifyUser (String id, String token, String[][]schemaFronte1, String[][]
        schemaRetro1, String[][]schemaFronte2, String[][]schemaRetro2, String obiettivoPrivato) throws RemoteException {
            this.id = id;
            this.setStatus(ViewStatus.SelezioneSchema);
            this.schemaFronte1 = schemaFronte1;
            this.schemaRetro1 = schemaRetro1;
            this.schemaFronte2 = schemaFronte2;
            this.schemaRetro2 = schemaRetro2;
            this.obiettivoPrivato = obiettivoPrivato;
            this.token = token;
            updateView = true;
        }

    public synchronized void setStatus(ViewStatus status) {
        this.status = status;
    }

    public synchronized ViewStatus getStatus() {
        return this.status;
    }

    @Override
    public synchronized void notifyScheme(boolean carta1, boolean fronteScelto) {
        updateView = true;
        this.fronteScelto = fronteScelto;
        this.carta1 = carta1;
        setStatus(ViewStatus.SvolgimentoPartita);
    }


    public void setPiazzamentoScorretto(String giocatoreCorrente) throws RemoteException {


        if (giocatoreCorrente.equalsIgnoreCase(this.id))
          //  System.out.println("\n\nerrore: ");
            mossaCorretta = false;
    }



    public void updateView(HashMap<String, String[][]> planceGiocatori, ArrayList<String> listaCartaUtensile, String giocatoreCorrente, int turno, int round, ArrayList<String> dadiRiserva, String dadoSelezionato, ArrayList<String> carteObiettivoPubblico, HashMap<String, String> carteObiettivoPrivato, ArrayList<String> tracciatoDelRound, HashSet<Integer> azioniGiocatore) throws RemoteException {


        //per la carta utensile    map <"TPennello Per Eglomise" "descrizione">

        this.startGame = true;
        this.planceGiocatori = planceGiocatori;
        this.carteUtensile = listaCartaUtensile;
        this.giocatoreCorrente = giocatoreCorrente;
        this.turno = turno;
        this.round = round;
        this.dadiRiserva = dadiRiserva;
        this.dadoSelezionato = dadoSelezionato;
        this.carteObiettivoPubblico = carteObiettivoPubblico;
        this.carteObiettivoPrivato = carteObiettivoPrivato;
        this.tracciatoDelRound = tracciatoDelRound;

        if (azioniGiocatore.contains(1) || azioniGiocatore.contains(4)) flagSceltaDado = 0;
        else flagSceltaDado = 2;

        if (azioniGiocatore.contains(2)) flagSceltaCartaUtensile = 0;
        else flagSceltaCartaUtensile = 1;


        this.mossaCorretta = true;
       /* if (cartaInUso.equals("Lathekin") && !lathekinPhase2) {
            lathekinPhase2 = true;
        } else if (cartaInUso.equals("Lathekin") && lathekinPhase2) {
            lathekinPhase2 = false;
        }*/
       /* if (cartaInUso.equals("Taglierina Manuale") && numeroDadiTool12 > 0) {
            numeroDadiTool12--;
        }
        else{numeroDadiTool12=0;}
        */
        this.updateView = true;


    }

    //faccio altri metodi updateView in base alla situazione

    public void updateViewPlanciaGiocatoreCorrente(String[][] plancia) throws RemoteException {

        //non ho bisogno che mi venga passato il giocatore corrente, in quanto è già salvato nella View

        planceGiocatori.put(giocatoreCorrente, plancia);

        this.mossaCorretta = true;
        this.updateView = true;


    }

    public void updateViewTurno(int turno) throws RemoteException {

        this.turno = turno;

        this.mossaCorretta = true;
        this.updateView = true;


    }

    public void updateViewRound(int round) throws RemoteException {

        this.round = round;

        this.mossaCorretta = true;
        this.updateView = true;


    }

    public void updateViewGiocatoreCorrente(String giocatoreCorrente) throws RemoteException {

        this.giocatoreCorrente = giocatoreCorrente;

        this.mossaCorretta = true;
        this.updateView = true;


    }

    public void updateViewPlanceGiocatori(HashMap<String, String[][]> planceGiocatori) throws RemoteException {

        this.planceGiocatori = planceGiocatori;

        this.mossaCorretta = true;
        this.updateView = true;


    }

    public void updateViewCarteUtensile(ArrayList<String> listaCartaUtensile) throws RemoteException {

        this.carteUtensile = listaCartaUtensile;

        this.mossaCorretta = true;
        this.updateView = true;


    }

    public void updateViewDadiRiserva(ArrayList<String> dadiRiserva) throws RemoteException {

        this.dadiRiserva = dadiRiserva;

        this.mossaCorretta = true;
        this.updateView = true;


    }

    public void updateViewDadoSelezionato(String dadoSelezionato) throws RemoteException {

        this.dadoSelezionato = dadoSelezionato;

        this.mossaCorretta = true;
        this.updateView = true;


    }

    public synchronized void updateViewPunteggio(HashMap<String, Integer> punteggi, String vincitore) throws RemoteException {

        this.punteggi = punteggi;
        this.mossaCorretta = true;
        this.vincitore = vincitore;
        setStatus(ViewStatus.CalcoloPunteggio);
        this.updateView = true;


    }

    @Override
    public void updateViewTool6Bool(boolean piazzabile) throws RemoteException {
        tool6piazzabile = piazzabile;
        if (!piazzabile) updateView = true;
    }

    @Override
    public void updateViewTool6Die(String Dado) throws RemoteException {
        tool6Dado = Dado;
        updateView = true;
    }

    @Override
    public synchronized void updateUsername(boolean usernameOK) {
        this.usernameOK = usernameOK;
        updateView = true;
    }




    @Override
    public void updateViewTool11 (String Dado) throws RemoteException {
        this.diluentePastaSaldaPhase2 = true;
        updateView = true;
        tool11Dado = Dado;
    }

    public void updateViewTool4 (boolean fase) throws RemoteException {
        lathekinPhase2=fase;
    }



    public void updateViewTool12 (boolean fase) throws RemoteException {
        if(fase==true){
            numeroDadiTool12--;
        }
        else{
            numeroDadiTool12=0;
            tRound=-1;

        }
    }

    @Override
    public void notifyTurnTimer() throws RemoteException {
        t.interrupt();
        updateView=true;
    }


    public void updateViewTool6piazzato (boolean piazzato) throws RemoteException {
        this.piazzato=piazzato;
    }

    public void updateMessage (String message) throws RemoteException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_YELLOW = "\u001B[33m";
        System.out.println(ANSI_YELLOW+message+ANSI_RESET);
        Pausa(4);
    }


    private void Pausa(int secondi){
        long start = System.currentTimeMillis();
        long end = start + secondi*1000; // ms/sec
        while (System.currentTimeMillis() < end) {
            System.out.print("");
        }
        return;
    }

    //=========================

    public void scorriCartaUtensile(int cmd) {
        //=== da rimuovere, solo per prova... per testare le carte utensile
        int indiceScorrimento = 0;
        boolean trovato = false;

        for (String carta : carteUtensile) {

            if (indiceScorrimento == cmd) {

                cartaInUso = carta.substring(1, carta.indexOf('*'));
            }
            indiceScorrimento++;

        }


        //===   ----------------------------------- ===
    }

    private int cercaUtensile(String nome) {
        int i = 0;
        for (String carta : carteUtensile) {
            if (carta.substring(1, carta.indexOf('*')).equals(nome)) {
                break;
            }
            i++;
        }
        return i;
    }

    @Override
    public void notifyExit() throws RemoteException {
        exit();
    }

    @Override
    public void ping() throws RemoteException {

    }

    private void exit() {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                {
                    System.exit(0);
                }
            }
        }, 500);
    }

    @Override
    public synchronized void notifyUserExit(String username) throws RemoteException {
        if (this.getStatus() == ViewStatus.SvolgimentoPartita) {
            System.out.println("============================================================================");
            System.out.println(username + " è uscito dalla partita.");
            System.out.println("============================================================================");
        }

    }


    public void svolgimentoPartita() throws IOException, InterruptedException {
        if (lathekinPhase2) {
            cartaInUso = "Lathekin";
            cmd = 2;
            flagSceltaCartaUtensile = 0;
        } else if (numeroDadiTool12 > 0) {
            cartaInUso = "Taglierina Manuale";
            cmd = 2;
            flagSceltaCartaUtensile = 0;
        } else if (diluentePastaSaldaPhase2) {
            cartaInUso = "Diluente Per Pasta Salda";
            cmd = 2;
            flagSceltaCartaUtensile = 0;
        }


        //anzichè fare una pulizia dello schermo, scorro molto in giù   --- NB: dovrà essere sostituito con "clearscreen"

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");


        stampante.printTavoloDiGioco(turno, round, tracciatoDelRound, planceGiocatori, 4, 5, giocatoreCorrente, id, carteObiettivoPubblico, carteObiettivoPrivato, carteUtensile, dadiRiserva, dadoSelezionato);


        updateView = false;

        passaturno = false;


        if (id.equalsIgnoreCase(giocatoreCorrente) && passaturno == false) {//TODO RIVEDERE non serve il parametro passaturno

            t= new CurrentPlayerTurn();
            t.start();


        } else{

            synchronized (this) {
                System.out.println("\nNON SEI IL GIOCATORE CORRENTE!\n");

            }
        }


        // in attesa di un aggiornamento alla view

        while (updateView == false) {
            System.out.print("");
        }


    }

    public void selezioneSchema() throws IOException{

        //qui avviene la stampa delle 4 carte schema, una delle quali verrà scelta dall'utente

        // prototipo della funzione:  private void stampaSchema(boolean carta1, boolean fronte);
        System.out.println("Benvenuto " + id + ", il token per accedere alla partita è: " + token);

        System.out.println();
        stampante.printCarteSchema(schemaFronte1, schemaRetro1, schemaFronte2, schemaRetro2, 4, 5);
        System.out.println();


        cmd = 0;
        while (cmd != 1 && cmd != 2) {
            System.out.println("(1) per scegliere la prima carta (2) per scegliere la seconda carta");
            try {
                cmd = inputCommand.nextInt();
            } catch (InputMismatchException e) {
                cmd = -1;
                inputCommand.nextLine();// flush the buffer
            }
        }
        if (cmd == 1) {
            carta1 = true;
        } else {
            carta1 = false;
        }
        cmd = 0;
        while (cmd != 1 && cmd != 2) {
            System.out.println("(1) per scegliere il fronte (2) per scegliere il retro");
            try {
                cmd = inputCommand.nextInt();
            } catch (InputMismatchException e) {
                cmd = -1;
                inputCommand.nextLine();// flush the buffer
            }
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

    public void preparazionePartita() throws IOException, InterruptedException {
        System.out.println("Preparazione partita... Attendi. (0) per uscire");
        while (status == ViewStatus.Preparazione) {
            if (System.in.available() > 0) {
                while(cmd!=0) {
                    try {
                        cmd = inputCommand.nextInt();
                    } catch (InputMismatchException e) {
                        cmd = -1;
                        inputCommand.nextLine();// flush the buffer
                    }
                    if (cmd== 0) {
                        controller.abbandonaPartita(this, username);
                        System.exit(0);
                    }
                }
            }
        }
        System.out.println(status);
        while (!updateView) {
            wait();
        }
        updateView = false;
    }

    public void calcoloPunteggi(){


        System.out.println("CALCOLO DEL PUNTEGGIO FINALE...");

        System.out.println("\n\n\n\n");


        stampante.printPunteggi(punteggi, id, vincitore);


    }

    private class CurrentPlayerTurn extends Thread{
        @Override
        public void run() {
            try {
                synchronized (View.this) {

                    System.out.println("\nSEI IL GIOCATORE CORRENTE!\n");


                    //stampe differite in base a quali comandi abbia già dato l'utente in input (se l'utente sceglie inizialmente di selezionare un dado, sarà settato il flagSceltaDado , cosi che si stampi una scelta di input diversa)

                    if (!lathekinPhase2 && numeroDadiTool12 == 0 && !diluentePastaSaldaPhase2) {
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


                        try {
                            cmd = input.call();
                        } catch (InputMismatchException e) {
                            cmd = -1;
                            inputCommand.nextLine();// flush the buffer
                        }

                        while (cmd != 1 && cmd != 2 && cmd != 3 && cmd != 4) {
                            System.out.println("SCELTA NON VALIDA! Reinserisci scelta:");
                            try {
                                cmd = input.call();
                            } catch (InputMismatchException e) {
                                cmd = -1;
                                inputCommand.nextLine();//flush the buffer
                            }
                        }
                    }
                }

                if (cmd == 1 && flagSceltaDado == 0) {
                    synchronized (View.this) {
                        cmd = -3;

                        while (cmd < 0 || cmd >= dadiRiserva.size()) {
                            System.out.println("inserisci il numero del dado della riserva che vuoi scegliere");//dovrei  verificare che la scelta sia corretta
                            try {
                                cmd = input.call(); //dentro a cmd è contenuto il dado selezionato
                                if (cmd < 0 || cmd >= dadiRiserva.size())
                                    System.out.print("POSIZIONE NON VALIDA! ");
                            } catch (InputMismatchException e) {
                                cmd = -3;
                                inputCommand.nextLine(); // flush the buffer
                            }

                        }
                        numeroDadoSelezionato = cmd;  // dato che la selezione del dado avviene in un istante diverso da quello dell'effettivo utilizzo, devo tenere traccia dellla posizione del dado selezionato all'interno della riserva;


                        if (simulazione)
                            dadoSelezionato = dadiRiserva.get(cmd); //TODO RIMUOVERE solo per prova... lo inserisco solo per far vedere che effettivamente l'aggiornamento funziona

                    }
                    mossaCorretta = false;
                    synchronized (View.this) {
                        while (mossaCorretta == false) {


                            System.out.println("inserisci le coordinate in cui vuoi posizionare il dado: ");

                            System.out.print("i ( 0 <= x <= 3):");
                            int i = -1;
                            try {
                                i = input.call();
                            } catch (InputMismatchException e) {
                                i = -1;
                                inputCommand.nextLine(); // flush the buffer
                            }
                            while (i < 0 || i > 3) {
                                System.out.print("inserisci delle coordinate di riga valide i ( 0 <= x <= 3):");
                                try {
                                    i = input.call();
                                } catch (InputMismatchException e) {
                                    i = -1;
                                    inputCommand.nextLine(); // flush the buffer
                                }

                            }

                            System.out.print("j ( 0 <= y <= 4):");
                            int j = -1;
                            try {
                                j = input.call();
                            } catch (InputMismatchException e) {
                                j = -1;
                                inputCommand.nextLine();
                            }
                            while (j < 0 || j > 4) {
                                System.out.print("inserisci delle coordinate di riga valide j ( 0 <= y <= 4):");
                                try {
                                    j = input.call();
                                } catch (InputMismatchException e) {
                                    j = -1;
                                    inputCommand.nextLine();
                                }
                            }


                            //chiamo il metodo del controller che mi permette di passare i parametri per la mossa scelta.
                            // try {
                            parametri.add(0, 1); // ho scelto di posizionare il dado
                            parametri.add(1, numeroDadoSelezionato); // passo la posizione del dado selezionato all'interno della riserva
                            parametri.add(2, i);
                            parametri.add(3, j);
                            try {
                                controller.svolgimentoPartita(View.this, parametri);  //RIVEDERE il fatto che debba essere passata anche la vista deve essere ripensato
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            // System.out.println("\nverifico convalida mossa\n");
                            parametri.clear(); //RIVEDERE non è detto che sia necessario new dato che viene fatto solo per precauzione. potrebbe essere sufficiente lasciarlo nelle condizioni attuali senza reinizializzare, in modo da sapere quale è l'ultima chiamata che è stata fatta al model.

                                   /* } catch (MossaIllegaleException e) {
                                        System.out.print("mossa non consentita");  //TODO RIVEDERE da rivedere se funziona
                                    }*/


                        }
                    }


                    //  flagSceltaDado = 2;

                } else if (cmd == 2 && flagSceltaCartaUtensile == 0) {
                    synchronized (View.this) {
                        if (!lathekinPhase2 && numeroDadiTool12 == 0 && !diluentePastaSaldaPhase2) {
                            cmd = -3;
                            while (cmd < 0 || cmd > 2) {
                                System.out.println("inserisci il numero della carta che vuoi scegliere");
                                try {
                                    cmd = input.call(); //dentro a cmd è contenuta la carta selezionata
                                } catch (InputMismatchException e) {
                                    cmd = -3;
                                    inputCommand.nextLine();
                                }

                            }
                        }
                        //chiamo il metodo del controller che mi permette di passare i parametri per la mossa scelta.
                        parametri = new ArrayList<>();
                        // try {
                        parametri.add(0, 2); // ho scelto di selezionare la carta utensile
                        if (!lathekinPhase2 || numeroDadiTool12 > 0 || !diluentePastaSaldaPhase2) {
                            parametri.add(1, cmd);   // questo è il numero della carta utensile che ha selezionato
                        } else if (numeroDadiTool12 > 0) {
                            parametri.add(1, cercaUtensile("Taglierina Manuale"));
                        } else if (lathekinPhase2) {
                            parametri.add(1, cercaUtensile("Lathekin"));
                        } else if (!diluentePastaSaldaPhase2) {
                            parametri.add(1, cmd);
                        }
                        try {
                            controller.svolgimentoPartita(View.this, parametri);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        parametri.clear();
                               /* } catch (MossaIllegaleException e) {
                                    System.out.print("carta non consentita");  //TODO RIVEDERE da rivedere se funziona
                                }*/
                    }


                    if (!lathekinPhase2 || numeroDadiTool12 == 0 || !diluentePastaSaldaPhase2) {
                        scorriCartaUtensile(cmd);//TODO RIMUOVERE serve solo per avere la simulazione di una partita
                    }


                    //====================  QUI VERRANNO FATTE RICHIESTE DIVERSE IN BASE ALLA CARTA UTENSILE

                    synchronized (View.this) {
                        System.out.println(" -- ... CARTA UTENSILE ... --");
                        parametri.clear();
                        if (cartaInUso.equalsIgnoreCase("Pinza Sgrossatrice")) {

                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("Seleziona un dado dalla riserva, inserisci la posizione");
                                    while (com < 0 || com >= dadiRiserva.size()) {
                                        com = input.call();
                                        if (com < 0 || com >= dadiRiserva.size()) {
                                            System.out.println(" posizione non valida reinserisci il numero del dado che vuoi scegliere");
                                        }
                                    }
                                    parametri.add(0, com);
                                    break;
                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }

                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("Inserisci 1 per incrementare, -1 per decrementare");
                                    while (com != -1 && com != 1) {
                                        com = input.call();
                                        if (com != -1 && com != 1) {
                                            System.out.println("OPERAZIONE ERRATA! inserire 1 per incrementare -1 per decrementare");
                                        }
                                    }
                                    parametri.add(1, com);
                                    break;
                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }

                            try {
                                controller.usaCartaUtensile(View.this, parametri);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        } else if ((cartaInUso.equalsIgnoreCase("Pennello Per Eglomise")) || (cartaInUso.equalsIgnoreCase("Alesatore per lamina di rame")) || (cartaInUso.equalsIgnoreCase("Lathekin"))) {
                            parametri.clear();
                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("Inserisci la riga del dado che vuoi spostare:(0 <= x <= 3)");
                                    while (com < 0 || com > 3) {
                                        com = input.call();
                                        if (com < 0 || com > 3) {
                                            System.out.println("INSERIMENTO RIGA ERRATO!\n" +
                                                    "Inserisci la riga del dado che vuoi spostare:(0 <= x <= 3)");
                                        }
                                    }
                                    parametri.add(0, com);
                                    break;

                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }
                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("Inserisci la colonna del dado che vuoi spostare:(0 <= y <= 4)");
                                    while (com < 0 || com > 4) {
                                        com = input.call();
                                        if (com < 0 || com > 4) {
                                            System.out.println("INSERIMENTO COLONNA ERRATO!\n" +
                                                    "Inserisci la colonna del dado che vuoi spostare:(0 <= y <= 4)");
                                        }
                                    }
                                    parametri.add(1, com);
                                    break;

                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }

                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("Inserisci la riga in cui vuoi spostare il dado:(0 <= x <= 3)");
                                    while (com < 0 || com > 3) {
                                        com = input.call();
                                        if (com < 0 || com > 3) {
                                            System.out.println("INSERIMENTO RIGA ERRATO!\n" +
                                                    "Inserisci la riga in cui vuoi spostare il dado:(0 <= x <= 3)");
                                        }
                                    }
                                    parametri.add(2, com);
                                    break;

                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }

                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("Inserisci la colonna in cui vuoi spostare il dado:(0 <= y <= 4)");
                                    while (com < 0 || com > 4) {
                                        com = input.call();
                                        if (com < 0 || com > 4) {
                                            System.out.println("INSERIMENTO COLONNA ERRATO!\n" +
                                                    "Inserisci la colonna in cui vuoi spostare il dado:(0 <= y <= 4)");
                                        }
                                    }
                                    parametri.add(3, com);
                                    break;

                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }
                            try {
                                controller.usaCartaUtensile(View.this, parametri);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        /* }else if (cartaInUso.equalsIgnoreCase("Alesatore per lamina di rame")) {
                            parametri.clear();
                            System.out.println("Inserisci la riga del dado che vuoi spostare:(0 <= x <= 3)");
                            parametri.add(0,input.call());
                            System.out.println("Inserisci la colonna del dado che vuoi spostare:(0 <= y <= 4)");
                            parametri.add(1,input.call());
                            System.out.println("Inserisci la riga in cui vuoi spostare il dado:(0 <= x <= 3)");
                            parametri.add(2,input.call());
                            System.out.println("Inserisci la colonna in cui vuoi spostare il dado:(0 <= y <= 3)");
                            parametri.add(3,input.call());
                            controller.usaCartaUtensile(this,parametri);
                            */

                        /*}else if (cartaInUso.equalsIgnoreCase("Lathekin")) {
                            parametri.clear();
                            System.out.println("Inserisci la riga del dado che vuoi spostare:(0 <= x <= 3)");
                            parametri.add(0,input.call());
                            System.out.println("Inserisci la colonna del dado che vuoi spostare:(0 <= y <= 4)");
                            parametri.add(1,input.call());
                            System.out.println("Inserisci la riga in cui vuoi spostare il dado:(0 <= x <= 3)");
                            parametri.add(2,input.call());
                            System.out.println("Inserisci la colonna in cui vuoi spostare il dado:(0 <= y <= 4)");
                            parametri.add(3,input.call());
                            controller.usaCartaUtensile(this,parametri);
                            System.out.println("SECONDA CHIAMATA CONTROLLER");
                            */


                        } else if (cartaInUso.equalsIgnoreCase("Taglierina Circolare")) {
                            parametri.clear();
                            if (!tracciatoDelRound.isEmpty()) {
                                while (true) {
                                    try {
                                        int com = -3;
                                        System.out.println("inserisci il numero del dado della riserva che vuoi scambiare con uno sul Tracciato del Round");
                                        while (com < 0 || com >= dadiRiserva.size()) {
                                            com = input.call();
                                            if (com < 0 || com >= dadiRiserva.size()) {
                                                System.out.println("INSERIMENTO POSIZIONE DADO RISERVA DA SCAMBIARE ERRATO!\n" +
                                                        "inserisci il numero del dado della riserva che vuoi scambiare con uno sul Tracciato del Round");
                                            }
                                        }
                                        parametri.add(0, com);
                                        break;

                                    } catch (InputMismatchException e) {
                                        inputCommand.nextLine();
                                    }
                                }
                                while (true) {
                                    try {
                                        int com = -3;
                                        System.out.println("inserisci il numero del dado del Tracciato del Round che vuoi scambiare con quello selezionato dalla riserva");
                                        while (com < 1 || com > tracciatoDelRound.size()) {
                                            com = input.call();
                                            if (com < 1 || com > tracciatoDelRound.size()) {
                                                System.out.println("INDICE TRACCIATO DEL ROUND ERRATO!!\n" +
                                                        "inserisci il numero del dado del Tracciato del Round che vuoi scambiare con quello selezionato dalla riserva");
                                            }
                                        }
                                        parametri.add(1, com);
                                        break;

                                    } catch (InputMismatchException e) {
                                        inputCommand.nextLine();
                                    }
                                }
                                try {
                                    controller.usaCartaUtensile(View.this, parametri);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Carta non utilizzabile, tracciato vuoto.");
                                Pausa(4);
                            }

                        } else if (cartaInUso.equalsIgnoreCase("Pennello Per Pasta Salda")) {
                            parametri.clear();
                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("Inserisci il numero del dado della riserva che vuoi lanciare nuovamente");
                                    while (com < 0 || com >= dadiRiserva.size()) {
                                        com = input.call();
                                        if (com < 0 || com >= dadiRiserva.size()) {
                                            System.out.println("INSERIMENTO POSIZIONE DADO RISERVA DA RILANCIARE ERRATO!!\n" +
                                                    "Inserisci il numero del dado della riserva che vuoi lanciare nuovamente");
                                        }
                                    }
                                    parametri.add(0, com);
                                    break;

                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }
                            try {
                                controller.usaCartaUtensile(View.this, parametri);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            while (!updateView) {
                                System.out.print("");
                            }
                            updateView = false;
                            if (tool6piazzabile) {
                                while (!piazzato) {
                                    parametri.clear();
                                    stampante.printDado(tool6Dado);
                                    System.out.println("");
                                    while (true) {
                                        try {
                                            int com = -3;
                                            System.out.println("Inserisci la riga in cui vuoi spostare il dado:(0 <= x <= 3)");
                                            while (com < 0 || com > 3) {
                                                com = input.call();
                                                if (com < 0 || com > 3) {
                                                    System.out.println("INSERIMENTO RIGA ERRATO!\n" +
                                                            "Inserisci la riga in cui vuoi spostare il dado:(0 <= x <= 3)");
                                                }
                                            }
                                            parametri.add(0, com);
                                            break;

                                        } catch (InputMismatchException e) {
                                            inputCommand.nextLine();
                                        }
                                    }
                                    while (true) {
                                        try {
                                            int com = -3;
                                            System.out.println("Inserisci la colonna in cui vuoi spostare il dado:(0 <= y <= 4)");
                                            while (com < 0 || com > 4) {
                                                com = input.call();
                                                if (com < 0 || com > 4) {
                                                    System.out.println("INSERIMENTO COLONNA ERRATO!\n" +
                                                            "Inserisci la colonna in cui vuoi spostare il dado:(0 <= y <= 4)");
                                                }
                                            }
                                            parametri.add(1, com);
                                            break;

                                        } catch (InputMismatchException e) {
                                            inputCommand.nextLine();
                                        }
                                    }
                                    try {
                                        controller.usaCartaUtensile(View.this, parametri);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                                piazzato = false;

                            } else {
                                updateView = true;

                            }

                        } else if (cartaInUso.equalsIgnoreCase("Martelletto")) {
                            try {
                                controller.usaCartaUtensile(View.this, parametri);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        } else if (cartaInUso.equalsIgnoreCase("Tenaglia A Rotelle")) {
                            parametri.clear();
                            try {
                                controller.usaCartaUtensile(View.this, parametri);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        } else if (cartaInUso.equalsIgnoreCase("Riga In Sughero")) {
                            parametri.clear();
                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("inserisci posizione dado in riserva da posizionare");
                                    while (com < 0 || com >= dadiRiserva.size()) {
                                        com = input.call();
                                        if (com < 0 || com >= dadiRiserva.size()) {
                                            System.out.println("INSERIMENTO POSIZIONE DADO IN RISERVA ERRATA!\n" +
                                                    "inserisci posizione dado in riserva da posizionare");
                                        }
                                    }
                                    parametri.add(0, com);
                                    break;

                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }


                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("Inserisci la riga in cui vuoi posizionare il dado:(0 <= x <= 3)");
                                    while (com < 0 || com > 3) {
                                        com = input.call();
                                        if (com < 0 || com > 3) {
                                            System.out.println("INSERIMENTO RIGA ERRATO!\n" +
                                                    "Inserisci la riga in cui vuoi posizionare il dado:(0 <= x <= 3)");
                                        }
                                    }
                                    parametri.add(1, com);
                                    break;

                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }
                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("Inserisci la colonna in cui vuoi piazzare il dado:(0 <= y <= 4)");
                                    while (com < 0 || com > 4) {
                                        com = input.call();
                                        if (com < 0 || com > 4) {
                                            System.out.println("INSERIMENTO COLONNA ERRATO!\n" +
                                                    "Inserisci la colonna in cui vuoi piazzare il dado:(0 <= y <= 4)");
                                        }
                                    }
                                    parametri.add(2, com);
                                    break;

                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }
                            try {
                                controller.usaCartaUtensile(View.this, parametri);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        } else if (cartaInUso.equalsIgnoreCase("Tampone Diamantato")) {
                            parametri.clear();
                            while (true) {
                                try {
                                    int com = -3;
                                    System.out.println("inserisci posizione dado riserva che vuoi girare(il dado sarà reinserito in coda alla riserva)");
                                    while (com < 0 || com >= dadiRiserva.size()) {
                                        com = input.call();
                                        if (com < 0 || com >= dadiRiserva.size()) {
                                            System.out.println("INSERIMENTO POSIZIONE DADO IN RISERVA DA GIRARE ERRATO!\n" +
                                                    "inserisci posizione dado riserva che vuoi girare(il dado sarà reinserito in coda alla riserva");
                                        }
                                    }
                                    parametri.add(0, com);
                                    break;

                                } catch (InputMismatchException e) {
                                    inputCommand.nextLine();
                                }
                            }
                            try {
                                controller.usaCartaUtensile(View.this, parametri);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        } else if (cartaInUso.equalsIgnoreCase("Diluente Per Pasta Salda")) {
                            if (!diluentePastaSaldaPhase2) {
                                while (true) {
                                    try {
                                        int com = -3;
                                        System.out.println("dammi dado riserva");
                                        while (com < 0 || com >= dadiRiserva.size()) {
                                            com = input.call();
                                            if (com < 0 || com >= dadiRiserva.size()) {
                                                System.out.println("INSERIMENTO POSIZIONE DADO IN RISERVA ERRATO!\n" +
                                                        "dammi dado riserva");
                                            }
                                        }
                                        parametri.add(0, com);
                                        break;

                                    } catch (InputMismatchException e) {
                                        inputCommand.nextLine();
                                    }
                                }

                                try {
                                    controller.usaCartaUtensile(View.this, parametri);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                                while (!updateView) {
                                    System.out.print("");
                                }

                                updateView = true; //<<<<critica
                            } else {
                                updateView = false;////<<<<critica
                                diluentePastaSaldaPhase2 = false;
                                System.out.println("il dado pescato dal sacchetto è:");
                                stampante.printDado(tool11Dado);
                                System.out.println("");
                                while (true) {
                                    try {
                                        int com = -3;
                                        System.out.println("dammi numero in cui vuoi cambiare 1-6");
                                        while (com < 1 || com > 6) {
                                            com = input.call();
                                            if (com < 1 || com > 6) {
                                                System.out.println("INSERIMENTO NUMERO ERRATO!\n" +
                                                        "dammi numero in cui vuoi cambiare 1-6");
                                            }
                                        }
                                        parametri.add(0, com);
                                        break;

                                    } catch (InputMismatchException e) {
                                        inputCommand.nextLine();
                                    }
                                }
                                while (true) {
                                    try {
                                        int com = -3;
                                        System.out.println("Inserisci la riga in cui vuoi posizionare il dado:(0 <= x <= 3)");
                                        while (com < 0 || com > 3) {
                                            com = input.call();
                                            if (com < 0 || com > 3) {
                                                System.out.println("INSERIMENTO RIGA ERRATO!\n" +
                                                        "Inserisci la riga in cui vuoi posizionare il dado:(0 <= x <= 3)");
                                            }
                                        }
                                        parametri.add(1, com);
                                        break;

                                    } catch (InputMismatchException e) {
                                        inputCommand.nextLine();
                                    }
                                }
                                while (true) {
                                    try {
                                        int com = -3;
                                        System.out.println("Inserisci la colonna in cui vuoi piazzare il dado:(0 <= y <= 4)");
                                        while (com < 0 || com > 4) {
                                            com = input.call();
                                            if (com < 0 || com > 4) {
                                                System.out.println("INSERIMENTO COLONNA ERRATO!\n" +
                                                        "Inserisci la colonna in cui vuoi piazzare il dado:(0 <= y <= 4)");
                                            }
                                        }
                                        parametri.add(2, com);
                                        break;

                                    } catch (InputMismatchException e) {
                                        inputCommand.nextLine();
                                    }
                                }
                                try {
                                    controller.usaCartaUtensile(View.this, parametri);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }


                        } else if (cartaInUso.equalsIgnoreCase("Taglierina Manuale")) {
                            parametri.clear();
                            if (!tracciatoDelRound.isEmpty()) {
                                if (numeroDadiTool12 == 0) {
                                    // if (!tracciatoDelRound.isEmpty()){
                                    while (!(tRound < tracciatoDelRound.size() + 1 && tRound >= 1)) {
                                        while (true) {
                                            try {
                                                System.out.println("Inserisci il numero del dado del tracciato del round di cui vuoi prendere il colore");
                                                tRound = input.call();
                                                break;
                                            } catch (InputMismatchException e) {
                                                inputCommand.nextLine();
                                            }
                                        }
                                    }
                                    parametri.add(0, tRound);
                                       /* } else {
                                            System.out.println("Carta non utilizzabile, tracciato vuoto.");
                                            updateView = true;}*/
                                    while (numeroDadiTool12 != 2 && numeroDadiTool12 != 1) {
                                        while (true) {
                                            try {
                                                System.out.println("Inserisci il numero di dadi che vuoi spostare (compreso tra 1 e 2):");
                                                numeroDadiTool12 = input.call();
                                                break;
                                            } catch (InputMismatchException e) {
                                                inputCommand.nextLine();
                                            }
                                        }
                                    }
                                    parametri.add(1, numeroDadiTool12);
                                } else {
                                    parametri.add(0, tRound);
                                    parametri.add(1, numeroDadiTool12);
                                }


                                while (true) {
                                    try {
                                        int com = -3;
                                        System.out.println("Inserisci la riga del dado che vuoi spostare :(0 <= x <= 3)");
                                        while (com < 0 || com > 3) {
                                            com = input.call();
                                            if (com < 0 || com > 3) {
                                                System.out.println("INSERIMENTO RIGA ERRATO!\n" +
                                                        "Inserisci la riga del dado che vuoi spostare :(0 <= x <= 3)");
                                            }
                                        }
                                        parametri.add(2, com);
                                        break;

                                    } catch (InputMismatchException e) {
                                        inputCommand.nextLine();
                                    }
                                }
                                while (true) {
                                    try {
                                        int com = -3;
                                        System.out.println("Inserisci la colonna del dado che vuoi spostare:(0 <= y <= 4)");
                                        while (com < 0 || com > 4) {
                                            com = input.call();
                                            if (com < 0 || com > 4) {
                                                System.out.println("INSERIMENTO COLONNA ERRATO!\n" +
                                                        "Inserisci la colonna del dado che vuoi spostare:(0 <= y <= 4)");
                                            }
                                        }
                                        parametri.add(3, com);
                                        break;

                                    } catch (InputMismatchException e) {
                                        inputCommand.nextLine();
                                    }
                                }

                                while (true) {
                                    try {
                                        int com = -3;
                                        System.out.println("Inserisci la riga in cui vuoi spostare il dado:(0 <= x <= 3)");
                                        while (com < 0 || com > 3) {
                                            com = input.call();
                                            if (com < 0 || com > 3) {
                                                System.out.println("INSERIMENTO RIGA ERRATO!\n" +
                                                        "Inserisci la riga in cui vuoi spostare il dado:(0 <= x <= 3)");
                                            }
                                        }
                                        parametri.add(4, com);
                                        break;

                                    } catch (InputMismatchException e) {
                                        inputCommand.nextLine();
                                    }
                                }
                                while (true) {
                                    try {
                                        int com = -3;
                                        System.out.println("Inserisci la colonna in cui vuoi spostare il dado:(0 <= y <= 4)");
                                        while (com < 0 || com > 4) {
                                            com = input.call();
                                            if (com < 0 || com > 4) {
                                                System.out.println("INSERIMENTO COLONNA ERRATO!\n" +
                                                        "Inserisci la colonna in cui vuoi spostare il dado:(0 <= y <= 4)");
                                            }
                                        }
                                        parametri.add(5, com);
                                        break;

                                    } catch (InputMismatchException e) {
                                        inputCommand.nextLine();
                                    }
                                }
                                try {
                                    controller.usaCartaUtensile(View.this, parametri);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Carta non utilizzabile, tracciato vuoto.");
                                Pausa(4);
                                updateView = true;
                            }

                        }

                    }

                    //====================

                    //flagSceltaCartaUtensile = 1;


                } else if (cmd == 3) {
                    passaturno = true;
                    //flagSceltaCartaUtensile=0;
                    // flagSceltaDado=0;

                    //chiamo il metodo del controller che mi permette di passare i parametri per la mossa scelta.
                    //  try {
                    parametri.add(0, 3); // ho scelto di passare il turno

                    try {
                        controller.svolgimentoPartita(View.this, parametri);  //TODO RIVEDERE il fatto che debba essere passata anche la vista deve essere ripensato
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    parametri = new ArrayList<Integer>(); // TODO RIVEDERE non è detto che sia necessario new dato che viene fatto solo per precauzione. potrebbe essere sufficiente lasciarlo nelle condizioni attuali senza reinizializzare, in modo da sapere quale è l'ultima chiamata che è stata fatta al model.

                           /* } catch (MossaIllegaleException e) {
                                System.out.print("mossa non consentita");  //TODO RIVEDERE da rivedere se funziona
                            }*/


                } else if (cmd == 4) {

                    synchronized (View.this) {
                        System.out.print("Inserisci il numero della carta della quale vuoi la descrizione:");
                        int c = -1;
                        try {
                            c = input.call();
                        } catch (InputMismatchException e) {
                            c = -1;
                            inputCommand.nextLine();
                        }
                        while (c < 0 || c > 2) {
                            try {
                                System.out.println("inserisci un numero carta valido della quale avere la descrizione:");
                                c = input.call();
                            } catch (InputMismatchException e) {
                                c = -1;
                                inputCommand.nextLine();
                            }
                        }

                        System.out.println();
                        stampante.printDescrizioneCartaUtensile(carteUtensile, c);

                        System.out.println("\nINSERISCI UN CARATTERE PER CONTINUARE");
                        String s = inputCommand.next();
                    }

                    stampante.printTavoloDiGioco(turno, round, tracciatoDelRound, planceGiocatori, 4, 5, giocatoreCorrente, id, carteObiettivoPubblico, carteObiettivoPrivato, carteUtensile, dadiRiserva, dadoSelezionato);  //<<-- aggiunte per aggiornare dopo aver letto la descrizione di
                    updateView = true;      //<<-- una carta
                    passaturno = false;       //<<--

                } else {
                    try {
                        updateMessage("SCELTA IMPOSSIBILE");
                    } catch (RemoteException ex) {
                        //do nothing
                    }

                    updateView = true;
                }


                System.out.println("\n\n\nattendi aggiornamento del tavolo di gioco...");

            }catch(InterruptedException ex){
                return;
            }

        }

    }

}