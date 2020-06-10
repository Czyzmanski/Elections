package elections.model.seats;

public class DHondtMethod extends DivisorMethod {

    public DHondtMethod() {
        super("D'Hondt method", seats -> seats + 1);
    }

}
