package elections.party;

import elections.district.District;

import java.util.Arrays;

public class Action {

    private final int[] changes;
    private final int absValuesSum;

    private Action(int[] changes, int absValuesSum) {
        this.changes = changes;
        this.absValuesSum = absValuesSum;
    }

    public Action(int[] changes) {
        this.changes = Arrays.copyOf(changes, changes.length);
        this.absValuesSum = Arrays.stream(changes)
                                  .map(Math::abs)
                                  .sum();
    }

    public int getCost(District district) {
        return absValuesSum * district.votersNumber();
    }

    public int getChange(int i) {
        return changes[i];
    }

    public Action getReverseAction() {
        int[] reverseChanges = new int[changes.length];
        for (int i = 0; i < reverseChanges.length; i++) {
            reverseChanges[i] = -changes[i];
        }

        return new Action(reverseChanges, absValuesSum);
    }

}
