package elections.model.party;

import elections.model.district.District;

import java.util.Comparator;
import java.util.List;

public class EconomicalParty extends Party {

    public EconomicalParty(String name, int budget) {
        super(name, budget);
    }

    @Override
    public void conductCampaign(List<Action> actions, List<District> districts) {
        District minVotersDistrict = districts.stream()
                                              .min(Comparator.comparingInt(District::getVotersNumber))
                                              .orElseThrow();
        Action minCostAction = actions.stream()
                                      .min(Comparator.comparingInt(
                                              action -> action.getCost(minVotersDistrict)))
                                      .orElseThrow();
        int minCost = minCostAction.getCost(minVotersDistrict);

        while (minCost <= budget) {
            minVotersDistrict.influenceVoters(minCostAction);
            budget -= minCost;
        }
    }

}
