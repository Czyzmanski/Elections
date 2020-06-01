package elections.party;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class MaxAssessSumParty extends AssessSumParty {

    public MaxAssessSumParty(String name, int budget) {
        super(name, budget, BinaryOperator.maxBy(Comparator.naturalOrder()));
    }

}
