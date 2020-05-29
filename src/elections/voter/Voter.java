package elections.voter;

import elections.candidate.Candidate;
import elections.district.District;
import elections.party.Action;

public abstract class Voter {

    protected String firstName;
    protected String lastName;
    protected District district;
    protected Candidate chosenCandidate;

    public Voter(String firstName, String lastName, District district) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.district = district;
        this.chosenCandidate = null;
    }

    public abstract void vote();

    public abstract void applyAction(Action action);

}
