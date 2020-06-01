package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;
import elections.party.Party;

import java.util.stream.Stream;

public class MaxSingleQualityPartyVoter extends MaxSingleQualityVoter {

    protected Party party;

    public MaxSingleQualityPartyVoter(String firstName, String lastName,
                                      District district, int qualityNumber, Party party) {
        super(firstName, lastName, district, qualityNumber);
        this.party = party;
    }

    @Override
    protected Stream<Candidate> matchingCandidates() {
        return super.matchingCandidates()
                    .filter(c -> c.belongs(party));
    }

}
