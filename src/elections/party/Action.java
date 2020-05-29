package elections.party;

import elections.district.District;

import java.util.Arrays;

public class Action {

    private int[] changes;
    private int sumOfAbsValues;

    public Action(int[] changes) {
        this.changes = Arrays.copyOf(changes, changes.length);
        this.sumOfAbsValues = Arrays.stream(this.changes)
                                    .map(Math::abs)
                                    .sum();
    }

    public int getCost(District district) {
        return this.sumOfAbsValues * district.numberOfVoters();
    }

}
