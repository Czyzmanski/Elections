package elections.model.voter;

import elections.model.candidate.Candidate;
import elections.model.district.District;
import elections.model.party.Action;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UniversalVoter extends Voter {

    private static final int MIN_WEIGHT_VALUE = -100;
    private static final int MAX_WEIGHT_VALUE = 100;

    protected int[] weights;
    protected int[] prevWeights;

    public UniversalVoter(String firstName, String lastName, District district, int[] weights) {
        super(firstName, lastName, district);
        this.weights = Arrays.copyOf(weights, weights.length);
        this.prevWeights = Arrays.copyOf(weights, weights.length);
    }

    @Override
    public int assess(Candidate candidate) {
        return IntStream.range(0, weights.length)
                        .map(i -> weights[i] * candidate.quality(i))
                        .sum();
    }

    @Override
    protected Stream<Candidate> matchingCandidates() {
        return district.candidates();
    }

    @Override
    public void vote() {
        chosenCandidate = matchingCandidates().max(Comparator.comparingInt(this::assess))
                                              .orElseThrow();
        chosenCandidate.voteFor();
    }

    @Override
    public void influence(Action action) {
        System.arraycopy(weights, 0, prevWeights, 0, weights.length);

        for (int i = 0; i < weights.length; i++) {
            int change = action.getChange(i);
            if (change < 0) {
                weights[i] = Math.max(weights[i] + change, MIN_WEIGHT_VALUE);
            } else {
                weights[i] = Math.min(weights[i] + change, MAX_WEIGHT_VALUE);
            }
        }
    }

    @Override
    public void revertLastInfluence() {
        System.arraycopy(prevWeights, 0, weights, 0, weights.length);
    }

}
