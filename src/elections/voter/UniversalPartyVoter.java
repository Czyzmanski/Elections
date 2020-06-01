package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;
import elections.party.Party;

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
        return super.matchingCandidates()
                    .filter(candidate -> candidate.belongs(party));
    }

}
