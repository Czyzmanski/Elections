package elections.party;

import elections.district.District;

import java.util.List;

import static java.util.Comparator.comparingInt;

public class EconomicalParty extends Party {

    public EconomicalParty(String name, int budget, int mandatesNumber) {
        super(name, budget, mandatesNumber);
    }

    @Override
    public void conductCampaign(List<Action> actions, List<District> districts) {
        District minVotersDistrict = districts.stream()
                                              .min(comparingInt(District::votersNumber))
                                              .orElseThrow();
        Action minCostAction = actions.stream()
                                      .min(comparingInt(action -> action.getCost(minVotersDistrict)))
                                      .orElseThrow();
        int minCost = minCostAction.getCost(minVotersDistrict);

        while (minCost <= budget) {
            minVotersDistrict.influenceVoters(minCostAction);
            budget -= minCost;
        }
    }

}