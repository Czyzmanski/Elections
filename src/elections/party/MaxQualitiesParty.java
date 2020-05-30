package elections.party;

import elections.district.District;

import java.util.List;

public class MaxQualitiesParty extends Party {

    public MaxQualitiesParty(String name, int budget, int mandatesNumber) {
        super(name, budget, mandatesNumber);
    }

    @Override
    public void conductCampaign(List<Action> actions, List<District> districts) {
        boolean anyActionPossible = true;
        
    }
}
