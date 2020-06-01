package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;
import elections.party.Action;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class MaxSingleQualityVoter extends SingleQualityVoter {

    public MaxSingleQualityVoter(String firstName, String lastName,
                                 District district, int qualityNumber) {
        super(firstName, lastName, district, qualityNumber,
              BinaryOperator.maxBy(Comparator.naturalOrder()));
    }

    @Override
    public int assess(Candidate candidate) {
        return 0;
    }

    @Override
    public void influence(Action action) {
    }

    @Override
    public void revertLastInfluence() {
    }

}
