package elections.model.voter;

import elections.model.candidate.Candidate;
import elections.model.district.District;

import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public abstract class SingleQualityVoter extends Voter {

    protected int qualityNumber;
    protected BinaryOperator<Integer> qualityAccumulator;

    public SingleQualityVoter(String firstName, String lastName, District district,
                              int qualityNumber, BinaryOperator<Integer> qualityAccumulator) {
        super(firstName, lastName, district);
        this.qualityNumber = qualityNumber - 1;
        this.qualityAccumulator = qualityAccumulator;
    }

    protected Stream<Candidate> matchingCandidates() {
        return district.candidates();
    }

    protected Stream<Integer> matchingQualities() {
        return matchingCandidates().map(candidate -> candidate.quality(qualityNumber));
    }

    @Override
    public void vote() {
        if (qualityAccumulator == null) {
            throw new IllegalStateException("Field qualityAccumulator is null.");
        } else {
            int desiredQuality = matchingQualities().reduce(qualityAccumulator)
                                                 .orElseThrow();
            int desiredQualityCount =
                    (int) (matchingQualities().filter(quality -> quality == desiredQuality)
                                              .count());
            chosenCandidate =
                    matchingCandidates().filter(
                            candidate -> candidate.quality(qualityNumber) == desiredQuality)
                                        .skip(new Random().nextInt(desiredQualityCount))
                                        .findFirst()
                                        .orElseThrow();
            chosenCandidate.voteFor();
        }
    }

}
