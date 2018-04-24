package ingSw2018StaglianoVagagginiVergari.model;
import ingSw2018StaglianoVagagginiVergari.model.carteUtensile.*;


public class FactoryCartaUtensile {


    public static CartaUtensile getCartaUtensile(int Id){

        if(Id == 1){ //PinzaSgrossatrice
            return PennelloPerEglomise.getInstance();            //chiamata per instanziare un singleton

        } else if(Id == 2){ //PennelloPerEglomise
            return PennelloPerEglomise.getInstance();

        } else if(Id == 3){ //AlesatorePerLaminaDiRame
            return PennelloPerEglomise.getInstance();

        } else if(Id == 4){ //Lathekin
            return PennelloPerEglomise.getInstance();

        } else if(Id == 5){ //TaglierinaCircolare
            return PennelloPerEglomise.getInstance();

        } else if(Id == 6){ //PennelloPerPastaSalda
            return PennelloPerEglomise.getInstance();

        } else if(Id == 7){ //Martelletto
            return PennelloPerEglomise.getInstance();

        } else if(Id == 8){ //TenagliaARotelle
            return PennelloPerEglomise.getInstance();

        } else if(Id == 9){ //RigaInSughero
            return PennelloPerEglomise.getInstance();

        } else if(Id == 10){ //TamponeDiamantato
            return PennelloPerEglomise.getInstance();

        } else if(Id == 11){ //DiluentePerPastaSalda
            return PennelloPerEglomise.getInstance();

        } else if(Id == 12){ //TaglierinaManuale
            return PennelloPerEglomise.getInstance();

        }


        return null;
    }
}
