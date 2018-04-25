package ingSw2018StaglianoVagagginiVergari.model;
import ingSw2018StaglianoVagagginiVergari.model.carteObiettivoPubblico.*;

public class FactoryCartaObiettivoPubblico {

    public static CartaObiettivoPubblico getCartaObiettivoPubblico(int Id){


        if(Id == 1){  //ColoriDiversiRiga
            return ColoriDiversiRiga.getInstance();

        } else if(Id == 2){ //ColoriDiversiColonna
            return ColoriDiversiColonna.getInstance();

        } else if(Id == 3){ //SfumatureDiverseRiga
            return SfumatureDiverseRiga.getInstance();

        } else if(Id == 4){ //SfumatureDiverseColonna
            return SfumatureDiverseColonna.getInstance();

        } else if(Id == 5){ //SfumatureChiare
            return SfumatureChiare.getInstance();

        } else if(Id == 6){ //SfumatureMedie
            return SfumatureMedie.getInstance();

        } else if(Id == 7){ //SfumatureScure
            return SfumatureScure.getInstance();

        } else if(Id == 8){ //SfumatureDiverse
            return SfumatureDiverse.getInstance();

        } else if(Id == 9){ //DiagonaliColorate
            return DiagonaliColorate.getInstance();

        } else if(Id == 10){ //VarietaDiColore
            return VarietaDiColore.getInstance();

        }

        return null;
    }


}



