package elections.mandates;

import elections.party.Party;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HareNiemeyerMethod extends MandatesAllocationMethod {

    @Override
    public Map<Party, Integer> allocateMandates(int mandatesNumber,
                                                Map<Party, Integer> partyToVotesCount) {
        int allocatedMandates = 0;
        int allVotes = partyToVotesCount.values()
                                        .stream()
                                        .reduce(0, Integer::sum);

        Map<Party, Double> partyToRemainder = new HashMap<>();
        Map<Party, Integer> partyToMandatesCount = new HashMap<>();

        for (Map.Entry<Party, Integer> entry : partyToVotesCount.entrySet()) {
            Party party = entry.getKey();
            double partyVotes = entry.getValue();
            double partyQuota = partyVotes / allVotes * mandatesNumber;

            partyToMandatesCount.put(party, (int) partyQuota);
            partyToRemainder.put(party, partyQuota - (int) partyQuota);

            allocatedMandates += (int) partyQuota;
        }

        partyToRemainder.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .map(Map.Entry::getKey)
                        .limit(mandatesNumber - allocatedMandates)
                        .forEach(party -> partyToMandatesCount.merge(party, 1, Integer::sum));

        return partyToMandatesCount;
    }


}
