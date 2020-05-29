package elections.party;

import elections.district.District;

import java.util.Arrays;

public class Action {

    private final int[] changes;
    private final int sumOfAbsValues;

    public Action(int[] changes) {
        this.changes = Arrays.copyOf(changes, changes.length);
        this.sumOfAbsValues = Arrays.stream(changes)
                                    .map(Math::abs)
                                    .sum();
    }

    public int getCost(District district) {
        return sumOfAbsValues * district.numberOfVoters();
    }

}
