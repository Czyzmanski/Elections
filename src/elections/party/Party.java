package elections.party;

import elections.district.District;

import java.util.Arrays;
import java.util.List;

public abstract class Party {

    protected String name;
    protected int budget;
    protected Action[] actions;
    protected int numberOfMandates;

    public Party(String name, int budget, Action[] actions, int numberOfMandates) {
        this.name = name;
        this.budget = budget;
        this.actions = Arrays.copyOf(actions, actions.length);
        this.numberOfMandates = numberOfMandates;
    }

    public void addMandates(int mandates) {
        this.numberOfMandates += mandates;
    }

    public abstract void conductCampaign(List<District> districts);

}
