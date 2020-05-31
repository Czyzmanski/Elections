package elections.party;

import elections.district.District;
import elections.voter.Voter;

import java.util.List;
import java.util.function.BinaryOperator;

public abstract class AssessSumParty extends Party {

    protected BinaryOperator<Double> assessSumAccumulator;

    public AssessSumParty(String name, int budget, int mandatesNumber,
                          BinaryOperator<Double> assessSumAccumulator) {
        super(name, budget, mandatesNumber);
        this.assessSumAccumulator = assessSumAccumulator;
    }

    private double getPartyCandidatesAssessSum(District district) {
        return district.candidates()
                       .filter(candidate -> candidate.belongs(this))
                       .mapToDouble(district::getCandidateAssessSum)
                       .sum();
    }

    @Override
    public void conductCampaign(List<Action> actions, List<District> districts) {
        boolean anyActionPossible = true;
        while (anyActionPossible) {
            double desiredAssessSum = Long.MIN_VALUE;
            Action desiredAssessSumAction = null;
            int desiredAssessSumActionCost = 0;
            District desiredAssessSumDistrict = null;

            for (Action action : actions) {
                for (District district : districts) {
                    int cost = action.getCost(district);
                    if (cost <= budget) {
                        district.influenceVoters(action);

                        double assessSum = getPartyCandidatesAssessSum(district);
                        double nextAssessSum = assessSumAccumulator.apply(assessSum,
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
