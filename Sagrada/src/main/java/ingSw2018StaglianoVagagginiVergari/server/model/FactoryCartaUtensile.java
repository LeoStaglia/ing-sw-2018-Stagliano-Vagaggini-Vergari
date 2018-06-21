package ingSw2018StaglianoVagagginiVergari.server.model;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.*;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.PennelloPerEglomise;


public class FactoryCartaUtensile {


    public static CartaUtensile getCartaUtensile(int Id){

        if(Id == 1){ //PinzaSgrossatrice
            return new PinzaSgrossatrice();            //chiamata per instanziare un singleton

        } else if(Id == 2){ //PennelloPerEglomise
            return new PennelloPerEglomise();

        } else if(Id == 3){ //AlesatorePerLaminaDiRame
            return new AlesatoreRame();

        } else if(Id == 4){ //Lathekin
            return new Lathekin();

        } else if(Id == 5){ //TaglierinaCircolare
            return new TaglierinaCircolare();

        } else if(Id == 6){ //PennelloPerPastaSalda
            return new PennelloPastaSalda();

        } else if(Id == 7){ //Martelletto
            return new Martelletto();

        } else if(Id == 8){ //TenagliaARotelle
            return new TenagliaARotelle();

        } else if(Id == 9){ //RigaInSughero
            return new RigaInSughero();

        } else if(Id == 10){ //TamponeDiamantato
            return new TamponeDiamantato();

        } else if(Id == 11){ //DiluentePerPastaSalda
            return new DiluentePerPastaSalda();

        } else if(Id == 12){ //TaglierinaManuale
            return new TaglierinaManuale();

        }


        return null;
    }
}
