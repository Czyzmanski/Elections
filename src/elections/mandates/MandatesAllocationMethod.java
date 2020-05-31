package elections.mandates;

import elections.party.Party;

import java.util.Map;

public abstract class MandatesAllocationMethod {

    public abstract Map<Party, Integer> allocateMandates(int mandatesNumber,
                                                         Map<Party, Integer> partyToVotesCount);

}
