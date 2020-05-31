package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;
import elections.party.Action;
import elections.party.Party;

import java.util.Random;
import java.util.stream.Stream;

public class PartyVoter extends Voter {

    protected final Party party;
    protected final Random random;

    public PartyVoter(String firstName, String lastName, District district, Party party) {
        super(firstName, lastName, district);
        this.party = party;
        this.random = new Random();
    }

    @Override
    public double assess(Candidate candidate) {
        return 0;
    }

    @Override
    protected Stream<Candidate> matchingCandidates() {
        return district.candidates()
                       .filter(candidate -> candidate.belongs(party));
    }

    @Override
    public void vote() {
        int toSkip = random.nextInt(district.mandatesNumber());
        chosenCandidate = matchingCandidates().skip(toSkip)
                                              .findFirst()
                                              .orElseThrow();
    }

    @Override
    public void influence(Action action) {
    }

    @Override
    public void revertLastInfluence() {
    }

}
