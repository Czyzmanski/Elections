package elections.voter;

import elections.district.District;
import elections.party.Action;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class MinSingleQualityVoter extends SingleQualityVoter {

    public MinSingleQualityVoter(String firstName, String lastName,
                                 District district, int qualityNumber) {
        super(firstName, lastName, district, qualityNumber);
        this.qualityAccumulator = BinaryOperator.minBy(Comparator.naturalOrder());
    }

    @Override
    public void applyAction(Action action) {
    }

}
