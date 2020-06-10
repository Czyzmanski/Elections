package elections.model.voter;

import elections.model.candidate.Candidate;
import elections.model.district.District;
import elections.model.party.Action;
import elections.model.party.Party;

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
        return district.candidates(party);
    }

    @Override
    public void vote() {
        chosenCandidate = matchingCandidates().skip(new Random().nextInt(district.getSeatsNumber()))
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
