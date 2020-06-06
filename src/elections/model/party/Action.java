package elections.model.party;

import elections.model.district.District;

import java.util.Arrays;

public class Action {

    private final int[] changes;
    private final int absValuesSum;

    public Action(int[] changes) {
        this.changes = Arrays.copyOf(changes, changes.length);
        this.absValuesSum = Arrays.stream(changes)
                                  .map(Math::abs)
                                  .sum();
    }

    public int getCost(District district) {
        return absValuesSum * district.getVotersNumber();
    }

    public int getChange(int i) {
        return changes[i];
    }

}
