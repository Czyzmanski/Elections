package elections.party;

import elections.district.District;

import java.util.List;

public class ProfiligateParty extends Party {

    public ProfiligateParty(String name, int budget, int mandatesNumber) {
        super(name, budget, mandatesNumber);
    }

    @Override
    public void conductCampaign(List<Action> actions, List<District> districts) {
        boolean anyActionPossible = true;
        while (anyActionPossible) {
            int maxCostPossible = 0;
            Action maxCostPossibleAction = null;
            District maxCostPossibleDistrict = null;

            for (Action action : actions) {
                for (District district : districts) {
                    int cost = action.getCost(district);
                    if (maxCostPossible < cost && cost <= budget) {
                        maxCostPossible = cost;
                        maxCostPossibleAction = action;
                        maxCostPossibleDistrict = district;
                    }
                }
            }

            anyActionPossible = maxCostPossibleAction != null;
            if (anyActionPossible) {
                while (maxCostPossible <= budget) {
                    maxCostPossibleDistrict.influenceVoters(maxCostPossibleAction);
                    budget -= maxCostPossible;
                }
            }
        }
    }

}