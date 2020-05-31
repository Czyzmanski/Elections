package elections.party;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class MaxAssessSumParty extends AssessSumParty {

    public MaxAssessSumParty(String name, int budget, int mandatesNumber) {
        super(name, budget, mandatesNumber, BinaryOperator.maxBy(Comparator.naturalOrder()));
    }

}
