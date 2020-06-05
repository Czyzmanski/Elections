package elections.model.voter;

import elections.model.candidate.Candidate;
import elections.model.district.District;
import elections.model.party.Action;

import java.util.stream.Stream;

public class CandidateVoter extends Voter {

    protected final Candidate candidate;

    public CandidateVoter(String firstName, String lastName,
                          District district, Candidate candidate) {
        super(firstName, lastName, district);
        this.candidate = candidate;
    }

    @Override
    public void vote() {
        chosenCandidate = matchingCandidates().findFirst()
                                              .orElseThrow();
        chosenCandidate.voteFor();
    }

    @Override
    protected Stream<Candidate> matchingCandidates() {
        return Stream.of(candidate);
    }

    @Override
    public int assess(Candidate candidate) {
        return 0;
    }

    @Override
    public void influence(Action action) {
    }

    @Override
    public void revertLastInfluence() {
    }

}
