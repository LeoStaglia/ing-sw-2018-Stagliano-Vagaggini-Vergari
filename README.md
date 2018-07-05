# Prova Finale Ingegneria del Software 2017/2018
# Membri del team:
1.  __Staglianò Leonardo__ Matricola:850431 CodicePersona:10542624
2.  __Vagaggini Marco__ Matricola:846557 CodicePersona:10526531 
3.  __Vergari Maurizio__ Matricola:845745 CodicePersona 10503085

## Coverage dei test:
    File presente nella repo:
    https://github.com/LeoStaglia/ing-sw-2018-Stagliano-Vagaggini-Vergari/blob/master/ScreenShotTestCoverage.pdf

## Link diagramma UML:
    File presente nella repo:
    https://github.com/LeoStaglia/ing-sw-2018-Stagliano-Vagaggini-Vergari/blob/master/Diagramma_UMLpdf.pdf

## Funzionalità implementate:
1. Regole Complete
2. GUI
3. CLI
4. RMI
5. Socket
6. FA: Multipartita

# Impostare una partita:
1. Avviare un server,verra richiesta la configurazione da command line dei seguenti parametri:
   1. porta socket
   2. porta rmi
   3. timer turno(millisecondi)
   4. timer di attesa registrazione(millisecondi)  
   (il tempo per il quale il server rimane in  attesa di connessione di ulteriori giocatori per quella partita dopo aver raggiunto i due giocatori)
   5. timer di scelta schemi(millisecondi)
2. Avviare un client, verrà richiesta la configurazione da command line dei seguenti parametri
   1. porta socket
   2. porta rmi
   3. indirizzo ip del server (da specifica noto all'utente)
3. Scelta del client di gioco con GUI(1) o CLI(2)
4. Scelta del tipo di connessione RMI o Socket differenziata a seconda di avvio in CLI o in GUI
    1. CLI : richiesta di parametri command line
    2. GUI: scelta mediante pressione di un bottone in un popup
  A questo punto il giocatore può registrarsi a una partita o riconnettersi tramite login a una già esistente.
  
  # Scelte Implementative:
  ## Gestione input utente:
  Solo la view del giocatore corrente può contattare il controller
  ## Carte Utensile:
  1. Pinza Sgrossatrice: se si prova a incrementare un 6 o a decrementare un 1 la carta non viene pagata e viene segnalata l'impossibilità di effettuare l'operazione
  2. Pennello per Eglomise: se si prova a spostare un dado in una posizione non valida viene segnalata mossa non valida e la carta non viene pagata
  3. Alesatore per Lamina di Rame: se si prova a spostare un dado in una posizione non valida viene segnalata mossa non valida e la carta non viene pagata.
  4. Lathekin: 1. Non essendo esplicitamente indicata la necessità di dover spostare due dadi distinti abbiamo deciso di lasciare la possibilità di spostare lo stesso dado due volte(poco sensato ma strategicamente possibile ad esempio utilizzo la carta solo per aumentarne il costo)
              2. I due dadi vengono spostati uno alla volta e il calcolo delle mosse per il piazzamento del secondo dado tiene conto ovviamente dello schema considerando il primo già piazzato.
              3. Se un giocatore effettua una mossa non valida durante il piazzamento si ripristina l'effetto della carta
              4. In caso di disconnessione tra lo spostamento del primo dado e lo spostamento del secondo non viene ripristinato l'effetto della carta e il costo rimane a 1 nel caso fosse precedentemente a 1
  5. Taglierina Circolare: nessuna scelta in particolare
  6. Pennello per Pasta Salda:se il giocatore si disconnette prima di piazzare, perderà il diritto di utilizzare la carta e il dado verrà scartato infatti l'utente anche non piazzando ha influenzato la partita in questo caso la penalità consiste nel lasciare una casella vuota che influenzerà negativamente il punteggio finale
  7. Martelletto: nessuna scelta particolare
  8. Tenaglia a Rotelle: il giocatore una volta attivata la carta salta il suo secondo turno e può piazzare due volte se non aveva mai piazzato, una sola volta altrimenti.
  9. Riga in Sughero: nessuna scelta in particolare
  10. Tampone diamantato: nessuna scelta in particolare
  11. Diluente Per Pasta Salda:se un giocatore esce dopo aver scambiato il dado del sacchetto con quello nella riserva, la carta utensile verrà ritenuta utilizzata e il dado sarà riposto in riserva
  12. Taglerina Manuale: nel caso di scelta di spostamento di due dadi valgono le stesse scelte effettuate per lathekin.
  
  ## Problematiche note: 
  ritardo rilevazione disconnessione giocatore in caso di disconnessione fisica della rete(spegnimento wifi,rimozione lan ecc.)
  In ogni caso tale disconnessione viene rilevata alla fine di un timer turn.
  
  
  
