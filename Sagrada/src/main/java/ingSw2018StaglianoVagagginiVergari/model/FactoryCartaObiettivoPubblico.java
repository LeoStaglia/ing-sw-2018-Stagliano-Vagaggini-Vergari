package ingSw2018StaglianoVagagginiVergari.model;
import ingSw2018StaglianoVagagginiVergari.model.carteObiettivoPubblico.*;

public class FactoryCartaObiettivoPubblico {
    public CartaObiettivoPubblico getCartaObiettivoPubblico(int Id){


        if(Id == 1){  //ColoriDiversiRiga
            return SfumatureChiare.getInstance();        //chiamata per instanziare un singleton

        } else if(Id == 2){ //ColoriDiversiColonna
            return SfumatureChiare.getInstance();

        } else if(Id == 3){ //SfumatureDiverseRiga
            return SfumatureChiare.getInstance();

        } else if(Id == 4){ //SfumatureDiverseColonna
            return SfumatureChiare.getInstance();

        } else if(Id == 5){ //SfumatureChiare
            return SfumatureChiare.getInstance();

        } else if(Id == 6){ //SfumatureMedie
            return SfumatureChiare.getInstance();

        } else if(Id == 7){ //SfumatureScure
            return SfumatureChiare.getInstance();

        } else if(Id == 8){ //SfumatureDiverse
            return SfumatureChiare.getInstance();

        } else if(Id == 9){ //DiagonaliColorate
            return SfumatureChiare.getInstance();

        } else if(Id == 10){ //VarietaDiColore
            return SfumatureChiare.getInstance();

        }

        return null;
    }


    }



