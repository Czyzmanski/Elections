package elections.data;

import elections.model.candidate.Candidate;
import elections.model.district.District;
import elections.model.district.PairToMerge;
import elections.model.party.Action;
import elections.model.party.Party;
import elections.model.voter.*;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataLoader implements Closeable {

    private final BufferedReader bufferedReader;

    private int districtsNumber;
    private int partiesNumber;
    private int actionsNumber;
    private int qualitiesNumber;

    private List<PairToMerge> districtsPairsToMerge;
    private Map<Integer, District> numberToDistrict;
    private Map<String, Party> nameToParty;
    private List<Action> actions;

    public DataLoader(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public List<PairToMerge> districtsPairsToMerge() {
        return new ArrayList<>(districtsPairsToMerge);
    }

    public Map<Integer, District> numberToDistrict() {
        return new HashMap<>(numberToDistrict);
    }

    public List<Party> parties() {
        return nameToParty.values()
                          .stream()
                          .sorted()
                          .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Action> actions() {
        return new ArrayList<>(actions);
    }

    public void loadData() throws IOException {
        loadNumbers();
        loadDistrictsToMerge();
        loadParties();
        loadVotersInDistrictsNumbers();
        loadCandidates();
        loadVoters();
        loadActions();
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

    private void loadNumbers() throws IOException {
        try (Scanner lineScanner = new Scanner(bufferedReader.readLine())) {
            districtsNumber = lineScanner.nextInt();
            partiesNumber = lineScanner.nextInt();
            actionsNumber = lineScanner.nextInt();
            qualitiesNumber = lineScanner.nextInt();
        }
    }

    private void loadDistrictsToMerge() throws IOException {
        String line = bufferedReader.readLine()
                                    .replaceAll("[(,)]", " ");

        try (Scanner lineScanner = new Scanner(line)) {
            int pairsToMerge = lineScanner.nextInt();
            this.districtsPairsToMerge = new ArrayList<>(pairsToMerge);

            for (int i = 0; i < pairsToMerge; i++) {
                int first = lineScanner.nextInt(), second = lineScanner.nextInt();
                this.districtsPairsToMerge.add(new PairToMerge(first, second));
            }
        }
    }

    private void loadParties() throws IOException {
        String[] partyNames = bufferedReader.readLine()
                                            .split("\\s");
        String[] partyBudgets = bufferedReader.readLine()
                                              .split("\\s");
        String[] partyTypes = bufferedReader.readLine()
                                            .split("\\s");

        nameToParty = IntStream.range(0, partiesNumber)
                               .mapToObj(i -> Party.newInstance(partyTypes[i], partyNames[i],
                                                                Integer.parseInt(partyBudgets[i])))
                               .collect(Collectors.toMap(Party::getName, Function.identity()));
    }

    private void loadVotersInDistrictsNumbers() throws IOException {
        String[] votersNumbers = bufferedReader.readLine()
                                               .split("\\s");

        numberToDistrict = IntStream.rangeClosed(1, districtsNumber)
                                    .mapToObj(i -> new District(i, Integer.parseInt(votersNumbers[i - 1])))
                                    .collect(Collectors.toMap(District::getNumber, Function.identity()));
    }

    private void loadCandidates() throws IOException {
        int[] qualities = new int[qualitiesNumber];

        for (int districtNumber = 1; districtNumber <= districtsNumber; districtNumber++) {
            for (int i = 0; i < partiesNumber; i++) {
                int mandatesNumber = numberToDistrict.get(districtNumber)
                                                     .getMandatesNumber();
                for (int j = 0; j < mandatesNumber; j++) {
                    try (Scanner lineScanner = new Scanner(bufferedReader.readLine())) {
                        String firstName = lineScanner.next(), lastName = lineScanner.next();
                        District district = numberToDistrict.get(lineScanner.nextInt());
                        Party party = nameToParty.get(lineScanner.next());
                        int ticketNumber = lineScanner.nextInt();
                        for (int k = 0; k < qualities.length; k++) {
                            qualities[k] = lineScanner.nextInt();
                        }

                        Candidate candidate = new Candidate(firstName, lastName, qualities,
                                                            district, party, ticketNumber);
                        district.addCandidate(candidate);
                    }
                }
            }
        }
    }

    private void loadVoters() throws IOException {
        for (int districtNumber = 1; districtNumber <= districtsNumber; districtNumber++) {
            District district = numberToDistrict.get(districtNumber);
            int votersNumber = district.getVotersNumber();

            for (int i = 0; i < votersNumber; i++) {
                try (VoterLoader voterLoader = new VoterLoader(bufferedReader.readLine())) {
                    Voter voter = voterLoader.loadVoter();
                    district.addVoter(voter);
                }
            }
        }
    }

    private void loadActions() throws IOException {
        actions = new ArrayList<>(actionsNumber);
        int[] changes = new int[qualitiesNumber];

        for (int i = 0; i < actionsNumber; i++) {
            try (Scanner lineScanner = new Scanner(bufferedReader.readLine())) {
                for (int j = 0; j < qualitiesNumber; j++) {
                    changes[j] = lineScanner.nextInt();
                }
                actions.add(new Action(changes));
            }
        }
    }

    private class VoterLoader implements Closeable {

        private static final int PARTY_VOTER = 1;
        private static final int CANDIDATE_VOTER = 2;
        private static final int MIN_SINGLE_QUALITY_VOTER = 3;
        private static final int MAX_SINGLE_QUALITY_VOTER = 4;
        private static final int UNIVERSAL_VOTER = 5;
        private static final int MIN_SINGLE_QUALITY_PARTY_VOTER = 6;
        private static final int MAX_SINGLE_QUALITY_PARTY_VOTER = 7;
        private static final int UNIVERSAL_PARTY_VOTER = 8;

        private final Scanner lineScanner;

        public VoterLoader(String line) {
            this.lineScanner = new Scanner(line);
        }

        public Voter loadVoter() {
            String firstName = lineScanner.next(), lastName = lineScanner.next();
            District district = numberToDistrict.get(lineScanner.nextInt());
            int type = lineScanner.nextInt();

            if (type == PARTY_VOTER || type == CANDIDATE_VOTER) {
                Party party = nameToParty.get(lineScanner.next());

                if (type == PARTY_VOTER) {
                    return new PartyVoter(firstName, lastName, district, party);
                } else {
                    Candidate candidate = district.getCandidate(party, lineScanner.nextInt());
                    return new CandidateVoter(firstName, lastName, district, candidate);
                }
            } else if (type == UNIVERSAL_VOTER || type == UNIVERSAL_PARTY_VOTER) {
                int[] weights = new int[qualitiesNumber];
                for (int i = 0; i < weights.length; i++) {
                    weights[i] = lineScanner.nextInt();
                }

                if (type == UNIVERSAL_VOTER) {
                    return new UniversalVoter(firstName, lastName, district, weights);
                } else {
                    Party party = nameToParty.get(lineScanner.next());
                    return new UniversalPartyVoter(firstName, lastName, district, weights, party);
                }
            } else if (type == MIN_SINGLE_QUALITY_VOTER || type == MIN_SINGLE_QUALITY_PARTY_VOTER
                    || type == MAX_SINGLE_QUALITY_VOTER || type == MAX_SINGLE_QUALITY_PARTY_VOTER) {
                int qualityNumber = lineScanner.nextInt();

                if (type == MIN_SINGLE_QUALITY_VOTER) {
                    return new MinSingleQualityVoter(firstName, lastName, district, qualityNumber);
                } else if (type == MAX_SINGLE_QUALITY_VOTER) {
                    return new MaxSingleQualityVoter(firstName, lastName, district, qualityNumber);
                } else {
                    Party party = nameToParty.get(lineScanner.next());

                    if (type == MIN_SINGLE_QUALITY_PARTY_VOTER) {
                        return new MinSingleQualityPartyVoter(firstName, lastName,
                                                              district, qualityNumber, party);
                    } else {
                        return new MaxSingleQualityPartyVoter(firstName, lastName,
                                                              district, qualityNumber, party);
                    }
                }
            } else {
                throw new UnsupportedOperationException("Unsupported voter type");
            }
        }

        @Override
        public void close() {
            lineScanner.close();
        }

    }

}
