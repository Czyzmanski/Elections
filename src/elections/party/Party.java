package elections.party;

import elections.district.District;

import java.util.List;
import java.util.Objects;

public abstract class Party {

    protected String name;
    protected int budget;
    protected int mandatesCount;

    public Party(String name, int budget) {
        this.name = name;
        this.budget = budget;
        this.mandatesCount = 0;
    }

    public String getName() {
        return name;
    }

    public void addMandates(int mandates) {
        mandatesCount += mandates;
    }

    public abstract void conductCampaign(List<Action> actions, List<District> districts);

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null || getClass() != other.getClass()) {
            return false;
        } else {
            Party party = (Party) other;
            return name.equals(party.name);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s, number of all mandates: %d", name, mandatesCount);
    }

}
