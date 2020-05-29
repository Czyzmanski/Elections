package elections.voter;

import elections.district.District;
import elections.party.Action;

import java.util.Comparator;
import java.util.Random;

public class ExtremeQualityVoter extends Voter {

    /* Number of quality to find extremum of. */
    protected int numberOfQuality;
    protected Comparator<? super Integer> comparator;
    protected Random random;

    public ExtremeQualityVoter(String firstName, String lastName, District district,
                               int numberOfQuality, Comparator<? super Integer> comparator) {
        super(firstName, lastName, district);
        this.numberOfQuality = numberOfQuality - 1;
        this.random = new Random();
    }

    @Override
    public void vote() {
        int extremeQuality = district.stream()
                                 .map(cand -> cand.getQuality(numberOfQuality))
                                 .min(comparator)
                                 .orElseThrow();
        int countOfExtremeQuality = (int) district.stream()
                                              .map(cand -> cand.getQuality(numberOfQuality))
                                              .filter(quality -> quality == extremeQuality)
                                              .count();
        int toSkip = random.nextInt(countOfExtremeQuality);
        chosenCandidate = district.stream()
                                  .filter(cand -> cand.getQuality(numberOfQuality) == extremeQuality)
                                  .skip(toSkip)
                                  .findFirst()
                                  .orElseThrow();
        chosenCandidate.voteFor();
    }

    @Override
    public void applyAction(Action action) {
    }

}
