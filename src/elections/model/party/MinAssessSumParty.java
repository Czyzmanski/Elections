package elections.model.party;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class MinAssessSumParty extends AssessSumParty {

    public MinAssessSumParty(String name, int budget) {
        super(name, budget, BinaryOperator.minBy(Comparator.naturalOrder()));
    }

}
