package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;
import elections.party.Action;

import java.util.Comparator;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SingleQualityVoter extends Voter {

    /* Number of quality to find extremum of. */
    protected int qualityNumber;
    protected Predicate<? super Candidate> candidatePredicate;
    protected Comparator<? super Integer> qualityComparator;
    protected Random random;

    public SingleQualityVoter(String firstName, String lastName,
                              District district, int qualityNumber,
                              Predicate<? super Candidate> candidatePredicate,
                              Comparator<? super Integer> qualityComparator) {
        super(firstName, lastName, district);
        this.qualityNumber = qualityNumber - 1;
        this.candidatePredicate = candidatePredicate;
        this.qualityComparator = qualityComparator;
        this.random = new Random();
    }

    protected Stream<Candidate> matchingCandidates() {
        return district.stream()
                       .filter(candidatePredicate);
    }

    protected Stream<Integer> matchingQualities() {
        return matchingCandidates().map(c -> c.quality(qualityNumber));
    }

    @Override
    public void vote() {
        int extremeQlty = matchingQualities().min(qualityComparator)
                                             .orElseThrow();
        int extremeQltyCount = (int) matchingQualities().filter(qlty -> qlty == extremeQlty)
                                                        .count();
        chosenCandidate = matchingCandidates().filter(c -> c.quality(qualityNumber) == extremeQlty)
                                              .skip(random.nextInt(extremeQltyCount))
                                              .findFirst()
                                              .orElseThrow();
        chosenCandidate.voteFor();
    }

    @Override
    public void applyAction(Action action) {
    }

}
