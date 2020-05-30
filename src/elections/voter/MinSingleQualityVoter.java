package elections.voter;

import elections.candidate.Candidate;
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
    public double assess(Candidate candidate) {
        return 0;
    }

    @Override
    public void influence(Action action) {
    }

}
