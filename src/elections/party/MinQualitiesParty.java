package elections.party;

import elections.district.District;

import java.util.List;

public class MinQualitiesParty extends Party {

    public MinQualitiesParty(String name, int budget, int mandatesNumber) {
        super(name, budget, mandatesNumber);
    }

    @Override
    public void conductCampaign(List<Action> actions, List<District> districts) {

    }
}
