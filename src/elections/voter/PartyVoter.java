package elections.voter;

import elections.district.District;
import elections.party.Action;
import elections.party.Party;

import java.util.Random;

public class PartyVoter extends Voter {

    protected final Party party;
    protected final Random random;

    public PartyVoter(String firstName, String lastName, District district, Party party) {
        super(firstName, lastName, district);
        this.party = party;
        this.random = new Random();
    }

    @Override
    public void vote() {
        int toSkip = random.nextInt(district.numberOfMandates());
        chosenCandidate = district.stream()
                                  .filter(candidate -> candidate.belongs(party))
                                  .skip(toSkip)
                                  .findFirst()
                                  .orElseThrow();
    }

    @Override
    public void applyAction(Action action) {
    }

}
