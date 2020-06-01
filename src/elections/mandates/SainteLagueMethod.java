package elections.mandates;

public class SainteLagueMethod extends DivisorMethod {

    public SainteLagueMethod() {
        super("Sainte-Laguë method", mandates -> 2 * mandates + 1);
    }

}
