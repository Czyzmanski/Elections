package elections.model.voter;

import elections.model.candidate.Candidate;
import elections.model.district.District;
import elections.model.party.Party;

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
        return district.candidates(party);
    }

}
