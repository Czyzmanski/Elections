package elections.party;

import elections.district.District;
import elections.voter.Voter;

import java.util.List;
import java.util.function.BinaryOperator;

public abstract class AssessSumParty extends Party {

    protected BinaryOperator<Integer> assessSumAccumulator;

    public AssessSumParty(String name, int budget, BinaryOperator<Integer> assessSumAccumulator) {
        super(name, budget);
        this.assessSumAccumulator = assessSumAccumulator;
    }

    private int getPartyCandidatesAssessSum(District district) {
        return district.candidates()
                       .filter(candidate -> candidate.belongs(this))
                       .mapToInt(district::getCandidateAssessSum)
                       .sum();
    }

    @Override
    public void conductCampaign(List<Action> actions, List<District> districts) {
        boolean anyActionPossible = true;
        while (anyActionPossible) {
            int desiredAssessSum = Integer.MIN_VALUE;
            Action desiredAssessSumAction = null;
            int desiredAssessSumActionCost = 0;
            District desiredAssessSumDistrict = null;

            for (Action action : actions) {
                for (District district : districts) {
                    int cost = action.getCost(district);
                    if (cost <= budget) {
                        district.influenceVoters(action);

                        int assessSum = getPartyCandidatesAssessSum(district);
                        int nextAssessSum = assessSumAccumulator.apply(assessSum,
                                                                          desiredAssessSum);
                        if (desiredAssessSum != nextAssessSum) {
                            desiredAssessSum = nextAssessSum;
                            desiredAssessSumAction = action;
                            desiredAssessSumActionCost = cost;
                            desiredAssessSumDistrict = district;
                        }

                        district.voters()
                                .forEach(Voter::revertLastInfluence);
                    }
                }
            }

            anyActionPossible = desiredAssessSumAction != null;
            if (anyActionPossible) {
                desiredAssessSumDistrict.influenceVoters(desiredAssessSumAction);
                budget -= desiredAssessSumActionCost;
            }
        }
    }
}
