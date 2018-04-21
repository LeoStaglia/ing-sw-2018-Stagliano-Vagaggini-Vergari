package ingSw2018StaglianoVagagginiVergari.model;
import java.util.*;

public class TracciatoDelRound {

        private int roundAttuale;

        // rimanenzeRiserva out are the dice that remained in the Draft Pool after the end of the round except for one
        private Set<Dado> rimanenzeRiservaOut=new HashSet<Dado>();
        // rimanenzeRiserva on are the dice that cover the Round Track
        private List<Dado>rimanenzeRiservaOn=new ArrayList<Dado>();

        public int getRoundAttuale(){
            return roundAttuale;
        }

        public void nextRound(){
            this.roundAttuale=roundAttuale+1;
        }

        public void addRimanenzeRiservaOut(HashSet<Dado> rimanenze){
            this.rimanenzeRiservaOut.addAll(rimanenze);
        }

        public void addRimanenzeRiservaOn(Dado copriRound){
            this.rimanenzeRiservaOn.add(copriRound);
        }

        public Dado getRimanenzeRiservaOn(int i){
            return this.rimanenzeRiservaOn.get(i-1);
        }

        // method used to calculate the TargetScore(singlePlayer)
        public int calcolaSommaRimanenzeRiserva(){
            int somma=0;
            for (Dado dado:rimanenzeRiservaOut) {
                somma+=dado.getNumero();
            }
            for(Dado d:rimanenzeRiservaOn){
                somma+=d.getNumero();
            }
            return somma;

        }
    }

