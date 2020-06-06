package elections.model.voter;

import elections.simulation.Reusable;
import elections.model.candidate.Candidate;
import elections.model.district.District;
import elections.model.party.Action;

import java.util.stream.Stream;

public abstract class Voter implements Reusable {

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

    protected abstract Stream<Candidate> matchingCandidates();

    public abstract void vote();

    public abstract void influence(Action action);

    public abstract void revertLastInfluence();

    public abstract int assess(Candidate candidate);

    @Override
    public String toString() {
        return String.format("%s %s, chosen candidate: %s", firstName, lastName,
                             chosenCandidate == null ? "" : chosenCandidate.getName());
    }

    @Override
    public void init() {
        chosenCandidate = null;
    }

}
