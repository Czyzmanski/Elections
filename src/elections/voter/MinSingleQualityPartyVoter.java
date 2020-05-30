package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;
import elections.party.Party;

import java.util.stream.Stream;

public class MinSingleQualityPartyVoter extends MinSingleQualityVoter {

    protected Party party;

    public MinSingleQualityPartyVoter(String firstName, String lastName,
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
