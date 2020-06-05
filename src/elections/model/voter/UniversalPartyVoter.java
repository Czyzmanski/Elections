package elections.model.voter;

import elections.model.candidate.Candidate;
import elections.model.district.District;
import elections.model.party.Party;

import java.util.stream.Stream;

public class UniversalPartyVoter extends UniversalVoter {

    protected Party party;

    public UniversalPartyVoter(String firstName, String lastName,
                               District district, int[] weights, Party party) {
        super(firstName, lastName, district, weights);
        this.party = party;
    }

    @Override
    protected Stream<Candidate> matchingCandidates() {
        return district.candidates(party);
    }

}
