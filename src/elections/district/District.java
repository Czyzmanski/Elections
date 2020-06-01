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
    protected Map<Party, List<Candidate>> partyToCandidates;
    protected Map<Party, Integer> partyToVotesCount;
    protected Map<Party, Integer> partyToMandatesCount;

    public District(int number) {
        this.number = number;
        this.voters = new ArrayList<>();
        this.partyToCandidates = new HashMap<>();
        this.partyToVotesCount = new HashMap<>();
        this.partyToMandatesCount = null;
    }

    public int getVotersNumber() {
        return voters.size();
    }

    public int getMandatesNumber() {
        return voters.size() / VOTERS_TO_MANDATES_DIVISION_FACTOR;
    }

    public void addVote(Party party) {
        partyToVotesCount.merge(party, 1, Integer::sum);
    }

    public void addVoter(Voter voter) {
        voters.add(voter);
    }

    public void addCandidate(Candidate candidate) {
        Party party = candidate.getParty();
        List<Candidate> candidates = partyToCandidates.getOrDefault(party, new ArrayList<>());
        candidates.add(candidate);
        partyToCandidates.put(party, candidates);
    }

    public District merge(District district) {
        district.candidates().forEach(candidate -> {
            addCandidate(candidate);
            candidate.setTicketNumber(candidate.getTicketNumber() + getMandatesNumber());
        });

        district.voters()
                .forEach(this::addVoter);

        return this;
    }

    protected void printElectionsResults(MandatesAllocationMethod allocationMethod) {
        System.out.println(allocationMethod);

        System.out.println("\nDistrict: " + number);

        System.out.println("Voters:");
        voters.forEach(System.out::println);

        System.out.println("Candidates:");
        candidates().forEach(System.out::println);

        System.out.println("Parties:");
        partyToMandatesCount.forEach(
                (party, mandatesCount) -> System.out.println(
                        party.getName() + " " + mandatesCount));
    }

    public void conductElections(MandatesAllocationMethod allocationMethod) {
        voters.forEach(Voter::vote);

        Map<Party, Integer> partyToVotesCountCopy = new HashMap<>(partyToVotesCount);
        partyToMandatesCount = allocationMethod.allocateMandates(getMandatesNumber(),
                                                                 partyToVotesCountCopy);

        printElectionsResults(allocationMethod);
    }

    public void influenceVoters(Action action) {
        voters.forEach(voter -> voter.influence(action));
    }

    public int getCandidateAssessSum(Candidate candidate) {
        return voters.stream()
                     .mapToInt(voter -> voter.assess(candidate))
                     .sum();
    }

    public Stream<Voter> voters() {
        return voters.stream();
    }

    public Stream<Candidate> candidates() {
        return partyToCandidates.values()
                                .stream()
                                .flatMap(Collection::stream);
    }

    public Stream<Candidate> candidates(Party party) {
        return partyToCandidates.getOrDefault(party, new ArrayList<>())
                                .stream();
    }

}
