package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;
import elections.party.Action;

public class CandidateVoter extends Voter {

    protected final Candidate candidate;

    public CandidateVoter(String firstName, String lastName,
                          District district, Candidate candidate) {
        super(firstName, lastName, district);
        this.candidate = candidate;
    }

    @Override
    public void vote() {
        candidate.voteFor();
    }

    @Override
    public void applyAction(Action action) {
    }

}
