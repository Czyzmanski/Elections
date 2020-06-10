package elections.model.seats;

import elections.model.party.Party;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HareNiemeyerMethod extends SeatsAllocationMethod {

    public HareNiemeyerMethod() {
        super("Hare-Niemeyer method");
    }

    @Override
    public Map<Party, Integer> allocateSeats(int seatsNumber,
                                                Map<Party, Integer> partyToVotesCount) {
        int allocatedSeats = 0;
        int allVotes = partyToVotesCount.values()
                                        .stream()
                                        .reduce(0, Integer::sum);

        Map<Party, Double> partyToRemainder = new HashMap<>();
        Map<Party, Integer> partyToSeatsCount = new TreeMap<>();

        for (Map.Entry<Party, Integer> entry : partyToVotesCount.entrySet()) {
            Party party = entry.getKey();
            double partyVotes = entry.getValue();
            double partyQuota = partyVotes / allVotes * seatsNumber;

            partyToSeatsCount.put(party, (int) partyQuota);
            partyToRemainder.put(party, partyQuota - (int) partyQuota);

            allocatedSeats += (int) partyQuota;
        }

        partyToRemainder.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .map(Map.Entry::getKey)
                        .limit(seatsNumber - allocatedSeats)
                        .forEach(party -> partyToSeatsCount.merge(party, 1, Integer::sum));

        return partyToSeatsCount;
    }


}
