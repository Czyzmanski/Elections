package elections.voter;

import elections.district.District;
import elections.party.Action;
import elections.party.Party;

import java.util.NoSuchElementException;
import java.util.Random;

public class PartyVoter extends Voter {

    private final Party party;
    private final Random random;

    public PartyVoter(String firstName, String lastName, District district, Party party) {
        super(firstName, lastName, district);
        this.party = party;
        this.random = new Random();
    }

    @Override
    public void vote() {
        int toSkip = this.random.nextInt(this.district.numberOfMandates());
        this.chosenCandidate = this.district.stream()
                                            .filter(candidate -> candidate.belongs(this.party))
                                            .skip(toSkip)
                                            .findFirst()
                                            .orElse(null);
        if (this.chosenCandidate == null) {
            throw new NoSuchElementException("No such candidate");
        } else {
            this.chosenCandidate.voteFor();
        }
    }

    @Override
    public void applyAction(Action action) {
    }

}
