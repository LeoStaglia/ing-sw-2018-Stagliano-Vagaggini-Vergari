package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;


import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

public class TamponeDiamantato implements CartaUtensile {

    private boolean costo ;
    private String Nome = "TamponeDiamantato";
    private int id = 10;
    String descrizione="Dopo aver scelto un dado, giralo\n" +
            "sulla faccia opposta\n\n" +
            "N.B. 6 diventa 1, 5 diventa 2, 4\n" +
            " diventa 3 ecc.";







    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static TamponeDiamantato instance = new TamponeDiamantato();
    public static TamponeDiamantato getInstance(){
        return instance;
    }

    //----------------------------

    @Override
    public void usaEffettoCarta(Partita PartitaCorrente) {

        Dado DadoinMano;

        int scelta = 0;  //todo deve essere dato dall'utente


        //è un numero a caso, ma questo valore deve essere dato dall'utente in quanto si tratta di una scelta
        DadoinMano = PartitaCorrente.getDadofromRiserva(scelta);

        int numero = DadoinMano.getNumero();
        String colore = DadoinMano.getColore();


        if (numero==1){
            DadoinMano = new Dado(colore,6);
        }
        else if(numero==2){
            DadoinMano = new Dado(colore,5);

        }
        else if(numero==3){
            DadoinMano = new Dado(colore,4);

        }
        else if(numero==4){
            DadoinMano = new Dado(colore,3);

        }
        else if(numero==5){
            DadoinMano = new Dado(colore,2);

        }
        else if(numero==6){
            DadoinMano = new Dado(colore,1);

        }


        PartitaCorrente.setDadoSelezionato(DadoinMano);


    }

    @Override
    public int getCosto() {
        if(costo){
            return 2;
        }
        else return 1;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }
}
