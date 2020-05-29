package elections.mandates;

import elections.party.Party;

import java.util.Map;

public abstract class MandatesAllocationMethod {

    public abstract Map<Party, Integer> allocateMandates(Map<Party, Integer> partyToVotesCount);

}
