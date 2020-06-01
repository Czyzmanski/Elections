package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;
import elections.party.Action;
import elections.party.Party;

import java.util.Random;
import java.util.stream.Stream;

public class PartyVoter extends Voter {

    protected final Party party;

    public PartyVoter(String firstName, String lastName, District district, Party party) {
        super(firstName, lastName, district);
        this.party = party;
    }

    @Override
    public int assess(Candidate candidate) {
        return 0;
    }

    @Override
    protected Stream<Candidate> matchingCandidates() {
        return district.candidates()
                       .filter(candidate -> candidate.belongs(party));
    }

    @Override
    public void vote() {
        chosenCandidate = matchingCandidates().skip(new Random().nextInt(district.mandatesNumber()))
                                              .findFirst()
                                              .orElseThrow();
        chosenCandidate.voteFor();
    }

    @Override
    public void influence(Action action) {
    }

    @Override
    public void revertLastInfluence() {
    }

}
