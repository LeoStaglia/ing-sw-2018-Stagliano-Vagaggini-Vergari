package ingSw2018StaglianoVagagginiVergari.server.model.carteSchema;

public class FactorySchema {
    public static Schema get(int schemaScelto){
        if (schemaScelto==1){
            return KaleidoscopicDream.get();
        }else if(schemaScelto==2){
            return Virtus.get();
        }else if(schemaScelto==3){
            return AuroraeMagnificus.get();
        }else if(schemaScelto==4){
            return ViaLux.get();
        }else if(schemaScelto==5){
            return SunCatcher.get();
        }else if(schemaScelto==6){
            return Bellesguard.get();
        }else if(schemaScelto==7){
            return Gravitas.get();
        }else if(schemaScelto==8){
            return FractalDrops.get();
        }else if(schemaScelto==9){
            return LuxAstram.get();
        }else if(schemaScelto==10){
            return ChromaticSplendor.get();
        }else if(schemaScelto==11){
            return Firelight.get();
        }else if(schemaScelto==12){
            return LuzCelestial.get();
        }else{
            /*If the integer value passed as parameter isn't in the range between 1 and 12 the method return null.
            The alternative is to throw exception;*/
            return null;

        }
    }
}
