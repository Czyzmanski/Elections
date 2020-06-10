package elections.model.seats;

public class SainteLagueMethod extends DivisorMethod {

    public SainteLagueMethod() {
        super("Sainte-Laguë method", seats -> 2 * seats + 1);
    }

}
