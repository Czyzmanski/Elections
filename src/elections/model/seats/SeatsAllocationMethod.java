package elections.model.seats;

import elections.model.party.Party;

import java.util.Map;

public abstract class SeatsAllocationMethod {

    protected String name;

    public SeatsAllocationMethod(String name) {
        this.name = name;
    }

    public abstract Map<Party, Integer> allocateSeats(int seatsNumber,
                                                         Map<Party, Integer> partyToVotesCount);

    @Override
    public String toString() {
        return name;
    }

}
