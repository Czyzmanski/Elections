package elections.mandates;

public class JeffersonMethod extends DivisorMethod {

    public JeffersonMethod() {
        super("Jefferson method", mandates -> mandates + 1);
    }

}
