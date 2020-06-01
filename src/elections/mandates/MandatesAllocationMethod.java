package elections.mandates;

import elections.party.Party;

import java.util.Map;

public abstract class MandatesAllocationMethod {

    protected String name;

    public MandatesAllocationMethod(String name) {
        this.name = name;
    }

    public abstract Map<Party, Integer> allocateMandates(int mandatesNumber,
                                                         Map<Party, Integer> partyToVotesCount);

    @Override
    public String toString() {
        return name;
    }

}
