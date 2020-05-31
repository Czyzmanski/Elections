package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;

import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;

public abstract class SingleQualityVoter extends Voter {

    protected Random random;
    protected int qualityNumber;
    protected BinaryOperator<Integer> qualityAccumulator;

    public SingleQualityVoter(String firstName, String lastName, District district,
                              int qualityNumber, BinaryOperator<Integer> qualityAccumulator) {
        super(firstName, lastName, district);
        this.random = new Random();
        this.qualityNumber = qualityNumber - 1;
        this.qualityAccumulator = qualityAccumulator;
    }

    protected Stream<Candidate> matchingCandidates() {
        return district.candidates();
    }

    protected Stream<Integer> matchingQualities() {
        return matchingCandidates().map(c -> c.quality(qualityNumber));
    }

    @Override
    public void vote() {
        if (qualityAccumulator == null) {
            throw new IllegalStateException("Field qualityAccumulator is null.");
        } else {
            int desiredQuality = matchingQualities().reduce(qualityAccumulator)
                                                 .orElseThrow();
            int desiredQualityCount =
                    toIntExact(matchingQualities().filter(quality -> quality == desiredQuality)
                                                  .count());
            chosenCandidate =
                    matchingCandidates().filter(
                            candidate -> candidate.quality(qualityNumber) == desiredQuality)
                                        .skip(random.nextInt(desiredQualityCount))
                                        .findFirst()
                                        .orElseThrow();
            chosenCandidate.voteFor();
        }
    }

}
