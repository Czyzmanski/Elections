package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;
import elections.party.Action;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class UniversalVoter extends Voter {

    private static final int MIN_WEIGHT_VALUE = -100;
    private static final int MAX_WEIGHT_VALUE = 100;

    protected int[] weights;

    public UniversalVoter(String firstName, String lastName, District district, int[] weights) {
        super(firstName, lastName, district);
        this.weights = Arrays.copyOf(weights, weights.length);
    }

    @Override
    public double assess(Candidate candidate) {
        int weightedQualitiesSum = 0;
        for (int i = 0; i < weights.length; i++) {
            weightedQualitiesSum += weights[i] * candidate.quality(i);
        }

        int weightsSum = Arrays.stream(weights)
                               .sum();

        return (double) weightedQualitiesSum / (double) weightsSum;
    }

    @Override
    protected Stream<Candidate> matchingCandidates() {
        return district.candidates();
    }

    @Override
    public void vote() {
        chosenCandidate = matchingCandidates().max(Comparator.comparingDouble(this::assess))
                                              .orElseThrow();
        chosenCandidate.voteFor();
    }

    @Override
    public void influence(Action action) {
        for (int i = 0; i < weights.length; i++) {
            int change = action.getChange(i);
            if (change < 0) {
                weights[i] = Math.max(weights[i] + change, MIN_WEIGHT_VALUE);
            } else {
                weights[i] = Math.min(weights[i] + change, MAX_WEIGHT_VALUE);
            }
        }
    }

}
