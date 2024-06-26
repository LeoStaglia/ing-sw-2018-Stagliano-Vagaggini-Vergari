package ingSw2018StaglianoVagagginiVergari.server.model;
import java.util.*;

public class TracciatoDelRound {

    private int roundAttuale=1;

    /** rimanenzeRiserva out are the dice that remained in the Draft Pool after the end of the round except for one*/
    private ArrayList<Dado> rimanenzeRiservaOut = new ArrayList<>();
    /** rimanenzeRiserva on are the dice that cover the Round Track*/
    private ArrayList<Dado> rimanenzeRiservaOn = new ArrayList<Dado>();

    public int getRoundAttuale() {
        return roundAttuale;
    }

    public void nextRound() {
        this.roundAttuale = roundAttuale + 1;
    }

    public void addRimanenzeRiservaOut(ArrayList<Dado> rimanenze) {
        this.rimanenzeRiservaOut.addAll(rimanenze);
    }

    public void addRimanenzeRiservaOn(Dado copriRound) {
        this.rimanenzeRiservaOn.add(copriRound);
    }

    public Dado getRimanenzeRiservaOn(int i) {
        return this.rimanenzeRiservaOn.get(i - 1);
    }

    public ArrayList<Dado> getRimanenzeRiservaOn() {
        return rimanenzeRiservaOn;
    }

    public ArrayList<Dado> getRimanenzeRiservaOut() {
        return this.rimanenzeRiservaOut;
    }

   /** method used to remove a dice on the Roundtrack in order to use a Card Tool
    * @return the die removed*/
    public Dado removeRimanenzeRiservaOn(int i) {
        return rimanenzeRiservaOn.remove(i - 1);
    }

    /** method to set the Roundtrack cells
     * @param i the round to cover (1-10)
     * @param d the die which "covers" the number of round*/
    public void setRimanenzeRiservaOn(int i,Dado d) {
        this.rimanenzeRiservaOn.add(i - 1,d);
    }

}

