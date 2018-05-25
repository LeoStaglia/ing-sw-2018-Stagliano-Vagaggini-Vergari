package ingSw2018StaglianoVagagginiVergari.server.model;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.*;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.PennelloPerEglomise;


public class FactoryCartaUtensile {


    public static CartaUtensile getCartaUtensile(int Id){

        if(Id == 1){ //PinzaSgrossatrice
            return PinzaSgrossatrice.getInstance();            //chiamata per instanziare un singleton

        } else if(Id == 2){ //PennelloPerEglomise
            return PennelloPerEglomise.getInstance();

        } else if(Id == 3){ //AlesatorePerLaminaDiRame
            return AlesatoreRame.getInstance();

        } else if(Id == 4){ //Lathekin
            return Lathekin.getInstance();

        } else if(Id == 5){ //TaglierinaCircolare
            return TaglierinaCircolare.getInstance();

        } else if(Id == 6){ //PennelloPerPastaSalda
            return PennelloPastaSalda.getInstance();

        } else if(Id == 7){ //Martelletto
            return Martelletto.getInstance();

        } else if(Id == 8){ //TenagliaARotelle
            return TenagliaARotelle.getInstance();

        } else if(Id == 9){ //RigaInSughero
            return RigaInSughero.getInstance();

        } else if(Id == 10){ //TamponeDiamantato
            return TamponeDiamantato.getInstance();

        } else if(Id == 11){ //DiluentePerPastaSalda
            return DiluentePerPastaSalda.getInstance();

        } else if(Id == 12){ //TaglierinaManuale
            return TaglierinaManuale.getInstance();

        }


        return null;
    }
}
