package ingSw2018StaglianoVagagginiVergari.server.model;

public class Coppia<F,S> {

    private F first;
    private S second;

    public Coppia(F left, S right) {
        this.first = left;
        this.second = right;
    }

    public F getFirst() { return first; }
    public S getSecond() { return second; }

    @Override
    public int hashCode() { return first.hashCode() ^ second.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coppia)) return false;
        Coppia coppia = (Coppia) o;
        return this.first.equals(coppia.getFirst()) &&
                this.second.equals(coppia.getSecond());
    }

}
