package elections.party;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class MinAssessSumParty extends AssessSumParty {

    public MinAssessSumParty(String name, int budget, int mandatesNumber) {
        super(name, budget, mandatesNumber, BinaryOperator.minBy(Comparator.naturalOrder()));
    }

}
