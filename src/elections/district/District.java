package elections.district;

import elections.candidate.Candidate;
import elections.mandates.MandatesAllocationMethod;
import elections.party.Action;
import elections.party.Party;
import elections.voter.Voter;

import java.util.*;
import java.util.stream.Stream;

public class District {

    private static final int VOTERS_TO_MANDATES_DIVISION_FACTOR = 10;

    protected int number;
    protected List<Voter> voters;
    protected List<Candidate> candidates;
    protected Map<Party, Integer> partyToVotesCount;
    protected Map<Party, Integer> partyToMandatesCount;

    public District(int number) {
        this.number = number;
        this.voters = new ArrayList<>();
        this.candidates = new ArrayList<>();
        this.partyToVotesCount = new HashMap<>();
        this.partyToMandatesCount = null;
    }

    public int votersNumber() {
        return voters.size();
    }

    public int mandatesNumber() {
        return voters.size() / VOTERS_TO_MANDATES_DIVISION_FACTOR;
    }

    public void addVote(Party party) {
        partyToVotesCount.put(party, partyToVotesCount.get(party) + 1);
    }

    public void conductElections(MandatesAllocationMethod allocationMethod) {
        voters.forEach(Voter::vote);

        Map<Party, Integer> partyToVotesCountCopy = new HashMap<>(partyToVotesCount);
        partyToMandatesCount = allocationMethod.allocateMandates(mandatesNumber(),
                                                                 partyToVotesCountCopy);
    }

    public void influenceVoters(Action action) {
        voters.forEach(voter -> voter.influence(action));
    }

    public double getCandidateAssessSum(Candidate candidate) {
        return voters.stream()
                     .mapToDouble(voter -> voter.assess(candidate))
                     .sum();
    }

    public Stream<Voter> voters() {
        return voters.stream();
    }

    public Stream<Candidate> candidates() {
        return candidates.stream();
    }

}
