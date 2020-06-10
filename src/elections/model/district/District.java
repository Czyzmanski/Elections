package elections.model.district;

import elections.model.candidate.Candidate;
import elections.model.seats.SeatsAllocationMethod;
import elections.model.party.Action;
import elections.model.party.Party;
import elections.model.voter.Voter;
import elections.simulation.Reusable;

import java.util.*;
import java.util.stream.Stream;

public class District implements Reusable {

    private static final int VOTERS_TO_SEATS_DIVISION_FACTOR = 10;

    protected int number;
    protected List<Voter> voters;
    protected int votersNumber;
    protected Map<Party, List<Candidate>> partyToCandidates;
    protected Map<Party, Integer> partyToVotesCount;
    protected Map<Party, Integer> partyToSeatsCount;

    public District(int number, int votersNumber) {
        this.number = number;
        this.votersNumber = votersNumber;
        this.voters = new ArrayList<>(votersNumber);
        this.partyToCandidates = new TreeMap<>();
        this.partyToVotesCount = new HashMap<>();
    }

    public int getNumber() {
        return number;
    }

    public int getVotersNumber() {
        return votersNumber;
    }

    public int getSeatsNumber() {
        return votersNumber / VOTERS_TO_SEATS_DIVISION_FACTOR;
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

    public Candidate getCandidate(Party party, int ticketNumber) {
        return candidates(party).filter(candidate -> candidate.getTicketNumber() == ticketNumber)
                                .findFirst()
                                .orElse(null);
    }

    public District merge(District district) {
        district.candidates()
                .forEach(candidate -> {
                    addCandidate(candidate);
                    candidate.setTicketNumber(candidate.getTicketNumber() + getSeatsNumber());
                });

        district.voters()
                .forEach(this::addVoter);

        votersNumber += district.votersNumber;

        return this;
    }

    public void conductElections(SeatsAllocationMethod allocationMethod) {
        voters.forEach(Voter::vote);

        Map<Party, Integer> partyToVotesCountCopy = new HashMap<>(partyToVotesCount);
        partyToSeatsCount = allocationMethod.allocateSeats(getSeatsNumber(),
                                                                 partyToVotesCountCopy);
        partyToSeatsCount.forEach(Party::addSeats);

        printElectionsResults();
    }

    protected void printElectionsResults() {
        System.out.println("\nDistrict: " + number);

        System.out.println("Voters:");
        voters.stream()
              .sorted()
              .forEachOrdered(System.out::println);

        System.out.println("Candidates:");
        candidates().sorted()
                    .forEachOrdered(System.out::println);

        System.out.println("Parties:");
        partyToSeatsCount.forEach(
                (party, seatsCount) -> System.out.println(party.getName() + " " + seatsCount));
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

    private static <K> void initCountMap(Map<K, Integer> countMap) {
        countMap.keySet()
                .forEach(key -> countMap.put(key, 0));
    }

    @Override
    public void init() {
        voters.forEach(Voter::init);

        partyToCandidates.values()
                         .stream()
                         .flatMap(Collection::stream)
                         .forEach(Candidate::init);

        initCountMap(partyToVotesCount);
        if (partyToSeatsCount != null) {
            initCountMap(partyToSeatsCount);
        }
    }

}
