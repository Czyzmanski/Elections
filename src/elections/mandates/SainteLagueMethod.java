package elections.mandates;

public class SainteLagueMethod extends DivisorMethod {

    public SainteLagueMethod() {
        super(mandates -> 2 * mandates + 1);
    }

}
