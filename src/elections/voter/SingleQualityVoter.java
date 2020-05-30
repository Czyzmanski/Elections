package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;

import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public abstract class SingleQualityVoter extends Voter {

    protected Random random;
    protected int qualityNumber;
    protected BinaryOperator<Integer> qualityAccumulator;

    public SingleQualityVoter(String firstName, String lastName,
                              District district, int qualityNumber) {
        super(firstName, lastName, district);
        this.random = new Random();
        this.qualityNumber = qualityNumber - 1;
    }

    protected Stream<Candidate> matchingCandidates() {
        return district.stream();
    }

    protected Stream<Integer> matchingQualities() {
        return matchingCandidates().map(c -> c.quality(qualityNumber));
    }

    @Override
    public void vote() {
        if (qualityAccumulator == null) {
            throw new IllegalStateException("Value of qualityAccumulator field is not set.");
        } else {
            int desiredQlty = matchingQualities().reduce(qualityAccumulator)
                                                 .orElseThrow();
            int desiredQltyCount = Math.toIntExact(matchingQualities().filter(q -> q == desiredQlty)
                                                                      .count());
            chosenCandidate = matchingCandidates().filter(c -> c.quality(qualityNumber) == desiredQlty)
                                                  .skip(random.nextInt(desiredQltyCount))
                                                  .findFirst()
                                                  .orElseThrow();
            chosenCandidate.voteFor();
        }
    }

}
