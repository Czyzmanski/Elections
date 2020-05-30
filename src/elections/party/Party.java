package elections.party;

import elections.district.District;

import java.util.List;

public abstract class Party {

    protected String name;
    protected int budget;
    protected int mandatesNumber;

    public Party(String name, int budget, int mandatesNumber) {
        this.name = name;
        this.budget = budget;
        this.mandatesNumber = mandatesNumber;
    }

    public void addMandates(int mandates) {
        this.mandatesNumber += mandates;
    }

    public abstract void conductCampaign(List<Action> actions, List<District> districts);

}
