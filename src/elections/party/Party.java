package elections.party;

import elections.district.District;

import java.util.List;

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
    public String toString() {
        return String.format("%s, number of all mandates: %d", name, mandatesCount);
    }

}
