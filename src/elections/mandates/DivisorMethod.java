package elections.mandates;

import elections.party.Party;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.IntFunction;

public abstract class DivisorMethod extends MandatesAllocationMethod {

    protected IntFunction<Integer> mandatesToDivisorFunction;

    public DivisorMethod(String name, IntFunction<Integer> mandatesToDivisorFunction) {
        super(name);
        this.mandatesToDivisorFunction = mandatesToDivisorFunction;
    }

    @Override
    public Map<Party, Integer> allocateMandates(int mandatesNumber,
                                                Map<Party, Integer> partyToVotesCount) {
        Map<Party, Integer> partyToMandatesCount = new HashMap<>();
        for (Party party : partyToVotesCount.keySet()) {
            partyToMandatesCount.put(party, 0);
        }

        Map<Party, Double> partyToQuotient = new HashMap<>();
        for (Map.Entry<Party, Integer> entry : partyToVotesCount.entrySet()) {
            partyToQuotient.put(entry.getKey(), (double) entry.getValue());
        }

        for (int i = 1; i <= mandatesNumber; i++) {
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

            double maxQuotientPartyVotes = partyToVotesCount.get(maxQuotientParty);
            int maxQuotientPartyMandates = partyToMandatesCount.get(maxQuotientParty);
            double divisor = mandatesToDivisorFunction.apply(maxQuotientPartyMandates);

            partyToQuotient.put(maxQuotientParty, maxQuotientPartyVotes / divisor);
            partyToMandatesCount.merge(maxQuotientParty, 1, Integer::sum);
        }

        return partyToMandatesCount;
    }

}
