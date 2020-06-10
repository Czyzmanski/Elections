package elections.model.seats;

import elections.model.party.Party;

import java.util.*;
import java.util.function.IntFunction;

public abstract class DivisorMethod extends SeatsAllocationMethod {

    protected IntFunction<Integer> seatsToDivisorFunction;

    public DivisorMethod(String name, IntFunction<Integer> seatsToDivisorFunction) {
        super(name);
        this.seatsToDivisorFunction = seatsToDivisorFunction;
    }

    @Override
    public Map<Party, Integer> allocateSeats(int seatsNumber,
                                                Map<Party, Integer> partyToVotesCount) {
        Map<Party, Integer> partyToSeatsCount = new TreeMap<>();
        for (Party party : partyToVotesCount.keySet()) {
            partyToSeatsCount.put(party, 0);
        }

        Map<Party, Double> partyToQuotient = new HashMap<>();
        for (Map.Entry<Party, Integer> entry : partyToVotesCount.entrySet()) {
            partyToQuotient.put(entry.getKey(), (double) entry.getValue());
        }

        for (int i = 1; i <= seatsNumber; i++) {
            double maxQuotient = partyToQuotient.values()
                                                .stream()
                                                .max(Comparator.naturalOrder())
                                                .orElseThrow();
            int maxQuotientCount = (int) partyToQuotient.values()
                                                        .stream()
                                                        .filter(quotient -> quotient == maxQuotient)
                                                        .count();
            Party maxQuotientParty = partyToQuotient.entrySet()
                                                    .stream()
                                                    .filter(entry -> entry.getValue() == maxQuotient)
                                                    .map(Map.Entry::getKey)
                                                    .skip(new Random().nextInt(maxQuotientCount))
                                                    .findFirst()
                                                    .orElseThrow();

            partyToSeatsCount.merge(maxQuotientParty, 1, Integer::sum);

            double maxQuotientPartyVotes = partyToVotesCount.get(maxQuotientParty);
            int maxQuotientPartySeats = partyToSeatsCount.get(maxQuotientParty);
            double divisor = seatsToDivisorFunction.apply(maxQuotientPartySeats);

            partyToQuotient.put(maxQuotientParty, maxQuotientPartyVotes / divisor);
        }

        return partyToSeatsCount;
    }

}
