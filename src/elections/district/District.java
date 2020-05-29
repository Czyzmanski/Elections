package elections.district;

import elections.candidate.Candidate;
import elections.mandates.MandatesAllocationMethod;
import elections.party.Party;
import elections.voter.Voter;

import java.util.*;
import java.util.stream.Stream;

public class District implements Iterable<Candidate> {

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

    public int numberOfVoters() {
        return voters.size();
    }

    public int numberOfMandates() {
        return voters.size() / VOTERS_TO_MANDATES_DIVISION_FACTOR;
    }

    public void addVote(Party party) {
        partyToVotesCount.put(party, partyToVotesCount.get(party) + 1);
    }

    public void conductElections(MandatesAllocationMethod allocationMethod) {
        voters.forEach(Voter::vote);
        Map<Party, Integer> partyToVotesCountCopy = new HashMap<>(partyToVotesCount);
        partyToMandatesCount = allocationMethod.allocateMandates(partyToVotesCountCopy);
    }

    public Stream<Candidate> stream() {
        return candidates.stream();
    }

    private class DistrictIterator implements Iterator<Candidate> {

        private int processed;

        public DistrictIterator() {
            this.processed = 0;
        }

        @Override
        public boolean hasNext() {
            return processed < District.this.numberOfVoters();
        }

        @Override
        public Candidate next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else {
                return District.this.candidates.get(processed++);
            }
        }

    }

    @Override
    public Iterator<Candidate> iterator() {
        return new DistrictIterator();
    }

}
